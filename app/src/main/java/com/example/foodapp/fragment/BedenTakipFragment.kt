package com.example.foodapp.fragment

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentBedenTakipBinding


class BedenTakipFragment : Fragment() {

    private lateinit var binding: FragmentBedenTakipBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBedenTakipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonKaydet.setOnClickListener {
            ekleBedenTakip()
        }
    }

    private fun ekleBedenTakip() {
        val tarih = binding.editTextGuncelTarih.text.toString()
        val guncelKol = binding.editTextGuncelKol.text.toString()
        val guncelGogus = binding.editTextGuncelGogus.text.toString()
        val guncelBel = binding.editTextGuncelBel.text.toString()
        val guncelKalca = binding.editTextGuncelKalca.text.toString()
        val guncelUstBacak = binding.editTextGuncelUstBacak.text.toString()
        val guncelBaldir = binding.editTextGuncelBaldR.text.toString()
        val guncelKg = binding.editTextGuncelKg.text.toString()

        val row = TableRow(requireContext()).apply {
            setBackgroundColor(Color.parseColor("#0E101D"))
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
        }

        val textViews = listOf(
            TextView(requireContext()).apply {
                text = tarih
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            },
            TextView(requireContext()).apply {
                text = guncelKol
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            },
            TextView(requireContext()).apply {
                text = guncelGogus
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            },
            TextView(requireContext()).apply {
                text = guncelBel
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            },
            TextView(requireContext()).apply {
                text = guncelKalca
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            },
            TextView(requireContext()).apply {
                text = guncelUstBacak
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            },
            TextView(requireContext()).apply {
                text = guncelBaldir
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            },
            TextView(requireContext()).apply {
                text = guncelKg
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            }
        )

        textViews.forEach { row.addView(it) }

        binding.tableLayoutBedenTakip.addView(row)
        clearInputFields()
    }

    private fun clearInputFields() {
        binding.editTextGuncelTarih.text.clear()
        binding.editTextGuncelKol.text.clear()
        binding.editTextGuncelGogus.text.clear()
        binding.editTextGuncelBel.text.clear()
        binding.editTextGuncelKalca.text.clear()
        binding.editTextGuncelUstBacak.text.clear()
        binding.editTextGuncelBaldR.text.clear()
        binding.editTextGuncelKg.text.clear()
    }

    override fun onResume() {
        super.onResume()
        loadDataFromSharedPreferences()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadDataFromSharedPreferences() {
        val sharedPreferences = requireActivity().getSharedPreferences("BodyMeasurements", Context.MODE_PRIVATE)
        val rowCount = sharedPreferences.getInt("row_count", 0)

        binding.tableLayoutBedenTakip.removeAllViews()
        addHeaderRow() // Add the header row back after clearing the table

        for (i in 0 until rowCount) {
            val row = TableRow(requireContext()).apply {
                setBackgroundColor(Color.parseColor("#0E101D"))
                layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
            }

            for (j in 0 until 8) {
                val cellText = sharedPreferences.getString("cell_${i}_$j", "") ?: ""
                val textView = TextView(requireContext()).apply {
                    text = cellText
                    gravity = Gravity.CENTER
                    setTextColor(Color.WHITE)
                    layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                }
                row.addView(textView)
            }

            binding.tableLayoutBedenTakip.addView(row)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addHeaderRow() {
        val headerRow = TableRow(requireContext()).apply {
            setBackgroundColor(Color.parseColor("#0E101D"))
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            setPadding(10, 10, 10, 10)
        }

        val headers = listOf("Tarih", "Kol", "Göğüs", "Üst Bacak", "KALÇA", "BEL", "BALDIR", "AĞIRLIK")

        headers.forEach { headerText ->
            val textView = TextView(requireContext()).apply {
                text = headerText
                setTextColor(requireContext().getColor(R.color.text_color))
                textSize = 12f
                setTypeface(resources.getFont(R.font.poppins_bold))
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                textAlignment = View.TEXT_ALIGNMENT_VIEW_START
            }
            headerRow.addView(textView)
        }

        binding.tableLayoutBedenTakip.addView(headerRow)
    }

    override fun onPause() {
        super.onPause()
        saveDataToSharedPreferences()
    }

    private fun saveDataToSharedPreferences() {
        val sharedPreferences = requireActivity().getSharedPreferences("BodyMeasurements", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val tableRowCount = binding.tableLayoutBedenTakip.childCount
        editor.putInt("row_count", tableRowCount - 1) // Exclude header row

        for (i in 1 until tableRowCount) { // Start from 1 to skip header row
            val row = binding.tableLayoutBedenTakip.getChildAt(i) as TableRow
            for (j in 0 until row.childCount) {
                val textView = row.getChildAt(j) as TextView
                editor.putString("cell_${i - 1}_$j", textView.text.toString()) // Adjust index for saved rows
            }
        }

        editor.apply()
    }
}
