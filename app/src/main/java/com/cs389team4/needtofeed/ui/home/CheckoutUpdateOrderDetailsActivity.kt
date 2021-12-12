package com.cs389team4.needtofeed.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.cs389team4.needtofeed.R
import com.cs389team4.needtofeed.databinding.ActivityCheckoutUpdateOrderDetailsBinding

class CheckoutUpdateOrderDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutUpdateOrderDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckoutUpdateOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentType = intent.extras?.getString("fragment")

        when (fragmentType) {
            "contact" -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<CheckoutUpdateContactInfoFragment>(R.id.checkout_order_details_fragment_container)
                }
            }
            "address" -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<CheckoutUpdateDeliveryAddressFragment>(R.id.checkout_order_details_fragment_container)
                }
            }
            "notes" -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<CheckoutUpdateCourierNotesFragment>(R.id.checkout_order_details_fragment_container)
                }
            }
        }
    }
}
