package com.example.foodapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.databinding.FragmentSu2Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SuFragment : Fragment() {
    private lateinit var binding: FragmentSu2Binding

    private val emptyGlass = R.drawable.empty_glass
    private val filledGlass = R.drawable.bardak_fill
    private val glassCapacity = 200 // Bardak kapasitesi (ml)
    private val bardaklar = mutableListOf<ImageView>() // Bardakların listesi
    private val suMiktarlari = mutableListOf<Int>() // Bardaklardaki su miktarlarının listesi (ml cinsinden)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSu2Binding.inflate(inflater, container, false)

        initializeBardaklar() // Bardakları ve su miktarlarını başlat


        return binding.root
    }

    // Bardakları ve su miktarlarını başlatan yardımcı fonksiyon
    private fun initializeBardaklar() {
        bardaklar.addAll(
            listOf(
                binding.imageView, binding.imageView2, binding.imageView3, binding.imageView7,
                binding.imageView10, binding.imageView11, binding.imageView13, binding.imageView17,
                binding.imageView20, binding.imageView21, binding.imageView23, binding.imageView27,
                binding.imageView30, binding.imageView31, binding.imageView33, binding.imageView37
            )
        )

        // Her bardak için başlangıçta su miktarını sıfıra ayarla
        for (i in 1..bardaklar.size) {
            suMiktarlari.add(0)
        }

        // Her bardak için tıklama işlemi tanımla
        bardaklar.forEachIndexed { index, bardak ->
            bardak.setOnClickListener {
                toggleGlass(index) // Bardağın durumunu değiştir
            }
        }
    }

    // Bardak durumunu değiştiren fonksiyon
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

        updateSuMiktari() // Toplam su miktarını güncelle
    }
    private fun calculateTotalSuMiktari(): Int {
        var totalSuMiktari = 0
        suMiktarlari.forEach { suMiktari ->
            totalSuMiktari += suMiktari
        }
        return totalSuMiktari
    }
    // Toplam su miktarını güncelleyen fonksiyon
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