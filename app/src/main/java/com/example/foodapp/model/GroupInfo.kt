package example.abhiandroid.expandablelistviewexample

import com.example.foodapp.model.ChildInfo
import java.util.ArrayList

class GroupInfo {
    var name: String = ""
    var list: MutableList<ChildInfo> = mutableListOf()
    var isButtonClickable: Boolean = false
}
