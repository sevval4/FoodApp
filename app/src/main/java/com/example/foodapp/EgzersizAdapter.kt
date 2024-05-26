import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.model.Egzersiz

class EgzersizAdapter(private val context: Context) :
    RecyclerView.Adapter<EgzersizAdapter.EgzersizViewHolder>() {

    private val selectedItems: MutableList<Egzersiz> = mutableListOf()
    private var onItemSelectedListener: OnItemSelectedListener? = null
    private var egzersizList: List<Egzersiz> = emptyList()
    private var selectedCount: Int = 0

    fun setData(newData: List<Egzersiz>) {
        egzersizList = newData
        notifyDataSetChanged()
    }

    interface OnItemSelectedListener {
        fun onItemSelectedCountChanged(count: Int)
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener) {
        onItemSelectedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EgzersizViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_egzersiz, parent, false)
        return EgzersizViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EgzersizViewHolder, position: Int) {
        val currentItem = egzersizList[position]
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            Log.d("EgzersizAdapter", "Tıklanan egzersiz: ${currentItem.egzersizAdi}")

            holder.checkBox.isChecked = !holder.checkBox.isChecked // CheckBox durumunu güncelle
        }

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                selectedItems.add(currentItem)
                selectedCount++
                Log.d("EgzersizAdapter", "Seçilen egzersiz: ${currentItem.egzersizAdi}")
            } else {
                selectedItems.remove(currentItem)
                selectedCount--
            }
            onItemSelectedListener?.onItemSelectedCountChanged(selectedCount)
        }
    }


    override fun getItemCount() = egzersizList.size

    fun getSelectedItems(): List<Egzersiz> {
        return selectedItems
    }


    inner class EgzersizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val egzersizAdiTextView: TextView = itemView.findViewById(R.id.egzersizAdiTextView)
        private val setSayisiTextView: TextView = itemView.findViewById(R.id.setSayisiTextView)
        private val yakilanKaloriTextView: TextView = itemView.findViewById(R.id.yakilanKaloriTextView)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox_egzersiz)

        fun bind(egzersizItem: Egzersiz) {
            egzersizAdiTextView.text = egzersizItem.egzersizAdi
            setSayisiTextView.text = "${egzersizItem.setSayisi} Set"
            yakilanKaloriTextView.text = "${egzersizItem.yakilanKalori} Kcal"
        }
    }
}
