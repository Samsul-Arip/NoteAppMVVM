package com.samsul.challengegdsc.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.samsul.challengegdsc.data.entity.Note

@Dao
interface NoteDao {
    //save data
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    //update data
    @Update
    fun update(note: Note)

    //delete data
    @Delete
    fun delete(note: Note)

    //get data list and detail
    @Query("SELECT * FROM tb_note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    //searching data
    @Query("SELECT * FROM tb_note WHERE title LIKE :text")
    fun getSearchResult(text: String): LiveData<List<Note>>

}