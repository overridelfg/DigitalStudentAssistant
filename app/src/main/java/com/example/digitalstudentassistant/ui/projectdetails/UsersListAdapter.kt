package com.example.digitalstudentassistant.ui.projectdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.models.responses.UserResponse
import com.example.digitalstudentassistant.domain.models.CV
import com.example.digitalstudentassistant.ui.profile.CVListAdapter

class UsersListAdapter (val click: (UserResponse) -> Unit) : RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {
    var usersList: MutableList<UserResponse> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cv, parent, false)
        return UsersListAdapter.ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView.text =
            usersList[position].firstName + " " + usersList[position].lastName

        holder.itemView.setOnClickListener {
            click.invoke(usersList[position])
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.cVTitleTextView)
    }
}