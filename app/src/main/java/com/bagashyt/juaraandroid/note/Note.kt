package com.bagashyt.juaraandroid.note

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @NonNull @ColumnInfo(name = "title")
    val noteTitle: String,
    @NonNull @ColumnInfo(name = "note")
    val noteText: String
)
