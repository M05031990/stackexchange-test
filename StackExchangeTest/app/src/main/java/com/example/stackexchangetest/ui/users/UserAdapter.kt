package com.example.stackexchangetest.ui.users

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.stackexchangetest.R
import com.example.stackexchangetest.databinding.ItemUserBinding
import com.example.stackexchangetest.model.User

class UserAdapter :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val items = mutableListOf<User>()

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<User>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItems(newItems: List<User>) {
        val start = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(start, newItems.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class UserViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) = with(binding) {
            nameText.text = user.displayName
            reputationText.text = "Reputation: ${user.reputation}"
            userTypeText.text = user.userType
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

            root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, UserDetailActivity::class.java)
                intent.putExtra("user", user) // requires User to be Parcelable
                context.startActivity(intent)
            }
        }
    }
}