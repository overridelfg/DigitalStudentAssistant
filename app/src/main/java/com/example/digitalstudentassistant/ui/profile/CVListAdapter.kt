package com.example.digitalstudentassistant.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.domain.models.CV

class CVListAdapter(val click: (CV) -> Unit) : RecyclerView.Adapter<CVListAdapter.ViewHolder>() {

    var cVList: MutableList<CV> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cv, parent, false)
        return CVListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView.text = cVList[position].nameCV
        holder.itemView.setOnClickListener {
            click.invoke(cVList[position])
        }
    }

    override fun getItemCount(): Int {
        return cVList.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.cVTitleTextView)
    }
}