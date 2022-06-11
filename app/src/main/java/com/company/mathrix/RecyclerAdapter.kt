package com.company.mathrix

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val users: ArrayList<User>, val context: Context) : RecyclerView.Adapter<RecyclerAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_design, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.userName.text = users.get(position).userName
        holder.userAvatar.setImageResource(users.get(position).userAvatar)
        holder.userScore.text = users.get(position).userScore.toString()
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView
        var userAvatar: ImageView
        var userScore: TextView

        init {
            userName = itemView.findViewById(R.id.cardUserName)
            userAvatar = itemView.findViewById(R.id.cardUserAvatar)
            userScore = itemView.findViewById(R.id.cardUserScore)
        }
    }
}