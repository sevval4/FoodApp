package com.example.foodapp.fragment


import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.example.foodapp.adapter.CustomAdapter
import com.example.foodapp.MainActivity
import com.example.foodapp.R
import com.example.foodapp.SharedPreferencesUtil
import com.example.foodapp.databinding.FragmentMainBinding

import com.example.foodapp.model.Besin
import com.example.foodapp.model.ChildInfo
import com.google.firebase.firestore.FirebaseFirestore
import example.abhiandroid.expandablelistviewexample.GroupInfo

class MainFragment : Fragment() {

    private lateinit var customAdapter: CustomAdapter
    private val subjects = LinkedHashMap<String, GroupInfo>()
    private val deptList = ArrayList<GroupInfo>()
    private lateinit var listAdapter: CustomAdapter
    private lateinit var simpleExpandableListView: ExpandableListView
    private lateinit var binding: FragmentMainBinding
    private val suMiktarlari = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        customAdapter = CustomAdapter(requireContext(), deptList)

        binding.imgProfil.setOnClickListener {
            val profiFragment = ProfileFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainer, profiFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        simpleExpandableListView =
            view.findViewById(R.id.simpleExpandableListView) as ExpandableListView
        val selectedItems = (activity as MainActivity).getSelectedItems()
        loadData(selectedItems)
        listAdapter = CustomAdapter(requireContext(), deptList)
        simpleExpandableListView.setAdapter(listAdapter)
        expandAll()
        simpleExpandableListView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            val headerInfo = deptList[groupPosition]
            val detailInfo = headerInfo.list[childPosition]
            false
        }
        simpleExpandableListView.setOnGroupClickListener { _, _, groupPosition, _ ->
            val headerInfo = deptList[groupPosition]
            false
        }


        binding.datePickerEditText.setOnClickListener {
            showDatePickerDialog()
        }


