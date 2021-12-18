package com.cs389team4.needtofeed.ui.auth

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.cs389team4.needtofeed.R
import com.cs389team4.needtofeed.databinding.FragmentRegisterBinding
import com.cs389team4.needtofeed.utils.Utils

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Create clickable links in html text
        val textTermsPrivacy = binding.textTermsPrivacy
        textTermsPrivacy.movementMethod = LinkMovementMethod.getInstance()
        textTermsPrivacy.text = Html.fromHtml(getString(R.string.terms_privacy_agreement), Html.FROM_HTML_MODE_COMPACT)

        val btnBack = binding.btnRegisterBack
        btnBack.setOnClickListener {
            // Navigate back
            findNavController().popBackStack()
        }

        val btnRegister = binding.btnRegister
        val loadingDialog = Utils.createLoadingDialog(context)
        btnRegister.setOnClickListener {

            loadingDialog.show()

            val name = binding.registerInputFullName.text.toString()
            val email = binding.registerInputEmail.text.toString()
            val password = binding.registerInputPassword.text.toString()

            val bundle = Bundle()
            bundle.putString("email", email)
            // Build Amplify user attributes
            val options = AuthSignUpOptions.builder()
                .userAttributes(mutableListOf(
                    AuthUserAttribute(AuthUserAttributeKey.name(), name),
                    AuthUserAttribute(AuthUserAttributeKey.email(), email)
                ))
                .build()

            // Register with Amplify Auth
            Amplify.Auth.signUp(email, password, options,
                // Registration successful
                {
                    loadingDialog.dismiss()
                    findNavController().navigate(RegisterFragmentDirections
                        .actionRegisterFragmentToRegisterConfirmationFragment(email))
                },
                // Registration error
                {
                    loadingDialog.dismiss()

                    Utils.showMessage(activity, "Registration error: ${it.message}")
                    Log.e ("RegisterFragment", "Registration failed", it)
                }
            )
        }
    }
}
