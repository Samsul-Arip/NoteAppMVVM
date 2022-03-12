package com.samsul.challengegdsc.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.samsul.challengegdsc.data.entity.Note
import com.samsul.challengegdsc.repository.NoteRepository

class MainViewModel(context: Context) : ViewModel() {

    private val noteRepository = NoteRepository(context)

    fun insertNote(note: Note) {
        noteRepository.insertNote(note)
    }
    fun updateNote(note: Note) {
        noteRepository.updateNote(note)
    }
    fun deleteNote(note: Note) {
        noteRepository.deleteNote(note)
    }

    fun getAllNotes(): LiveData<List<Note>> = noteRepository.getAllNotes()

    fun getSearchResult(text: String): LiveData<List<Note>> = noteRepository.getSearchResult(text)

}