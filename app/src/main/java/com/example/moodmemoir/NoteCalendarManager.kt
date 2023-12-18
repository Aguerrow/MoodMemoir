// NoteCalendarManager.kt

package com.example.moodmemoir

import java.util.Calendar

class NoteCalendarManager {
    private val notesMap: MutableMap<Calendar, String> = mutableMapOf()

    fun addNote(date: Calendar, note: String) {
        notesMap[date] = note
    }

    fun getNotesForDate(date: Calendar): String? {
        return notesMap[date]
    }
}
