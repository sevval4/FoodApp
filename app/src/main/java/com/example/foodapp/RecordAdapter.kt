package com.example.foodapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.model.Besin

import com.example.foodapp.databinding.ItemBesinBinding


class RecordAdapter(private val context: Context, private val besinList: List<Besin>) :
    RecyclerView.Adapter<RecordAdapter.BesinViewHolder>() {

    inner class BesinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val besinAdiTextView: TextView = itemView.findViewById(R.id.besinAdiTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
        return BesinViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        val currentItem = besinList[position]
        holder.besinAdiTextView.text = currentItem.besinAdi
    }

    override fun getItemCount() = besinList.size
}