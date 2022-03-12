package com.samsul.challengegdsc.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.samsul.challengegdsc.R
import com.samsul.challengegdsc.databinding.FragmentSplashBinding
import com.samsul.challengegdsc.ui.HomeActivity
import com.samsul.challengegdsc.utils.Preferences
import kotlinx.coroutines.*

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    val activityScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Preferences.localPreferences(requireActivity())
        val login = Preferences.getSharedPreferences().getBoolean("login", false)

        Handler(Looper.getMainLooper()).postDelayed({
            if(login) {
                findNavController().navigate(R.id.action_splashFragment_to_homeActivity)
                requireActivity().finish()
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
            }
        }, TIME_LIMITS_SECOND)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    companion object {
        private const val TIME_LIMITS_SECOND = 2500L
    }
}