package com.examplemynotes.notes.services;

import java.util.List;
import com.examplemynotes.notes.models.FormNote;
import com.examplemynotes.notes.models.Note;

public interface NoteService {
    List<Note> getAllNotes();

    List<Note> getUserNotes(Long id);

    Note getNote(Long id);

    Note saveNote(FormNote formNote);

    Note editNote(Long id, FormNote formNote);

    void deleteNote(Long id);

}
