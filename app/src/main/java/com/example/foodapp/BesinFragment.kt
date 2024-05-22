package com.example.foodapp

import BesinAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.model.Besin
import com.google.firebase.firestore.FirebaseFirestore


class BesinFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BesinAdapter
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
        adapter = BesinAdapter(requireContext()) // Context'i BesinAdapter'a geçir
        recyclerView.adapter = adapter
        arguments?.getSerializable("besinList")?.let { besinList ->
            adapter.setData(besinList as List<Besin>)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setOnItemSelectedListener(object : BesinAdapter.OnItemSelectedListener {
            override fun onItemSelectedCountChanged(count: Int) {
                view.findViewById<TextView>(R.id.selected_text)?.text = count.toString()
            }
        })
        view.findViewById<CheckBox>(R.id.checkBox2)?.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                val selectedItems = adapter.getSelectedItems()
                selectedItemsListener.onSelectedItemsList(selectedItems)
            }
        }
    }

    interface SelectedItemsListener {
        fun onSelectedItemsList(selectedItems: List<Besin>)
    }




}




