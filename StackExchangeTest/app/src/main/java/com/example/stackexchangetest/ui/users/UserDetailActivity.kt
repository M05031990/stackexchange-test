package com.example.stackexchangetest.ui.users

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.stackexchangetest.R
import com.example.stackexchangetest.databinding.ActivityUserDetailBinding
import com.example.stackexchangetest.model.User

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra<User>("user") ?: return

        bindUser(user)
    }

    private fun bindUser(user: User) = with(binding) {
        nameText.text = user.displayName
        reputationText.text = "Reputation: ${user.reputation}"
        userTypeText.text = user.userType ?: "unknown"

        user.badgeCounts?.let {
            bronzeBadge.text = String.format(
                binding.root.context.getString(R.string.bronze_),
                it.bronze.toString()
            )
            silverBadge.text = String.format(
                binding.root.context.getString(R.string.silver_),
                it.silver.toString()
            )
            goldBadge.text = String.format(
                binding.root.context.getString(R.string.gold_),
                it.gold.toString()
            )
        }
        profileImage.load(user.profileImage) {
            crossfade(true)
            placeholder(android.R.drawable.ic_menu_report_image)
            error(android.R.drawable.stat_notify_error)
        }

        profileLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(user.link))
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}