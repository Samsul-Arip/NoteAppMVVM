package com.samsul.challengegdsc.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.samsul.challengegdsc.data.entity.Note
import com.samsul.challengegdsc.data.room.NoteDao
import com.samsul.challengegdsc.data.room.NoteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(context: Context) {
    private val noteDao: NoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = NoteDatabase.getDatabase(context)
        noteDao = db.noteDao()
    }

    fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    fun getSearchResult(text: String): LiveData<List<Note>> = noteDao.getSearchResult(text)

    fun insertNote(note: Note) {
        executorService.execute { noteDao.insert(note) }
    }

    fun updateNote(note: Note) {
        executorService.execute { noteDao.update(note) }
    }

    fun deleteNote(note: Note) {
        executorService.execute { noteDao.delete(note) }
    }
}