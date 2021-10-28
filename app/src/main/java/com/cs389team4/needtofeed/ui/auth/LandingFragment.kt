package com.cs389team4.needtofeed.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cs389team4.needtofeed.R
import com.cs389team4.needtofeed.databinding.FragmentLandingBinding
import com.cs389team4.needtofeed.databinding.FragmentWelcomeObjectBinding
import com.google.android.material.tabs.TabLayoutMediator

class LandingFragment : Fragment() {
    private lateinit var binding: FragmentLandingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLandingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = binding.welcomeViewpager
        val tabLayout = binding.welcomeTabLayout
        // Set viewpager adapter
        viewPager.adapter = WelcomePagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
        }.attach()

        val btnRegister = binding.btnGotoRegister
        btnRegister.setOnClickListener {
            // Navigate to registration fragment
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0)
                replace<RegisterFragment>(R.id.welcome_fragment_container).addToBackStack(null)
            }
        }

        val btnLogin = binding.btnGotoLogin
        btnLogin.setOnClickListener {
            // Navigate to login fragment
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0)
                replace<LoginFragment>(R.id.welcome_fragment_container).addToBackStack(null)
            }
        }
    }
}

class WelcomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3
    // Set fragment position
    override fun createFragment(position: Int): Fragment {
        val fragment = WelcomeObjectFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position + 1)
        }
        return fragment
    }
}

private const val ARG_OBJECT = "object"

class WelcomeObjectFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeObjectBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeObjectBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            // Set fragment text based on position in viewpager
            val descriptionText = binding.textPagerDescription
            descriptionText.text = getInt(ARG_OBJECT).toString()
        }
    }
}
