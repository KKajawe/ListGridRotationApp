package com.example.sharecareapp.util

import androidx.recyclerview.widget.DiffUtil
import com.example.sharecareapp.model.UserItem

class DiffUtilCallback : DiffUtil.ItemCallback<UserItem>() {
    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.equals(newItem)
    }
}