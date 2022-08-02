package com.moyahong.githubclient.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moyahong.githubclient.R
import com.moyahong.githubclient.model.User

/**
 * author: moyahong .
 * date:   On 2022/8/2
 */
class UserListAdapter : RecyclerView.Adapter<UserListViewHolder>() {
    var data: List<User>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = View.inflate(parent.context, R.layout.home_search_item, null)
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.onBind(data?.get(position))
    }

    override fun getItemCount(): Int = data?.size ?: 0


}