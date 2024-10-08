package com.example.foodapp.fragment

import com.example.foodapp.adapter.FoodAdapter
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.model.Besin


class FoodFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodAdapter
    private lateinit var selectedItemsListener: SelectedItemsListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SelectedItemsListener) {
            selectedItemsListener = context
        } else {
            throw RuntimeException("$context  SelectedItemsListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_besin, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        arguments?.getSerializable("besinList")?.let { besinList ->
            adapter = FoodAdapter(requireContext(), besinList as List<Besin>)
            recyclerView.adapter = adapter
        }
        arguments?.getSerializable("besinList")?.let { besinList ->
            adapter.setData(besinList as List<Besin>)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtArama = view.findViewById<EditText>(R.id.txt_arama)

        txtArama.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s)
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        adapter.setOnItemSelectedListener(object : FoodAdapter.OnItemSelectedListener {
            override fun onItemSelectedCountChanged(count: Int) {
                view.findViewById<TextView>(R.id.selected_text)?.text = count.toString()
            }
        })
        view.findViewById<CheckBox>(R.id.checkBox2)?.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                val selectedItems = adapter.getSelectedItems()
                selectedItemsListener.onSelectedItemsList(selectedItems)
                Log.d("BesinFragment", "Seçilen öğeler: $selectedItems")
            }
        }

    }

    interface SelectedItemsListener {
        fun onSelectedItemsList(selectedItems: List<Besin>)
    }




}




