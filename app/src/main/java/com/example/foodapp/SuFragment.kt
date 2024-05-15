package com.example.foodapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.databinding.FragmentSu2Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SuFragment : Fragment() {
    private lateinit var binding: FragmentSu2Binding

    lateinit var bardaklar: Array<ImageView>
    val emptyGlass = R.drawable.empty_glass
    val filledGlass = R.drawable.bardak_fill


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SuFragment", "onCreateView() called")
        binding = FragmentSu2Binding.inflate(inflater, container, false)
        Log.d("SuFragment", "Layout inflated successfully")

        bardaklar = arrayOf(
            binding.imageView, binding.imageView2, binding.imageView3, binding.imageView7,
            binding.imageView10, binding.imageView11, binding.imageView13, binding.imageView17,
            binding.imageView20, binding.imageView21, binding.imageView23, binding.imageView27,
            binding.imageView30, binding.imageView31, binding.imageView33, binding.imageView37
        )
        bardaklar.forEach { bardak->   bardak.setOnClickListener{
            if(bardak.tag==filledGlass){
                bardak.setImageResource(emptyGlass)
                bardak.tag=emptyGlass
            }else{
                bardak.setImageResource(filledGlass)
                bardak.tag=filledGlass
            }
        }
        }
        return binding.root
    }



}


