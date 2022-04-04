package com.example.digitalstudentassistant.ui.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.ui.projects.ProjectsListAdapter

class InterestsListAdapter : RecyclerView.Adapter<InterestsListAdapter.ViewHolder>() {
    var interestsList: MutableList<String> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_interests, parent, false)
        return InterestsListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.interestTextView.text = interestsList[position]
    }

    override fun getItemCount(): Int {
        return interestsList.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val interestTextView : TextView = itemView.findViewById(R.id.interestNameTextView)
    }
}