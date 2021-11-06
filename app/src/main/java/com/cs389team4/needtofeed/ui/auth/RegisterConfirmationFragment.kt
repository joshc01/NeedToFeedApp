package com.cs389team4.needtofeed.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amplifyframework.core.Amplify
import com.cs389team4.needtofeed.MainActivity
import com.cs389team4.needtofeed.databinding.FragmentRegisterConfirmationBinding
import com.cs389team4.needtofeed.utils.Utils

class RegisterConfirmationFragment: Fragment() {
    private lateinit var binding: FragmentRegisterConfirmationBinding
    lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val bundle = this.arguments
        email = bundle!!.getString("email").toString()

        binding = FragmentRegisterConfirmationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnConfirm = binding.btnRegisterConfirm

        btnConfirm.setOnClickListener {
            val confirmCode = binding.registerConfirmInputCode.text.toString()

            // Confirm registration with Amplify Auth
            Amplify.Auth.confirmSignUp(email!!, confirmCode,
                { result ->
                    // Confirmation successful
                    if (result.isSignUpComplete) {
                        startActivity(Intent(activity, MainActivity::class.java))
                        requireActivity().finish()
                        // Confirmation unsuccessful
                    } else {
                        Utils().showMessage(activity, "Registration confirmation error")
                    }
                },
                // Confirmation error
                { Utils().showMessage(activity, "Failed to confirm registration: ${it.message}") }
            )
        }
    }
}
