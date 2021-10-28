package com.cs389team4.needtofeed.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.amplifyframework.core.Amplify
import com.cs389team4.needtofeed.MainActivity
import com.cs389team4.needtofeed.R
import com.cs389team4.needtofeed.databinding.FragmentLoginBinding
import com.cs389team4.needtofeed.utils.Utils

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnBack = binding.btnLoginBack
        btnBack.setOnClickListener {
            // Navigate back
            parentFragmentManager.popBackStack()
        }

        val btnForgotPassword = binding.btnForgotPassword
        btnForgotPassword.setOnClickListener {
            // Navigate to forgot password fragment
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0)
                replace<ForgotPasswordFragment>(R.id.welcome_fragment_container).addToBackStack(null)
            }
        }

        val btnLogin = binding.btnLogin
        btnLogin.setOnClickListener {
            val email = binding.loginInputEmail.text.toString()
            val password = binding.loginInputPassword.text.toString()

            // Sign in with Amplify Auth
            Amplify.Auth.signIn(email, password,
                { result ->
                    // Sign in successful
                    if (result.isSignInComplete) {
                        startActivity(Intent(activity, MainActivity::class.java))
                        requireActivity().finish()
                        // Sign in unsuccessful
                    } else {
                        Utils().showMessage(activity, "Sign in failed")
                    }
                },
                // Sign in error
                { Utils().showMessage(activity, "Sign in error: ${it.message}") }
            )
        }
    }
}
