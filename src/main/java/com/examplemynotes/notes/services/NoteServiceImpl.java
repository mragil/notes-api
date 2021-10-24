package com.examplemynotes.notes.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import com.examplemynotes.notes.exception.ResourceNotFoundException;
import com.examplemynotes.notes.exception.UserNotAuthorizedException;
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
    public Note editNote(Long userId, Long noteId, FormNote formNote) {

        Optional<Note> note = noteRepo.findById(noteId);

        if (!note.isPresent()) {
            throw new ResourceNotFoundException("Note with id : " + noteId + " Not Found!");
        }

        Note userNote = note.get();

        if (!userNote.getUser().getId().equals(userId)) {
            throw new UserNotAuthorizedException("You cannot edit other user note!");
        }

        userNote.setTitle(formNote.getTitle());
        userNote.setDescription(formNote.getDescription());

        return userNote;
    }

    @Override
    public void deleteNote(Long userId, Long noteId) {
        Optional<Note> note = noteRepo.findById(noteId);

        if (!note.isPresent()) {
            throw new ResourceNotFoundException("Note with id : " + noteId + "Not Found!");
        }

        Note userNote = note.get();

        if (!userNote.getUser().getId().equals(userId)) {
            throw new UserNotAuthorizedException("You cannot edit other user note!");
        }

        noteRepo.deleteNote(noteId);
    }
}
