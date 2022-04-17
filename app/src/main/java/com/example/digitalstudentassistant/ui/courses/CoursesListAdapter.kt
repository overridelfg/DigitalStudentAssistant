package com.example.digitalstudentassistant.ui.courses

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.models.responses.CourseResponse
import com.example.digitalstudentassistant.data.models.responses.ProjectResponse


class CoursesListAdapter(var context: Context, val click: (CourseResponse) -> Unit) : RecyclerView.Adapter<CoursesListAdapter.ViewHolder>() {
    var coursesList: MutableList<CourseResponse> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.courseNameTextView.text = coursesList[position].name
        holder.courseDescriptionTextView.text = coursesList[position].about
        holder.courseSourceTextView.text = coursesList[position].source
        holder.itemView.setOnClickListener {
            click.invoke(coursesList[position])
        }
    }

    override fun getItemCount(): Int {
        return coursesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val courseNameTextView: TextView = itemView.findViewById(R.id.courseNameTextView)
        val courseSourceTextView: TextView = itemView.findViewById(R.id.sourceTextView)
        val courseDescriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

}