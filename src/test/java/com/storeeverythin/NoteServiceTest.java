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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testFindByUserId() {
        long userId = 1L;
        List<NoteEntity> notes = Arrays.asList(new NoteEntity(), new NoteEntity());
        when(noteRepository.findByUserId(userId)).thenReturn(notes);

        List<NoteEntity> result = noteService.findByUserId(userId);

        assertEquals(2, result.size());
        verify(noteRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testDeleteNoteById() {
        long noteId = 1L;
        doNothing().when(noteRepository).deleteById(noteId);

        noteService.deleteNoteById(noteId);

        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @Test
    public void testFindByTitle() {
        String title = "Test Title";
        List<NoteEntity> notes = Arrays.asList(new NoteEntity(), new NoteEntity());
        when(noteRepository.findByTitle(title)).thenReturn(notes);

        List<NoteEntity> result = noteService.findByTitle(title);

        assertEquals(2, result.size());
        verify(noteRepository, times(1)).findByTitle(title);
    }

    @Test
    public void testFindById() {
        long noteId = 1L;
        NoteEntity note = new NoteEntity();
        when(noteRepository.findById(noteId)).thenReturn(note);

        NoteEntity result = noteService.findById(noteId);

        assertNotNull(result);
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    public void testSaveNote() {
        NoteEntity note = new NoteEntity();
        UserEntity user = new UserEntity();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(noteRepository.save(note)).thenReturn(note);

        NoteEntity result = noteService.save(note);

        assertNotNull(result);
        assertEquals(user, note.getUser());
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    public void testEditNote() {
        NoteEntity note = new NoteEntity();
        when(noteRepository.save(note)).thenReturn(note);

        noteService.editNote(note);

        verify(noteRepository, times(1)).save(note);
    }

    @Test
    public void testDeleteNote() {
        long noteId = 1L;
        doNothing().when(noteRepository).deleteById(noteId);

        noteService.deleteNoteById(noteId);

        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @Test
    public void testGetAllNotes() {
        List<NoteEntity> notes = Arrays.asList(new NoteEntity(), new NoteEntity());
        when(noteRepository.findAll()).thenReturn(notes);

        List<NoteEntity> result = noteService.getAllNotes();

        assertEquals(2, result.size());
        verify(noteRepository, times(1)).findAll();
    }
}