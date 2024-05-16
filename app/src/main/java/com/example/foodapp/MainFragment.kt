package com.example.foodapp


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




    private val subjects = LinkedHashMap<String, GroupInfo>()
    private val deptList = ArrayList<GroupInfo>()

    private lateinit var listAdapter: CustomAdapter
    private lateinit var simpleExpandableListView: ExpandableListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // add data for displaying in expandable list view
        loadData()

        //get reference of the ExpandableListView
        simpleExpandableListView = view.findViewById(R.id.simpleExpandableListView) as ExpandableListView

        // create the adapter by passing your ArrayList data
        listAdapter = CustomAdapter(requireContext(), deptList)
        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(listAdapter)

        //expand all the Groups
        expandAll()

        // setOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            //get the group header
            val headerInfo = deptList[groupPosition]
            //get the child info
            val detailInfo = headerInfo.list[childPosition]
            //display it or do something with it
            Toast.makeText(requireContext(), " Clicked on :: " + headerInfo.name
                    + "/" + detailInfo.name, Toast.LENGTH_LONG).show()
            false
        }
        // setOnGroupClickListener listener for group heading click
        simpleExpandableListView.setOnGroupClickListener { parent, v, groupPosition, id ->
            //get the group header
            val headerInfo = deptList[groupPosition]
            //display it or do something with it
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
                // 'gönderilecek veri' kısmını kaldır
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

    //load some initial data into out list
    private fun loadData() {
        addProduct("Kahvaltı", "Ürün 1")
        addProduct("Kahvaltı", "Ürün 2")
        addProduct("Kahvaltı", "Ürün 3")

        addProduct("Öğle Yemeği", "Ürün 1")
        addProduct("Öğle Yemeği", "Ürün 2")

        addProduct("Akşam Yemeği", "Ürün 1")
        addProduct("Akşam Yemeği", "Ürün 2")

        addProduct("Ara Öğün Ekle", "Yeni Ürün")

        addProduct("Su Ekle", "Su")

        addProduct("Egzersiz Ekle", "Yürüyüş")
    }


    //here we maintain our products in various departments
    private fun addProduct(department: String, product: String): Int {
        var groupPosition = 0

        //check the hash map if the group already exists
        var headerInfo = subjects[department]
        //add the group if doesn't exists
        if (headerInfo == null) {
            headerInfo = GroupInfo()
            headerInfo.name = department
            subjects[department] = headerInfo
            deptList.add(headerInfo)
        }

        //get the children for the group
        val productList = headerInfo.list
        //size of the children list
        var listSize = productList.size
        //add to the counter
        listSize++

        //create a new child and add that to the group
        val detailInfo = ChildInfo()
        detailInfo.sequence = listSize.toString()
        detailInfo.name = product
        productList.add(detailInfo)
        headerInfo.list = productList

        //find the group position inside the list
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

