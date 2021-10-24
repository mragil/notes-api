package com.examplemynotes.notes.repositories;

import java.util.List;
import com.examplemynotes.notes.models.Note;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoteRepo extends JpaRepository<Note, Long> {
    @Query(
            value = "SELECT * FROM user_notes s WHERE s.user_id = ?1 ",
            nativeQuery = true)
    List<Note> findByUser(Long id, Pageable pageable);

}
