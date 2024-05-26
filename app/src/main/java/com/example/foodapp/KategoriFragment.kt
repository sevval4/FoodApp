package com.example.foodapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodapp.databinding.FragmentKategoriBinding
import com.example.foodapp.model.Besin
import com.google.firebase.firestore.FirebaseFirestore
import java.io.Serializable


class KategoriFragment : Fragment() {
    private lateinit var binding: FragmentKategoriBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKategoriBinding.inflate(inflater, container, false)
        setupCategoryClickListener()
        return binding.root
    }

    private fun setupCategoryClickListener() {
        binding.txtSut.setOnClickListener {
            fetchDataForCategory(1)
        }
        binding.txtEkmek.setOnClickListener {
            fetchDataForCategory(6)
        }
        binding.txtYag.setOnClickListener {
            fetchDataForCategory(6)
        }
        binding.txtEt.setOnClickListener {
            fetchDataForCategory(2)
        }
        binding.txtBaklagil.setOnClickListener {
            fetchDataForCategory(5)
        }
        binding.txtMeyve.setOnClickListener {
            fetchDataForCategory(6)
        }
        binding.txtFastfood.setOnClickListener {
            fetchDataForCategory(6)
        }
    }

    private fun fetchDataForCategory(categoryId: Int) {
        db.collection("Besin")
            .whereEqualTo("KategoriID", categoryId)
            .get()
            .addOnSuccessListener { documents ->
                val besinList = mutableListOf<Besin>()
                for (document in documents) {
                    val besinAdi = document.getString("BesinAdi") ?: ""
                    val kalori = document.getLong("Kalori") ?: 0
                    val olcu = document.getString("Olcu") ?: ""
                    val kateoriId = document.getLong("KategoriId") ?: 0
                    val besin = Besin(besinAdi, kalori, olcu,0L)
                    besinList.add(besin)
                }
                startBesinFragment(besinList)
            }
            .addOnFailureListener { exception ->
                Log.e("KategoriFragment", "Error getting documents: ", exception)
            }
    }

    private fun startBesinFragment(besinList: List<Besin>) {
        val besinFragment = BesinFragment()
        val bundle = Bundle().apply {
            putSerializable("besinList", ArrayList(besinList) as Serializable)
        }
        besinFragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, besinFragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}
