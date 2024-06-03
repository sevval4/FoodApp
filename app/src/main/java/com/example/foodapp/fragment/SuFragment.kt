package com.example.foodapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentSuBinding


class SuFragment : Fragment() {
    private lateinit var binding: FragmentSuBinding

    private val emptyGlass = R.drawable.empty_glass
    private val filledGlass = R.drawable.bardak_fill
    private val glassCapacity = 200
    private val bardaklar = mutableListOf<ImageView>()
    private val suMiktarlari = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuBinding.inflate(inflater, container, false)

        initializeBardaklar()


        return binding.root
    }


    private fun initializeBardaklar() {
        bardaklar.addAll(
            listOf(

                binding.imageView10, binding.imageView11, binding.imageView13, binding.imageView17,
                binding.imageView20, binding.imageView21, binding.imageView23, binding.imageView27,
                binding.imageView30, binding.imageView31,
            )
        )

        for (i in 1..bardaklar.size) {
            suMiktarlari.add(0)
        }

        bardaklar.forEachIndexed { index, bardak ->
            bardak.setOnClickListener {
                toggleGlass(index)
            }
        }
    }

    private fun toggleGlass(index: Int) {
        val bardak = bardaklar[index]
        val currentSuMiktari = suMiktarlari[index]

        if (currentSuMiktari == 0) {
            bardak.setImageResource(filledGlass)
            suMiktarlari[index] = glassCapacity
        } else {
            bardak.setImageResource(emptyGlass)
            suMiktarlari[index] = 0
        }

        updateSuMiktari()
    }
    private fun calculateTotalSuMiktari(): Int {
        var totalSuMiktari = 0
        suMiktarlari.forEach { suMiktari ->
            totalSuMiktari += suMiktari
        }
        return totalSuMiktari
    }
    private fun updateSuMiktari() {
        val toplamSuMiktari = calculateTotalSuMiktari()
        binding.txtSu.text = "Tüketilen: ${toplamSuMiktari} ml"

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("toplamSuMiktari", toplamSuMiktari)
        editor.apply()

        val mainFragment = requireActivity().supportFragmentManager.findFragmentByTag("MainFragment") as? MainFragment
        mainFragment?.updateSuMiktariText()
    }

}