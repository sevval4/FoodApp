import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentOyunBinding

class OyunFragment : Fragment() {
    private lateinit var binding: FragmentOyunBinding
    private var popupWindow: PopupWindow? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOyunBinding.inflate(inflater, container, false)

        binding.imageView1.setOnClickListener {
            showPopup()
        }

        return binding.root
    }

    private fun showPopup() {
        val popupView = layoutInflater.inflate(R.layout.oyunpopup, null)

        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // Popup penceresini gösterme
        popupWindow?.showAtLocation(binding.imageView1, Gravity.CENTER, 0, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        popupWindow?.dismiss()
    }
}
