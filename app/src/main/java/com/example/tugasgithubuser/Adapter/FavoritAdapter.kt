package com.example.tugasgithubuser.Adapter
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasgithubuser.UI.Detail
import com.example.tugasgithubuser.database.UserEntity
import com.example.tugasgithubuser.databinding.ItemUserBinding

class FavoriteAdapter :
    ListAdapter<UserEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemName: UserEntity) {
            binding.txtUserName.text = itemName.username
            Glide.with(binding.root)
                .load(itemName.avatarUrl)
                .into(binding.circleImageView)
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, Detail::class.java)
                intentDetail.putExtra("ID", itemName.name)
                intentDetail.putExtra("USERNAME", itemName.username)
                intentDetail.putExtra("AVATAR", itemName.avatarUrl)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}