package com.example.foodapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.foodapp.databinding.FragmentFruitProcessingBinding
import com.example.foodapp.ml.DietModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class FruitProcessingFragment : Fragment() {

    private lateinit var binding: FragmentFruitProcessingBinding
    private val imageSize = 32

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { uri ->
                try {
                    val image = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                    binding.imageView.setImageBitmap(image)

                    val scaledImage = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                    classifyImage(scaledImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            val extras = result.data?.extras
            if (extras != null && extras.containsKey("data")) {
                val imageBitmap = extras["data"] as Bitmap
                binding.imageView.setImageBitmap(imageBitmap)

                val scaledImage = ThumbnailUtils.extractThumbnail(imageBitmap, imageSize, imageSize)
                classifyImage(scaledImage)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFruitProcessingBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.camera.setOnClickListener {
            if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                takePictureLauncher.launch(cameraIntent)
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }

        binding.gallery.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            takePictureLauncher.launch(galleryIntent)
        }

        return view
    }

    private fun classifyImage(image: Bitmap) {
        val model = DietModel.newInstance(requireContext())

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 64, 64, 3), DataType.FLOAT32)

        val byteBuffer = ByteBuffer.allocateDirect(4 * 64 * 64 * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val scaledImage = Bitmap.createScaledBitmap(image, 64, 64, false)

        val intValues = IntArray(64 * 64)
        scaledImage.getPixels(intValues, 0, scaledImage.width, 0, 0, scaledImage.width, scaledImage.height)
        var pixel = 0

        for (i in 0 until 64) {
            for (j in 0 until 64) {
                val value = intValues[pixel++] // RGB
                byteBuffer.putFloat(((value shr 16) and 0xFF).toFloat() / 255f)
                byteBuffer.putFloat(((value shr 8) and 0xFF).toFloat() / 255f)
                byteBuffer.putFloat((value and 0xFF).toFloat() / 255f)
            }
        }

        inputFeature0.loadBuffer(byteBuffer)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.getOutputFeature0AsTensorBuffer()

        val confidences = outputFeature0.floatArray
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }

        val classIndices = mapOf(
            0 to "Banana",
            1 to "Grape & Apple",
            2 to "Orange",
            3 to "Qiwi",
            4 to "Watermelon"
        )

        val predictedClass = classIndices[maxPos] ?: "Bilinmeyen Meyve"
        binding.result.text = predictedClass

        val predictedCalories = getCalories(predictedClass)

        val calorieText = "Tahmini Kalori Değeri: $predictedCalories"
        binding.result.append("\n$calorieText")

        model.close()
    }

    private fun getCalories(predictedClass: String): Int {
        val calorieDict = mapOf(
            "banana" to 89,
            "grape & apple" to 67,
            "orange" to 62,
            "qiwi" to 23,
            "watermelon" to 30,
            "unknown fruit" to -1
        )
        return calorieDict[predictedClass.toLowerCase()] ?: -1
    }
}
