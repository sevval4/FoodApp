package com.example.foodapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodapp.model.Besin

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.databinding.FragmentRecordBinding


class RecordFragment : Fragment() {

    private lateinit var binding: FragmentRecordBinding
    private lateinit var recordAdapter: RecordAdapter
    private var besinList: List<Besin> = listOf()
    private var selectedDate: String? = null

    companion object {
        private const val ARG_BESIN_LIST = "besin_list"
        private const val ARG_SELECTED_DATE = "selected_date"

        fun newInstance(besinList: List<Besin>, selectedDate: String): RecordFragment {
            val fragment = RecordFragment()
            val args = Bundle()
            args.putSerializable(ARG_BESIN_LIST, ArrayList(besinList))
            args.putString(ARG_SELECTED_DATE, selectedDate)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            @Suppress("UNCHECKED_CAST")
            besinList = it.getSerializable(ARG_BESIN_LIST) as List<Besin>
            selectedDate = it.getString(ARG_SELECTED_DATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectedDateTextView.text = selectedDate

        recordAdapter = RecordAdapter(requireContext(), besinList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recordAdapter
        }
    }
}