package com.bagashyt.juaraandroid

import android.app.Application
import com.bagashyt.juaraandroid.note.NoteRoomDatabase

class NoteApplication : Application() {

    val database: NoteRoomDatabase by lazy { NoteRoomDatabase.getDatabase(this) }
}