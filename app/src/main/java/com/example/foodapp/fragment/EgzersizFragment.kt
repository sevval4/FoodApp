package com.example.foodapp.fragment

import com.example.foodapp.adapter.EgzersizAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.model.Egzersiz
import com.google.firebase.firestore.FirebaseFirestore



class EgzersizFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EgzersizAdapter
    private lateinit var selectedEgzersizItemsListener: SelectedEgzersizListener
    private val db = FirebaseFirestore.getInstance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SelectedEgzersizListener) {
            selectedEgzersizItemsListener = context
        } else {
            throw RuntimeException("$context must implement SelectedEgzersizListener")
        }
    }



    interface SelectedEgzersizListener {
        fun onSelectedEgzersizList(selectedEgzersiz: List<Egzersiz>)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_egzersiz, container, false)
        recyclerView = view.findViewById(R.id.egzersizRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = EgzersizAdapter(requireContext())
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadEgzersizData()

        adapter.setOnItemSelectedListener(object : EgzersizAdapter.OnItemSelectedListener {
            override fun onItemSelectedCountChanged(count: Int) {
                view.findViewById<TextView>(R.id.selected_text)?.text = count.toString()
            }
        })

        view.findViewById<CheckBox>(R.id.checkBox2)?.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                val selectedItems = adapter.getSelectedItems()
                selectedEgzersizItemsListener.onSelectedEgzersizList(selectedItems)
                Log.d("EgzersizFragment", "CheckBox işaretlendi. Seçilen egzersizler: $selectedItems")

                for (selectedEgzersiz in selectedItems) {
                    updateEgzersiz(selectedEgzersiz.egzersizAdi, selectedEgzersiz.yakilanKalori)
                }
            }
        }

    }

    private fun loadEgzersizData() {
        db.collection("Egzersiz")
            .get()
            .addOnSuccessListener { documents ->
                val egzersizList = mutableListOf<Egzersiz>()
                for (document in documents) {
                    val egzersizAdi = document.getString("Egzersiz Adı") ?: ""
                    val setSayisi = document.getLong("Set Sayısı")?.toInt() ?: 0
                    val yakilanKalori = document.getLong("Yakılan Kalori")?.toInt() ?: 0
                    val egzersiz = Egzersiz(egzersizAdi, setSayisi, yakilanKalori)
                    egzersizList.add(egzersiz)
                }
                adapter.setData(egzersizList)
            }
            .addOnFailureListener { exception ->
                Log.w("EgzersizFragment", "Error getting documents: ", exception)
            }
    }
    private fun updateEgzersiz(selectedEgzersizAdi: String, selectedYakilanKalori: Int) {

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("selectedEgzersizAdi", selectedEgzersizAdi)
        editor.putInt("selectedYakilanKalori", selectedYakilanKalori)
        editor.apply()

        val mainFragment = requireActivity().supportFragmentManager.findFragmentByTag("MainFragment") as? MainFragment
        mainFragment?.updateSuMiktariText()
    }

    interface SelectedEgzersizItemsListener {
        fun onSelectedEgzersizItemsList(selectedItems: List<Egzersiz>)
    }
}