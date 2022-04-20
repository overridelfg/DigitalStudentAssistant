package com.example.digitalstudentassistant.ui.recommendation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.models.responses.ProjectResponse

class ProjectRecommendationListAdapter(var context: Context, val click: (ProjectResponse) -> Unit) : RecyclerView.Adapter<ProjectRecommendationListAdapter.ViewHolder>() {
    var projectsList: MutableList<ProjectResponse> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_projects, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.projectNameTextView.text = projectsList[position].title
        holder.projectDescriptionTextView.text = projectsList[position].description
        holder.itemView.setOnClickListener {
            click.invoke(projectsList[position])
        }

    }

    override fun getItemCount(): Int {
        return projectsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val projectNameTextView: TextView = itemView.findViewById(R.id.projectNameTextView)
        val projectDescriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }
}