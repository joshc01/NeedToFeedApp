package com.cs389team4.needtofeed.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amplifyframework.core.Amplify
import com.cs389team4.needtofeed.databinding.FragmentForgotPasswordConfirmationBinding
import com.cs389team4.needtofeed.utils.Utils

class ForgotPasswordConfirmationFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordConfirmationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnConfirmResetPassword = binding.btnResetPasswordConfirm
        btnConfirmResetPassword.setOnClickListener {
            val confirmCode = binding.resetPasswordConfirmInputCode.text.toString()
            val newPassword = binding.resetPasswordConfirmNewPassword.text.toString()

            // Confirm password reset with Amplify Auth
            Amplify.Auth.confirmResetPassword(newPassword, confirmCode,
                // Password reset successful
                {
                    // Navigate back twice
                    parentFragmentManager.popBackStack()
                    parentFragmentManager.popBackStack()
                },
                // Password reset unsuccessful
                {
                    Utils().showMessage(activity, "Password reset failed: ${it.message}")
                }
            )
        }
    }
}
