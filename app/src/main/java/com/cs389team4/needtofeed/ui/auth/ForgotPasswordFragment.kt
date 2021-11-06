package com.cs389team4.needtofeed.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.amplifyframework.core.Amplify
import com.cs389team4.needtofeed.R
import com.cs389team4.needtofeed.databinding.FragmentForgotPasswordBinding
import com.cs389team4.needtofeed.utils.Utils

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnBack = binding.btnResetPasswordBack
        btnBack.setOnClickListener {
            // Navigate back
            parentFragmentManager.popBackStack()
        }

        val btnResetPassword = binding.btnResetPassword
        btnResetPassword.setOnClickListener {
            val email = binding.resetPasswordInputEmail.text.toString()

            // Send password reset email with Amplify Auth
            Amplify.Auth.resetPassword(email,
                // Sending password reset email successful
                {
                    // Navigate to password reset confirmation fragment
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<ForgotPasswordConfirmationFragment>(R.id.welcome_fragment_container)
                    }
                },
                // Sending password reset email unsuccessful
                {
                    Utils().showMessage(activity, "Sending password reset email failed: ${it.message}")
                }
            )
        }
    }
}
