package com.example.foodapp

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BedenTakipFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BedenTakipFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var guncelTarihEditText: EditText
    private lateinit var guncelKolEditText: EditText
    private lateinit var guncelGogusEditText: EditText
    private lateinit var guncelBelEditText: EditText
    private lateinit var guncelKalcaEditText: EditText
    private lateinit var guncelUstBacakEditText: EditText
    private lateinit var guncelBaldırEditText: EditText
    private lateinit var kaydetButton: Button
    private lateinit var tableLayoutBedenTakip: TableLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_beden_takip, container, false)

        guncelTarihEditText = rootView.findViewById(R.id.editTextGuncelTarih)
        guncelKolEditText = rootView.findViewById(R.id.editTextGuncelKol)
        guncelGogusEditText = rootView.findViewById(R.id.editTextGuncelGogus)
        guncelBelEditText = rootView.findViewById(R.id.editTextGuncelBel)
        guncelKalcaEditText = rootView.findViewById(R.id.editTextGuncelKalca)
        guncelUstBacakEditText = rootView.findViewById(R.id.editTextGuncelUstBacak)
        guncelBaldırEditText = rootView.findViewById(R.id.editTextGuncelBaldır)
        kaydetButton = rootView.findViewById(R.id.buttonKaydet)
        tableLayoutBedenTakip = rootView.findViewById(R.id.tableLayoutBedenTakip)

        kaydetButton.setOnClickListener {
            ekleBedenTakip()
        }

        return rootView
    }

    private fun ekleBedenTakip() {
        val tarih = guncelTarihEditText.text.toString()
        val guncelKol = guncelKolEditText.text.toString()
        val guncelGogus = guncelGogusEditText.text.toString()
        val guncelBel = guncelBelEditText.text.toString()
        val guncelKalca = guncelKalcaEditText.text.toString()
        val guncelUstBacak = guncelUstBacakEditText.text.toString()
        val guncelBaldır = guncelBaldırEditText.text.toString()

        val row = TableRow(requireContext())
        val params = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        row.layoutParams = params
        // Tarih
        val tarihTextView = TextView(requireContext())
        tarihTextView.text = tarih
        tarihTextView.gravity = Gravity.CENTER
        tarihTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        row.addView(tarihTextView)

        // Kol
        val kolTextView = TextView(requireContext())
        kolTextView.text = guncelKol
        kolTextView.gravity = Gravity.CENTER
        kolTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        row.addView(kolTextView)

        // Göğüs
        val gogusTextView = TextView(requireContext())
        gogusTextView.text = guncelGogus
        gogusTextView.gravity = Gravity.CENTER
        gogusTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        row.addView(gogusTextView)

        // Kalça
        val kalcaTextView = TextView(requireContext())
        kalcaTextView.text = guncelKalca
        kalcaTextView.gravity = Gravity.CENTER
        kalcaTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        row.addView(kalcaTextView)

        // Üst Bacak
        val ustBacakTextView = TextView(requireContext())
        ustBacakTextView.text = guncelUstBacak
        ustBacakTextView.gravity = Gravity.CENTER
        ustBacakTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        row.addView(ustBacakTextView)

        // Baldır
        val baldirTextView = TextView(requireContext())
        baldirTextView.text = guncelBaldır
        baldirTextView.gravity = Gravity.CENTER
        baldirTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        row.addView(baldirTextView)

        // Bel
        val belTextView = TextView(requireContext())
        belTextView.text = guncelBel
        belTextView.gravity = Gravity.CENTER
        belTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        row.addView(belTextView)



        // TableRow'u TableLayout'a ekle
        tableLayoutBedenTakip.addView(row)


        // EditText alanlarını temizle
        guncelTarihEditText.text.clear()
        guncelKolEditText.text.clear()
        guncelGogusEditText.text.clear()
        guncelBelEditText.text.clear()
        guncelKalcaEditText.text.clear()
        guncelUstBacakEditText.text.clear()
        guncelBaldırEditText.text.clear()
    }
}
