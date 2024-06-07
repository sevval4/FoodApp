package com.example.foodapp.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.foodapp.R
import com.example.foodapp.fragment.ExerciseFragment
import com.example.foodapp.fragment.CategoryFragment
import com.example.foodapp.fragment.WaterFragment
import com.example.foodapp.model.ChildInfo
import example.abhiandroid.expandablelistviewexample.GroupInfo

class CustomAdapter(private val context: Context, private val deptList: ArrayList<GroupInfo>) :
    BaseExpandableListAdapter() {
    private var clickedCategory: String = ""

    //    fun getClickedCategory(): String {
//        return clickedCategory
//    }
    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val productList = deptList[groupPosition].list
        return productList[childPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val detailInfo = getChild(groupPosition, childPosition) as ChildInfo
        if (convertView == null) {
            val infalInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.child_items, null)
        }

        val sequence = convertView!!.findViewById<TextView>(R.id.sequence)
        sequence.text = detailInfo.sequence.trim() + ". "
        val childItem = convertView.findViewById<TextView>(R.id.childItem)
        childItem.text = detailInfo.name.trim()

        if (deptList[groupPosition].name != "Su Ekle") {
            val kalori = convertView.findViewById<TextView>(R.id.kalori)
            kalori.text = "${detailInfo.kalori} kcal"
        } else {
            val kalori = convertView.findViewById<TextView>(R.id.kalori)
            kalori.visibility = View.GONE
        }

        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        val productList = deptList[groupPosition].list
        return productList.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return deptList[groupPosition]
    }

    override fun getGroupCount(): Int {
        return deptList.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(
        groupPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val headerInfo = getGroup(groupPosition) as GroupInfo
        if (convertView == null) {
            val inf = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inf.inflate(R.layout.group_items, null)
        }

        val heading = convertView!!.findViewById<TextView>(R.id.heading)
        val button = convertView.findViewById<ImageView>(R.id.btn_add)
        heading.text = headerInfo.name.trim()
        button.setOnClickListener {
            clickedCategory = headerInfo.name.trim()
            Log.d("CustomAdapter", "TIKLANAN KATEGORİ: $clickedCategory")

            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("clickedCategory", clickedCategory)
            editor.apply()

            val fragment = CategoryFragment()
            val transaction =
                (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        if (headerInfo.name == "Su Ekle") {
            convertView.setOnClickListener {
                val suFragment = WaterFragment()
                val transaction =
                    (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, suFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        if (headerInfo.name == "Egzersiz Ekle") {
            convertView.setOnClickListener {
                val egzersizFragment = ExerciseFragment()
                val transaction =
                    (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, egzersizFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
