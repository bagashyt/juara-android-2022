package com.bagashyt.juaraandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagashyt.juaraandroid.databinding.ItemNoteListBinding
import com.bagashyt.juaraandroid.note.Note

class NoteListAdapter(private val onItemClicked: (Note) -> Unit) :
    ListAdapter<Note,NoteListAdapter.NoteViewHolder>(DiffCallback){

        class NoteViewHolder(private var binding: ItemNoteListBinding) :
                RecyclerView.ViewHolder(binding.root){
                    fun bind(note: Note){
                        binding.itemNoteTittleNote.text = note.noteTitle
                        binding.itemNoteTextNote.text = note.noteText
                    }
                }

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.noteTitle == newItem.noteTitle
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
       return NoteViewHolder(
           ItemNoteListBinding.inflate(
               LayoutInflater.from(
                   parent.context
               )
           )
       )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }
}