        return view
    }



    fun fetchDailyRecords(startDate: String, endDate: String) {
        val db = FirebaseFirestore.getInstance()
        val dailyRecordRef = db.collection("DailyRecord")

        dailyRecordRef.whereGreaterThanOrEqualTo("date", startDate)
            .whereLessThanOrEqualTo("date", endDate)
            .get()
            .addOnSuccessListener { documents ->
                val records = ArrayList<Besin>()

                for (document in documents) {
                    val besinAdi = document.getString("BesinAdi")
                    val kalori = document.getLong("Kalori")
                    val olcu = document.getString("Olcu")
                    val kategoriID = document.getLong("KategoriID")

                    val besin = Besin(besinAdi!!, kalori!!, olcu!!, kategoriID ?: 0)
                    records.add(besin)

//                    Log.d(
//                        "DailyRecord",
//                        "Besin Adı: $besinAdi, Kalori: $kalori, Ölçü: $olcu"
//                    )
//                    val fragment = RecordFragment.newInstance(records, startDate)
//                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
//                    transaction.replace(R.id.fragmentContainer, fragment)
//                    transaction.addToBackStack(null)
//                    transaction.commit()

                }


            }
            .addOnFailureListener { e ->
               // Log.e("DailyRecord", "Error fetching records: $e")
            }
    }



    fun updateSuMiktariText() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val toplamSuMiktari = sharedPreferences.getInt("toplamSuMiktari", 0)
        val toplamSuMiktariText = "Tüketilen: ${toplamSuMiktari} ml"

        var headerInfo = subjects["Su Ekle"]
        if (headerInfo == null) {
            headerInfo = GroupInfo()
            headerInfo.name = "Su Ekle"
            subjects["Su Ekle"] = headerInfo
            deptList.add(headerInfo)
        }

        val productList = headerInfo.list
        productList.clear()
        val detailInfo = ChildInfo()
        detailInfo.sequence = "1"
        detailInfo.name = toplamSuMiktariText
        detailInfo.kalori = "0"
        productList.add(detailInfo)

        headerInfo.list = productList

    }

    private fun updateEgzersizText() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val selectedEgzersizAdi = sharedPreferences.getString("selectedEgzersizAdi", "")
        val selectedYakilanKalori = sharedPreferences.getInt("selectedYakilanKalori", 0)

        var headerInfo = subjects["Egzersiz Ekle"]
        if (headerInfo == null) {
            headerInfo = GroupInfo()
            headerInfo.name = "Egzersiz Ekle"
            subjects["Egzersiz Ekle"] = headerInfo
            deptList.add(headerInfo)
        } else {
            val index = deptList.indexOf(headerInfo)
            if (index != -1) {
                deptList[index] = headerInfo
            }
        }

        val productList = headerInfo.list

        if (productList.none { it.name == selectedEgzersizAdi }) {
            val detailInfo = ChildInfo()
            detailInfo.sequence = (productList.size + 1).toString()
            detailInfo.name = selectedEgzersizAdi!!
            detailInfo.kalori = selectedYakilanKalori.toString()
            productList.add(detailInfo)
        } else {
            Log.d("Egzersiz", "Already added: Egzersiz: $selectedEgzersizAdi, Yakılan Kalori: $selectedYakilanKalori")
        }

        headerInfo.list = productList
        productList.forEach { childInfo ->
            Log.d("ProductInfo", "Egzersiz: ${childInfo.name}, Kalori: ${childInfo.kalori}")
        }
    }


    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                binding.datePickerEditText.setText(selectedDate)
                val selectedItems = (activity as MainActivity).getSelectedItems()
                //handleDateSelection(selectedDate, selectedItems)
                Log.d("Firestore", "Selected items: $selectedItems")
            },
            2024,
            1,
            21
        )
        datePickerDialog.show()
    }

    private fun expandAll() {
        val count = listAdapter.groupCount
        for (i in 0 until count) {
            simpleExpandableListView.expandGroup(i)
        }
    }

    fun loadData(selectedItems: List<Besin>) {
        val sharedPreferences =
            requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val clickedCategory = sharedPreferences.getString("clickedCategory", "default_value")
        Log.d("loadData", "kategori: $clickedCategory")

        val kahvaltiList =
            SharedPreferencesUtil.getBesinList(requireContext(), "Kahvalti")?.toMutableList()
                ?: mutableListOf()
        val ogleYemegiList =
            SharedPreferencesUtil.getBesinList(requireContext(), "OgleYemegi")?.toMutableList()
                ?: mutableListOf()
        val aksamYemegiList =
            SharedPreferencesUtil.getBesinList(requireContext(), "AksamYemegi")?.toMutableList()
                ?: mutableListOf()
        val araOgunEkleList =
            SharedPreferencesUtil.getBesinList(requireContext(), "AraOgunEkle")?.toMutableList()
                ?: mutableListOf()
        val suEkleList =
            mutableListOf<Besin>()
        //val egzersizEkleList = SharedPreferencesUtil.getBesinList(requireContext(), "EgzersizEkle")?.toMutableList() ?: mutableListOf()
        val digerList =
            SharedPreferencesUtil.getBesinList(requireContext(), "Diger")?.toMutableList()
                ?: mutableListOf()

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
                    // suEkleList.add(item)
                }

                "Egzersiz Ekle" -> {
                    // egzersizEkleList.add(item)
                }

                else -> {
                    digerList.add(item)
                }
            }
        }

        SharedPreferencesUtil.saveBesinList(requireContext(), "Kahvalti", kahvaltiList)
        SharedPreferencesUtil.saveBesinList(requireContext(), "OgleYemegi", ogleYemegiList)
        SharedPreferencesUtil.saveBesinList(requireContext(), "AksamYemegi", aksamYemegiList)
        SharedPreferencesUtil.saveBesinList(requireContext(), "AraOgunEkle", araOgunEkleList)
        // SharedPreferencesUtil.saveBesinList(requireContext(), "SuEkle", suEkleList)
        //SharedPreferencesUtil.saveBesinList(requireContext(), "EgzersizEkle", egzersizEkleList)
        SharedPreferencesUtil.saveBesinList(requireContext(), "Diger", digerList)

        addProduct("Kahvaltı", kahvaltiList)
        addProduct("Öğle Yemeği", ogleYemegiList)
        addProduct("Akşam Yemeği", aksamYemegiList)
        addProduct("Ara Öğün Ekle", araOgunEkleList)
        // addProduct("Su Ekle", suEkleList)
        //addProduct("Egzersiz Ekle", egzersizEkleList)


        val selectedDate = binding.datePickerEditText.text.toString()
        //handleDateSelection(selectedDate, selectedItems)

        updateSuMiktariText()
        updateEgzersizText()
        updateRemainingCalories()
        customAdapter.notifyDataSetChanged()
    }


    private fun addProduct(department: String, selectedItems: List<Besin>) {
        var headerInfo = subjects[department]
        if (headerInfo == null) {
            headerInfo = GroupInfo()
            headerInfo.name = department
            subjects[department] = headerInfo
            deptList.add(headerInfo)
        } else {
            val index = deptList.indexOf(headerInfo)
            if (index != -1) {
                deptList[index] = headerInfo
            }
        }

        val productList = headerInfo.list

        selectedItems.forEachIndexed { index, item ->
            if (productList.none { it.name == item.besinAdi }) {
                val detailInfo = ChildInfo()
                detailInfo.sequence = (index + 1).toString()
                detailInfo.name = item.besinAdi
                detailInfo.kalori = if (department == "Su Ekle") "0" else item.kalori.toString()
                productList.add(detailInfo)
            }
        }

        headerInfo.list = productList
        productList.forEach { childInfo ->
            Log.d("ProductInfo", "Department: $department, Name: ${childInfo.name}, Kalori: ${childInfo.kalori}")
        }
    }




    private fun updateRemainingCalories() {
        val totalCaloriesIn = calculateTotalCaloriesIn()
        val remainingCalories = 2000 - totalCaloriesIn
        binding.txtKalanKalori.text = remainingCalories.toString()
    }

    private fun calculateTotalCaloriesIn(): Int {
        var totalCaloriesIn = 0
        deptList.forEach { groupInfo ->
            groupInfo.list.forEach { childInfo ->
                totalCaloriesIn += childInfo.kalori.toIntOrNull() ?: 0
            }
        }
        return totalCaloriesIn
    }
}

