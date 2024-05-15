import android.content.Intent
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.MainActivity
import com.example.foodapp.MainFragment
import com.example.foodapp.R
import com.example.foodapp.model.Besin

class BesinAdapter(private val context: Context) :
    RecyclerView.Adapter<BesinAdapter.BesinViewHolder>() {
    private var besinList: MutableList<Besin> = mutableListOf()

//    fun setData(besinList: List<Besin>) {
//        this.besinList = besinList
//        notifyDataSetChanged()
//    }

    fun setData(newData: List<Besin>) {

        besinList.clear()
        besinList.addAll(newData)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_besin, parent, false)
        return BesinViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        val currentItem = besinList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = besinList.size

    inner class BesinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val besinAdiTextView: TextView = itemView.findViewById(R.id.besinAdiTextView)
        private val kaloriTextView: TextView = itemView.findViewById(R.id.kaloriTextView)
        private val olcuTextView: TextView = itemView.findViewById(R.id.olcuTextView)
        private val btnEkle: Button = itemView.findViewById(R.id.btn_ekle)

        fun bind(besinItem: Besin) {
            besinAdiTextView.text = besinItem.besinAdi
            kaloriTextView.text = "${besinItem.kalori} kalori"
            olcuTextView.text = besinItem.olcu

            btnEkle.setOnClickListener {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra(
                    "veri",
                    "${besinItem.besinAdi}: ${besinItem.kalori} kalori, ${besinItem.olcu}"
                )
                context.startActivity(intent)


                val yeniBesin = Besin(besinItem.besinAdi, besinItem.kalori, besinItem.olcu, 0)
                besinList.add(yeniBesin)
                notifyItemInserted(besinList.size - 1)
            }

        }
    }
}