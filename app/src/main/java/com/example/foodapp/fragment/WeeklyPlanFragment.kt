package com.example.foodapp.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodapp.databinding.FragmentHaftalikPlanBinding

class WeeklyPlanFragment : Fragment() {
    private lateinit var binding: FragmentHaftalikPlanBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHaftalikPlanBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences =
            requireActivity().getSharedPreferences("HaftalikPlanData", Context.MODE_PRIVATE)

        binding.editTextTextMultiLine3.setText(sharedPreferences.getString("text1", ""))
        binding.editTextTextMultiLine4.setText(sharedPreferences.getString("text2", ""))
        binding.editTextTextMultiLine5.setText(sharedPreferences.getString("text3", ""))
        binding.editTextTextMultiLine7.setText(sharedPreferences.getString("text4", ""))
        binding.editTextTextMultiLine8.setText(sharedPreferences.getString("text5", ""))
    }
    override fun onPause() {
        super.onPause()
        saveTextToSharedPreferences()
    }
    override fun onResume() {
        super.onResume()

        binding.editTextTextMultiLine3.setText(sharedPreferences.getString("text1", ""))
        binding.editTextTextMultiLine4.setText(sharedPreferences.getString("text2", ""))
        binding.editTextTextMultiLine5.setText(sharedPreferences.getString("text3", ""))
        binding.editTextTextMultiLine7.setText(sharedPreferences.getString("text4", ""))
        binding.editTextTextMultiLine8.setText(sharedPreferences.getString("text5", ""))
    }
    private fun saveTextToSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.putString("text1", binding.editTextTextMultiLine3.text.toString())
        editor.putString("text2", binding.editTextTextMultiLine4.text.toString())
        editor.putString("text3", binding.editTextTextMultiLine5.text.toString())
        editor.putString("text4", binding.editTextTextMultiLine7.text.toString())
        editor.putString("text5", binding.editTextTextMultiLine8.text.toString())
        editor.apply()
    }
}