package com.storeeverythin;

import com.storeeverythin.model.NoteEntity;
import com.storeeverythin.model.UserEntity;
import com.storeeverythin.repository.NoteRepository;
import com.storeeverythin.repository.UserRepository;
import com.storeeverythin.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class NoteServiceTest {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    public void testFindByUserId() {
        List<NoteEntity> notes = new ArrayList<>();
        when(noteRepository.findByUserId(anyLong())).thenReturn(notes);

        List<NoteEntity> result = noteService.findByUserId(1L);

        assertEquals(notes, result);
    }

    @Test
    public void testDeleteNoteById() {
        doNothing().when(noteRepository).deleteById(anyLong());

        noteService.deleteNoteById(1L);

        verify(noteRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testFindByTitle() {
        List<NoteEntity> notes = new ArrayList<>();
        when(noteRepository.findByTitle(anyString())).thenReturn(notes);

        List<NoteEntity> result = noteService.findByTitle("test");

        assertEquals(notes, result);
    }

    @Test
    public void testFindById() {
        NoteEntity note = new NoteEntity();
        when(noteRepository.findById(anyLong())).thenReturn(note);

        NoteEntity result = noteService.findById(1L);

        assertEquals(note, result);
    }

    @Test
    public void testSave() {
        NoteEntity note = new NoteEntity();
        UserEntity user = new UserEntity();
        when(authentication.getPrincipal()).thenReturn(user);
        when(noteRepository.save(any(NoteEntity.class))).thenReturn(note);

        NoteEntity result = noteService.save(note);

        assertEquals(note, result);
        verify(noteRepository, times(1)).save(note);
        assertEquals(user, note.getUser());
    }

    @Test
    public void testDeleteNote() {
        doNothing().when(noteRepository).deleteById(anyLong());

        noteService.deleteNote(1L);

        verify(noteRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testGetAllNotes() {
        List<NoteEntity> notes = new ArrayList<>();
        when(noteRepository.findAll()).thenReturn(notes);

        List<NoteEntity> result = noteService.getAllNotes();

        assertEquals(notes, result);
    }
}