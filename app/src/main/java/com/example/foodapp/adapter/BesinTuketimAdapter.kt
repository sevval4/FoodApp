package com.example.foodapp.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.model.Besin




class BesinTuketimAdapter(private val context: Context, private val besinList: List<Besin>) : RecyclerView.Adapter<BesinTuketimAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtBesinAdi: TextView = itemView.findViewById(R.id.txt_besin_adi)
        val txtKalori: TextView = itemView.findViewById(R.id.txt_kalori)
        val txtOlcu: TextView = itemView.findViewById(R.id.txt_olcu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.besin_tuketim, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val besin = besinList[position]
        holder.txtBesinAdi.text = besin.besinAdi
        holder.txtKalori.text = besin.kalori.toString()
        holder.txtOlcu.text = besin.olcu
    }

    override fun getItemCount(): Int {
        return besinList.size
    }




}