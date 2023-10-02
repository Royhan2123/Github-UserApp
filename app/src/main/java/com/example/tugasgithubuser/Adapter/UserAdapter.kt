package com.example.tugasgithubuser.Adapter
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasgithubuser.API.UserItem
import com.example.tugasgithubuser.UI.Detail
import com.example.tugasgithubuser.databinding.ItemUserBinding

class UserAdapter(user: List<UserItem>) :
    ListAdapter<UserItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemName: UserItem) {
            binding.txtUserName.text = itemName.login
            Glide.with(binding.root)
                .load(itemName.avatarUrl)
                .into(binding.circleImageView)
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, Detail::class.java)
                intentDetail.putExtra("ID", itemName.id)
                intentDetail.putExtra("USERNAME", itemName.login)
                intentDetail.putExtra("AVATAR", itemName.avatarUrl)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

