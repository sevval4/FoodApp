import android.content.Intent
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.MainActivity
import com.example.foodapp.MainFragment
import com.example.foodapp.R
import com.example.foodapp.model.Besin

class BesinAdapter(private val context: Context) :
    RecyclerView.Adapter<BesinAdapter.BesinViewHolder>() {

    private val selectedItems: MutableList<Besin> = mutableListOf()
    private var onItemSelectedListener: OnItemSelectedListener? = null
    private var besinList: List<Besin> = emptyList()
    private var selectedCount: Int = 0


    fun setData(newData: List<Besin>) {
        besinList = newData
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
            Log.d("BesinAdapter", "Selected item: ${currentItem.besinAdi}")
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