package com.example.foodapp


import android.content.Context
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.foodapp.model.Besin
import com.example.foodapp.model.ChildInfo
import example.abhiandroid.expandablelistviewexample.GroupInfo

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

    private lateinit var customAdapter: CustomAdapter
    private val subjects = LinkedHashMap<String, GroupInfo>()
    private val deptList = ArrayList<GroupInfo>()

    private lateinit var listAdapter: CustomAdapter
    private lateinit var simpleExpandableListView: ExpandableListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        customAdapter = CustomAdapter(requireContext(), deptList)

        simpleExpandableListView = view.findViewById(R.id.simpleExpandableListView) as ExpandableListView
        val selectedItems = (activity as MainActivity).getSelectedItems()
        loadData(selectedItems)

        listAdapter = CustomAdapter(requireContext(), deptList)

        simpleExpandableListView.setAdapter(listAdapter)

        expandAll()

        simpleExpandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            val headerInfo = deptList[groupPosition]
            val detailInfo = headerInfo.list[childPosition]
            Toast.makeText(requireContext(), " Clicked on :: " + headerInfo.name
                    + "/" + detailInfo.name, Toast.LENGTH_LONG).show()
            false
        }
        simpleExpandableListView.setOnGroupClickListener { parent, v, groupPosition, id ->
            val headerInfo = deptList[groupPosition]
            Toast.makeText(requireContext(), " Header is :: " + headerInfo.name,
                Toast.LENGTH_LONG).show()
            false
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
                val trimmedBesinAdi = besinAdi.replace("gönderilecek veri", "").trim()
                besinList.add(Besin(trimmedBesinAdi, kalori, olcu, 0))
            }
        }

        Log.d("MainFragment", "Besin Listesi: $besinList")
        return besinList
    }

    private fun expandAll() {
        val count = listAdapter.groupCount
        for (i in 0 until count) {
            simpleExpandableListView.expandGroup(i)
        }
    }
    private fun loadData(selectedItems: List<Besin>) {
        // clickedCategory değerini SharedPreferences'tan al
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val clickedCategory = sharedPreferences.getString("clickedCategory", "default_value")

        Log.d("loadData", "kategori: $clickedCategory")

        val kahvaltiList = SharedPreferencesUtil.getBesinList(requireContext(), "Kahvalti")?.toMutableList() ?: mutableListOf()
        val ogleYemegiList = SharedPreferencesUtil.getBesinList(requireContext(), "OgleYemegi")?.toMutableList() ?: mutableListOf()
        val aksamYemegiList = SharedPreferencesUtil.getBesinList(requireContext(), "AksamYemegi")?.toMutableList() ?: mutableListOf()
        val araOgunEkleList = SharedPreferencesUtil.getBesinList(requireContext(), "AraOgunEkle")?.toMutableList() ?: mutableListOf()
        val suEkleList = SharedPreferencesUtil.getBesinList(requireContext(), "SuEkle")?.toMutableList() ?: mutableListOf()
        val egzersizEkleList = SharedPreferencesUtil.getBesinList(requireContext(), "EgzersizEkle")?.toMutableList() ?: mutableListOf()
        val digerList = SharedPreferencesUtil.getBesinList(requireContext(), "Diger")?.toMutableList() ?: mutableListOf()

        selectedItems.forEach { item ->
            when (clickedCategory) {
                "Kahvaltı" -> {
                    kahvaltiList.add(item)
                    Log.d("loadData", "Kahvaltı Ekle : $item")
                }
                "Öğle Yemeği" -> {
                    ogleYemegiList.add(item)
                }
                "Akşam Yemeği" -> {
                    aksamYemegiList.add(item)
                }
                "Ara Öğün Ekle" -> {
                    araOgunEkleList.add(item)
                    Log.d("loadData", "Ara Öğün Ekle : $item")
                }
                "Su Ekle" -> {
                    suEkleList.add(item)
                }
                "Egzersiz Ekle" -> {
                    egzersizEkleList.add(item)
                }
                else -> {
                    digerList.add(item)
                }
            }
        }

        // Listeleri SharedPreferences içinde sakla
        SharedPreferencesUtil.saveBesinList(requireContext(), "Kahvalti", kahvaltiList)
        SharedPreferencesUtil.saveBesinList(requireContext(), "OgleYemegi", ogleYemegiList)
        SharedPreferencesUtil.saveBesinList(requireContext(), "AksamYemegi", aksamYemegiList)
        SharedPreferencesUtil.saveBesinList(requireContext(), "AraOgunEkle", araOgunEkleList)
        SharedPreferencesUtil.saveBesinList(requireContext(), "SuEkle", suEkleList)
        SharedPreferencesUtil.saveBesinList(requireContext(), "EgzersizEkle", egzersizEkleList)
        SharedPreferencesUtil.saveBesinList(requireContext(), "Diger", digerList)

        // Ürünleri ekleyin
        addProduct("Kahvaltı", kahvaltiList)
        addProduct("Öğle Yemeği", ogleYemegiList)
        addProduct("Akşam Yemeği", aksamYemegiList)
        addProduct("Ara Öğün Ekle", araOgunEkleList)
        addProduct("Su Ekle", suEkleList)
        addProduct("Egzersiz Ekle", egzersizEkleList)
        addProduct("Diğer", digerList)
    }


    private fun addProduct(department: String, selectedItems: List<Besin>): Int {
        var groupPosition = 0
        var headerInfo = subjects[department]
        if (headerInfo == null) {
            headerInfo = GroupInfo()
            headerInfo.name = department
            subjects[department] = headerInfo
            deptList.add(headerInfo)
        }

        val productList = headerInfo.list
        var listSize = productList.size

        selectedItems.forEach { item ->
            listSize++

            val detailInfo = ChildInfo()
            detailInfo.sequence = listSize.toString()
            detailInfo.name = item.besinAdi
            productList.add(detailInfo)
        }

        headerInfo.list = productList

        groupPosition = deptList.indexOf(headerInfo)
        return groupPosition
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

