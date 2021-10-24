package com.examplemynotes.notes.controllers;

import java.net.URI;
import java.util.List;
import com.examplemynotes.notes.models.FormNote;
import com.examplemynotes.notes.models.Note;
import com.examplemynotes.notes.services.NoteService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getUsers() {
        return ResponseEntity.ok().body(noteService.getAllNotes());
    }

    @PostMapping("/notes/save")
    public ResponseEntity<Note> saveUser(@RequestBody FormNote formNote) {
        URI location = URI
                .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/notes/save")
                        .toUriString());
        return ResponseEntity.created(location).body(noteService.saveNote(formNote));
    }

    @GetMapping("/notes/user/{userId}")
    public ResponseEntity<List<Note>> getUserNote(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size, @PathVariable Long userId) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok().body(noteService.getUserNotes(userId, paging));
    }

    @PutMapping("/notes/user/{userId}/note/{noteId}")
    public ResponseEntity<Note> editNote(@PathVariable Long noteId,
            @RequestBody FormNote formNote) {

        return ResponseEntity.ok().body(noteService.editNote(noteId, formNote));
    }

    @DeleteMapping("/notes/user/{userId}/note/{noteId}")
    public void deleteNote(@PathVariable Long noteId) {
        noteService.deleteNote(noteId);
    }

}
