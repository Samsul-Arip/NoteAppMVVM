package com.samsul.challengegdsc.data.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tb_note")
@Parcelize
data class Note (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "location")
    var location: String = "Probolinggo, East Java",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "about")
    var about: String = "",

    @ColumnInfo(name = "date")
    var date: String = ""
) : Parcelable