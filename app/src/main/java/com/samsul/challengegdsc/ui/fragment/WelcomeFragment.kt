package com.samsul.challengegdsc.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.findNavController
import com.samsul.challengegdsc.R
import com.samsul.challengegdsc.databinding.FragmentWelcomeBinding
import com.samsul.challengegdsc.utils.Preferences

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            this.isEnabled
        }
        callback.handleOnBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Preferences.localPreferences(requireContext())
        binding.btnStart.setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragment_to_homeActivity)
            Preferences.getEditor()?.putBoolean("login",true)?.apply()
            requireActivity().finish()
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}