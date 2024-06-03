package com.example.foodapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.model.Besin

class BesinAdapter(private val context: Context,private var originalBesinList: List<Besin>) :
    RecyclerView.Adapter<BesinAdapter.BesinViewHolder>() {

    private val selectedItems: MutableList<Besin> = mutableListOf()
    private var onItemSelectedListener: OnItemSelectedListener? = null
    private var besinList: List<Besin> = originalBesinList
    private var selectedCount: Int = 0


    fun setData(newData: List<Besin>) {
        besinList = newData
        notifyDataSetChanged()
    }
    fun filter(query: CharSequence?) {
        val filteredList = mutableListOf<Besin>()
        if (!query.isNullOrEmpty()) {
            val filterPattern = query.toString().toLowerCase().trim()
            for (besin in originalBesinList) {
                if (besin.besinAdi.toLowerCase().contains(filterPattern)) {
                    filteredList.add(besin)
                }
            }
        } else {
            filteredList.addAll(originalBesinList)
        }
        besinList = filteredList
        notifyDataSetChanged()
    }



    interface OnItemSelectedListener {
        fun onItemSelectedCountChanged(count: Int)
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener) {
        onItemSelectedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_besin, parent, false)
        return BesinViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        val currentItem = besinList[position]
        holder.bind(currentItem)

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectedItems.add(currentItem)
                selectedCount++
            } else {
                selectedItems.remove(currentItem)
                selectedCount--
            }
            onItemSelectedListener?.onItemSelectedCountChanged(selectedCount)
            Log.d("com.example.foodapp.adapter.BesinAdapter", "Selected item: ${currentItem.besinAdi}")
        }
    }


    override fun getItemCount() = besinList.size

    fun getSelectedItems(): List<Besin> {
        return selectedItems
    }

    inner class BesinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val besinAdiTextView: TextView = itemView.findViewById(R.id.besinAdiTextView)
        private val kaloriTextView: TextView = itemView.findViewById(R.id.kaloriTextView)
        private val olcuTextView: TextView = itemView.findViewById(R.id.olcuTextView)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        fun bind(besinItem: Besin) {
            besinAdiTextView.text = besinItem.besinAdi
            kaloriTextView.text = "${besinItem.kalori} kalori"
            olcuTextView.text = besinItem.olcu
        }
    }
}