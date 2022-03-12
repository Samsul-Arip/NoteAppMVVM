package com.samsul.challengegdsc.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.samsul.challengegdsc.R
import com.samsul.challengegdsc.databinding.FragmentDescriptionBinding
import com.samsul.challengegdsc.utils.ViewModelFactory
import com.samsul.challengegdsc.viewmodel.MainViewModel

class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance(requireContext())
        ViewModelProvider(requireActivity(), factory)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val idNote = args?.getInt("id_note",0)
        viewModel.getAllNotes().observe(viewLifecycleOwner) {
            if(it!=null) {
                for(item in it) {
                    if(item.id == idNote) {
                        binding.tvDescription.text = item.description
                    }
                }
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}