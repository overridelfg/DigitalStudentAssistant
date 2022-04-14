package com.example.digitalstudentassistant.ui.projects

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.data.models.responses.ProjectResponse
import com.example.digitalstudentassistant.domain.models.Project
import com.google.android.material.chip.Chip
import org.w3c.dom.Text

class ProjectsListAdapter(var context: Context, val click: (String) -> Unit) : RecyclerView.Adapter<ProjectsListAdapter.ViewHolder>() {
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
//        holder.projectStatusTextView.text = projectsList[position].tags
        holder.itemView.setOnClickListener {
            click.invoke(projectsList[position].id)
        }
        holder.likeChip.setOnClickListener {
            holder.likeChip.isSelected = !holder.likeChip.isSelected
            if(holder.likeChip.isSelected){
                holder.likeChip.text = (holder.likeChip.text.toString().toInt() + 1).toString()
                holder.likeChip.setChipIconResource(R.drawable.ic_thumb_up)
            }else{
                holder.likeChip.text = (holder.likeChip.text.toString().toInt() - 1).toString()
                holder.likeChip.setChipIconResource(R.drawable.ic_outline_thumb_up)
            }
        }
    }

    override fun getItemCount(): Int {
        return projectsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val projectNameTextView: TextView = itemView.findViewById(R.id.projectNameTextView)
        val projectStatusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        val projectDescriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val likeChip: Chip = itemView.findViewById(R.id.likeChip)
    }

}