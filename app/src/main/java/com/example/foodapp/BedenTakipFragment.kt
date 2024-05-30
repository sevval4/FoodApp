package com.example.foodapp

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
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
        val guncelBaldır = binding.editTextGuncelBaldR.text.toString()

        val row = TableRow(requireContext())
        val params = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        row.layoutParams = params

        val tarihTextView = TextView(requireContext()).apply {
            text = tarih
            gravity = Gravity.CENTER
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        }
        row.addView(tarihTextView)

        val kolTextView = TextView(requireContext()).apply {
            text = guncelKol
            gravity = Gravity.CENTER
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        }
        row.addView(kolTextView)

        val gogusTextView = TextView(requireContext()).apply {
            text = guncelGogus
            gravity = Gravity.CENTER
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        }
        row.addView(gogusTextView)

        val belTextView = TextView(requireContext()).apply {
            text = guncelBel
            gravity = Gravity.CENTER
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        }
        row.addView(belTextView)

        val kalcaTextView = TextView(requireContext()).apply {
            text = guncelKalca
            gravity = Gravity.CENTER
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        }
        row.addView(kalcaTextView)

        val ustBacakTextView = TextView(requireContext()).apply {
            text = guncelUstBacak
            gravity = Gravity.CENTER
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        }
        row.addView(ustBacakTextView)

        val baldirTextView = TextView(requireContext()).apply {
            text = guncelBaldır
            gravity = Gravity.CENTER
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        }
        row.addView(baldirTextView)

        binding.tableLayoutBedenTakip.addView(row)
        binding.editTextGuncelTarih.text.clear()
        binding.editTextGuncelKol.text.clear()
        binding.editTextGuncelGogus.text.clear()
        binding.editTextGuncelBel.text.clear()
        binding.editTextGuncelKalca.text.clear()
        binding.editTextGuncelUstBacak.text.clear()
        binding.editTextGuncelBaldR.text.clear()
    }

}