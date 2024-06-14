package com.storeeverythin.service;

import com.storeeverythin.model.NoteEntity;
import com.storeeverythin.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public NoteEntity addNote(NoteEntity note) {
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

    public NoteEntity getNoteById(long id) {
        return noteRepository.findById(id);
    }

    public List<NoteEntity> getNotesByTitle(String title) {
        return noteRepository.findByTitle(title);
    }

}
