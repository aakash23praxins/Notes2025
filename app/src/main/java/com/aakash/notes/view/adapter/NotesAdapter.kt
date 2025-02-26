package com.aakash.notes.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aakash.notes.R
import com.aakash.notes.model.data.Notes
import com.aakash.notes.view.AddActivity

class NotesAdapter(private val context: Context) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var notesList: List<Notes> = arrayListOf()

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtDesc: TextView = itemView.findViewById(R.id.txtDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val layoutParams = holder.itemView.layoutParams
        if (layoutParams is ViewGroup.MarginLayoutParams) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            holder.itemView.layoutParams = layoutParams
        }
        val cData = notesList[position]
        holder.txtTitle.text = cData.nTitle
        holder.txtDesc.text = cData.nDesc

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, AddActivity::class.java).apply {
                putExtra("isEdit", true)
                putExtra("id", cData.nId)
            })
        }

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setData(notes: List<Notes>) {
        this.notesList = notes
        notifyDataSetChanged()
    }

    fun filteredItem(notes: List<Notes>) {
        this.notesList = notes
        notifyDataSetChanged()
    }
    fun getPositionDataDelete(position: Int):Notes{
        return notesList[position]
    }
}