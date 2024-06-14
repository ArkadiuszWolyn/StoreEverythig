package com.storeeverythin.service;

import com.storeeverythin.model.NoteEntity;
import com.storeeverythin.model.UserEntity;
import com.storeeverythin.repository.NoteRepository;
import com.storeeverythin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public List<NoteEntity> findByUserId(long userId) {
        return noteRepository.findByUserId(userId);
    }
    
    public void deleteNoteById(long id) {
        noteRepository.deleteById(id);
    }


    public List<NoteEntity> findByTitle(String title) {
        return noteRepository.findByTitle(title);
    }

    public NoteEntity findById(long noteId) {
        return noteRepository.findById(noteId);
    }

    public NoteEntity save(NoteEntity note) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        note.setUser(user);
        return noteRepository.save(note);
    }
    
    public void editNote(NoteEntity note) {
        noteRepository.save(note);
    }

    public void deleteNote(long id) {
        noteRepository.deleteById(id);
    }

    public List<NoteEntity> getAllNotes() {
        return noteRepository.findAll();
    }
}
