package com.submission.githubuser.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.submission.githubuser.ItemsItem
import com.submission.githubuser.R
import com.submission.githubuser.ui.detail.DetailUserActivity
import de.hdodenhof.circleimageview.CircleImageView

class FavoriteUserAdapter (private val listUser: List<ItemsItem>) : RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val user = listUser[position]

        Glide.with(viewHolder.itemView.context)
            .load(user.avatarUrl)
            .apply(RequestOptions())
            .into(viewHolder.civAvatar)
        viewHolder.tvName.text = user.login

        viewHolder.itemView.setOnClickListener{
            val intentDetail = Intent(viewHolder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra(DetailUserActivity.KEY_USER, user.login)
            intentDetail.putExtra(DetailUserActivity.KEY_ID, user.id)
            intentDetail.putExtra(DetailUserActivity.KEY_URL, user.avatarUrl)
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = listUser.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val civAvatar: CircleImageView = view.findViewById(R.id.civ_avatar)
        val tvName: TextView = view.findViewById(R.id.tv_name)
    }
}