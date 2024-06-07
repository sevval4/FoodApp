package com.example.foodapp.fragment
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentOyunBinding

class GameFragment : Fragment() {
    private lateinit var binding: FragmentOyunBinding
    private var popupWindow: PopupWindow? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOyunBinding.inflate(inflater, container, false)

        binding.burger.setOnClickListener {
            showPopup()
        }
        binding.icecreamImg.setOnClickListener {
            showPopupIcecream()
        }
        binding.wrapImg.setOnClickListener {
            showPopupWrap()
        }
        binding.donutImg.setOnClickListener {
            showPopupDonat()
        }
        binding.cakeImg.setOnClickListener {
            showPopupCake()
        }
        binding.toastImg.setOnClickListener {
            showPopupToast()
        }

        return binding.root
    }

    private fun showPopup() {
        val popupView = layoutInflater.inflate(R.layout.oyunpopup, null)

        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            isFocusable = true
            setBackgroundDrawable(null)
        }
        popupWindow?.setOnDismissListener {
            popupWindow = null
        }
        popupWindow?.showAtLocation(binding.burger, Gravity.CENTER, 0, 0)
    }
    private fun showPopupIcecream() {
        val popupView = layoutInflater.inflate(R.layout.icecream_popup, null)

        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            isFocusable = true
            setBackgroundDrawable(null)
        }
        popupWindow?.setOnDismissListener {
            popupWindow = null
        }
        popupWindow?.showAtLocation(binding.icecreamImg, Gravity.CENTER, 0, 0)
    }
    private fun showPopupWrap() {
        val popupView = layoutInflater.inflate(R.layout.wrap_popup, null)

        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            isFocusable = true
            setBackgroundDrawable(null)
        }
        popupWindow?.setOnDismissListener {
            popupWindow = null
        }
        popupWindow?.showAtLocation(binding.wrapImg, Gravity.CENTER, 0, 0)
    }

    private fun showPopupDonat() {
        val popupView = layoutInflater.inflate(R.layout.donut_popup, null)

        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            isFocusable = true
            setBackgroundDrawable(null)
        }
        popupWindow?.setOnDismissListener {
            popupWindow = null
        }
        popupWindow?.showAtLocation(binding.donutImg, Gravity.CENTER, 0, 0)
    }
    private fun showPopupCake() {
        val popupView = layoutInflater.inflate(R.layout.cake_popup, null)

        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            isFocusable = true
            setBackgroundDrawable(null)
        }
        popupWindow?.setOnDismissListener {
            popupWindow = null
        }
        popupWindow?.showAtLocation(binding.cakeImg, Gravity.CENTER, 0, 0)
    }

    private fun showPopupToast() {
        val popupView = layoutInflater.inflate(R.layout.toast_popup, null)

        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            isFocusable = true
            setBackgroundDrawable(null)
        }
        popupWindow?.setOnDismissListener {
            popupWindow = null
        }
        popupWindow?.showAtLocation(binding.toastImg, Gravity.CENTER, 0, 0)
    }



    override fun onDestroy() {
        super.onDestroy()
        popupWindow?.dismiss()
    }
}
