package com.moyahong.githubclient.main

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moyahong.githubclient.R
import com.moyahong.githubclient.base.AppConfig
import com.moyahong.githubclient.model.User
import com.moyahong.githubclient.profile.PersonActivity

/**
 * author: moyahong .
 * date:   On 2022/8/2
 */
class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val avatarIv: ImageView = itemView.findViewById<ImageView>(R.id.iv_avatar)
    val nameTv: TextView = itemView.findViewById<TextView>(R.id.tv_name)
    var user: User? = null

    init {
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, PersonActivity::class.java)
            intent.putExtra(AppConfig.INTENT_USER, user?.login)
            itemView.context.startActivity(intent)
        }
    }

    fun onBind(user: User?) {
        this.user = user
        user?.run {
            avatarUrl?.let {
                Glide.with(itemView)
                    .load(avatarUrl)
//            .onlyRetrieveFromCache(!PrefUtils.isLoadImageEnable())
                    .into(avatarIv)
            }
            nameTv.text = login
        }

    }
}