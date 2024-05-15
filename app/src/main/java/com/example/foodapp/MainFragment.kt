package com.example.foodapp


import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.foodapp.model.Besin

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MainFragment : Fragment() {
    //private lateinit var binding: FragmentMainBinding
    //val db = FirebaseFirestore.getInstance()
    //private var veri:String?=null
    private lateinit var besinAdapter: BesinTuketimAdapter
    private lateinit var listView: ListView
    private lateinit var btnKahvalti: ImageView
    private lateinit var imgProfil:ImageView
    private lateinit var btnSu:ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val veri = arguments?.getString("veri")
        imgProfil = view.findViewById(R.id.img_profil)
        btnSu=view.findViewById(R.id.btn_su)
        Log.d("MainFragment", "Veri geldi: $veri")

        val besinList = parseVeri(veri)

        imgProfil.setOnClickListener {
            val fragment = ProfilFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        btnSu.setOnClickListener {
            val fragment = SuFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val besinAdapter = BesinTuketimAdapter(requireContext(), besinList)
        recyclerView.adapter = besinAdapter

        btnKahvalti = view.findViewById(R.id.btn_kahvalti)

        btnKahvalti.setOnClickListener {
            val fragment = KategoriFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        return view
    }


    private fun parseVeri(veri: String?): List<Besin> {
        val besinList = mutableListOf<Besin>()

        veri?.let {
            val pattern = Regex("(.*?): (\\d+) kalori, (\\d+)g")
            val matches = pattern.findAll(it)

            matches.forEach { match ->
                val besinAdi = match.groupValues[1]
                val kalori = match.groupValues[2].toLong()
                val olcu = match.groupValues[3]
                // 'gönderilecek veri' kısmını kaldır
                val trimmedBesinAdi = besinAdi.replace("gönderilecek veri", "").trim()
                besinList.add(Besin(trimmedBesinAdi, kalori, olcu, 0))
            }
        }

        Log.d("MainFragment", "Besin Listesi: $besinList")
        return besinList
    }







}

















//    fun butonaBasildi() {
//
//        binding.btnKahvalti.setOnClickListener {
//            callFragment(KategoriFragment())
//            if (veri != null) {
//                binding.editTextTextMultiLine2.setText(veri)
//            } else {
//                Log.d("main", "Veri null, alınamadı.")
//            }
//
//        }
//
//        binding.btnOgleYemek.setOnClickListener {
//            callFragment(KategoriFragment())
//            binding.editTextText.setText(veri)
//        }
//
//        binding.btnAksamYemek.setOnClickListener {
//            callFragment(KategoriFragment())
//            binding.editTextTextAksam.setText(veri)
//        }
//
//
//
//        binding.btnSu.setOnClickListener {
//            callFragment(SuFragment())
//        }
//    }

//    fun callFragment(fragment: Fragment) {
//        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
//        binding.container.removeAllViews()
//        fragmentTransaction.replace(binding.container.id, fragment)
//        fragmentTransaction.commit()
//    }

