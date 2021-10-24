package com.examplemynotes.notes.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import com.examplemynotes.notes.models.FormNote;
import com.examplemynotes.notes.models.Note;
import com.examplemynotes.notes.models.User;
import com.examplemynotes.notes.repositories.NoteRepo;
import com.examplemynotes.notes.repositories.UserRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // DI with Lombok
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepo noteRepo;
    private final UserRepo userRepo;

    @Override
    public List<Note> getAllNotes() {

        return noteRepo.findAll();
    }

    @Override
    public List<Note> getUserNotes(Long userId, Pageable paging) {

        return noteRepo.findByUser(userId, paging);
    }

    @Override
    public Note getNote(Long id) {

        return noteRepo.findById(id).get();
    }

    @Override
    public Note saveNote(FormNote formNote) {

        User user = userRepo.findById(formNote.getUserId()).get();

        Note note = new Note(null, formNote.getTitle(), formNote.getDescription(), user);
        return noteRepo.save(note);
    }

    @Override
    public Note editNote(Long id, FormNote formNote) {

        Optional<Note> note = noteRepo.findById(id);

        if (!note.isPresent()) {
            return null; // Note not found
        }

        Note userNote = note.get();

        userNote.setTitle(formNote.getTitle());
        userNote.setDescription(formNote.getDescription());

        return userNote;
    }

    @Override
    public void deleteNote(Long id) {
        Optional<Note> note = noteRepo.findById(id);

        if (!note.isPresent()) {
            // Note not found
        }
        userRepo.deleteById(id);
    }
}
