package com.repo.contentproviderapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.repo.contentproviderapp.Model.RandomTextData
import com.repo.contentproviderapp.R

class RandomTextAdapter(
    private val items: MutableList<RandomTextData>,
    private val onDeleteClicked: (Int) -> Unit
) : RecyclerView.Adapter<RandomTextAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textInfo: TextView = itemView.findViewById(R.id.textInfo)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_random_text, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textInfo.text = "String: ${item.value}\nLength: ${item.length}\nCreated: ${item.created}"
        holder.btnDelete.setOnClickListener {
            onDeleteClicked(position)
        }
    }
}
