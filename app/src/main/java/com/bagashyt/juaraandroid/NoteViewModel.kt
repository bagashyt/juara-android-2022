package com.bagashyt.juaraandroid

import android.util.Log
import androidx.lifecycle.*
import com.bagashyt.juaraandroid.note.Note
import com.bagashyt.juaraandroid.note.NoteDao
import kotlinx.coroutines.launch

class NoteViewModel(private val noteDao: NoteDao) : ViewModel() {

    val allNote: LiveData<List<Note>> = noteDao.getNotes().asLiveData()

    fun isNoteAvailable(note: Note): Boolean {

        if (note.id < 0){
            return false
        }
        return true
    }

    fun addNewNote(noteTitle: String, noteText: String) {
        val newNote = getNewNote(noteTitle, noteText)
        insertNote(newNote)
        Log.d("viewmodel","NEW NOTE ADDED")
    }

    fun updateNote(
        noteId: Int,
        noteTitle: String,
        noteText: String
    ){
        val updatedNote = getUpdatedNote(noteId, noteTitle, noteText)
        updateItemScope(updatedNote)
        Log.d("viewmodel","NOTE UPDATED")
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }

    fun retrieveNote(id: Int): LiveData<Note>{
        return noteDao.getNote(id).asLiveData()
    }

    fun isEntryValid(noteTitle: String, noteText: String): Boolean{
        if (noteTitle.isBlank() || noteText.isNotBlank()) {
            Log.d("viewmodel","ENTRY INVALID")
            return false
        }
        Log.d("viewmodel","ENTRY VALID")
        return true
    }

    private fun updateItemScope(note: Note){
        viewModelScope.launch {
            noteDao.update(note)
        }
    }

    private fun insertNote(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    private fun getNewNote(noteTitle: String, noteText: String): Note {
        return Note(
            noteTitle = noteTitle,
            noteText = noteText
        )
    }

    private fun getUpdatedNote(noteId: Int, noteTitle: String, noteText: String): Note {
        return Note(
            id = noteId,
            noteTitle = noteTitle,
            noteText = noteText
        )
    }
}

class NoteViewModelFactory(private val noteDao: NoteDao): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}