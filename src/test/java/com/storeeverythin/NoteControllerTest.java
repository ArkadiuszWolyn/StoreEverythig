package com.storeeverythin;
import com.storeeverythin.controller.NoteController;
import com.storeeverythin.model.NoteEntity;
import com.storeeverythin.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class NoteControllerTest {

    @InjectMocks
    private NoteController noteController;

    @Mock
    private NoteService noteService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNote() {
        NoteEntity note = new NoteEntity();
        List<NoteEntity> notes = new ArrayList<>();
        when(noteService.getAllNotes()).thenReturn(notes);

        String result = noteController.addNote(note, bindingResult, model);

        verify(noteService, times(1)).save(any(NoteEntity.class));
        verify(model, times(1)).addAttribute(eq("notes"), eq(notes));
        assertEquals("redirect:/notes", result);
    }

    @Test
    public void testShowEditNoteForm() {
        NoteEntity note = new NoteEntity();
        when(noteService.findById(anyLong())).thenReturn(note);

        String result = noteController.showEditNoteForm(1L, model);

        verify(model, times(1)).addAttribute(eq("noteForm"), eq(note));
        assertEquals("editNote.html", result);
    }

    @Test
    public void testEditNote() {
        NoteEntity note = new NoteEntity();
        NoteEntity existingNote = new NoteEntity();
        existingNote.setPublicationDate(LocalDate.now());
        when(noteService.findById(anyLong())).thenReturn(existingNote);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = noteController.editNote(1L, note, bindingResult, model);

        verify(noteService, times(1)).save(any(NoteEntity.class));
        assertEquals("redirect:/notes", result);
    }

    @Test
    public void testViewNote() {
        NoteEntity note = new NoteEntity();
        when(noteService.findById(anyLong())).thenReturn(note);

        String result = noteController.viewNote(1L, model);

        verify(model, times(1)).addAttribute(eq("note"), eq(note));
        assertEquals("viewNote", result);
    }

    @Test
    public void testDeleteNote() {
        doNothing().when(noteService).deleteNoteById(anyLong());

        String result = noteController.deleteNote(1L, model);

        verify(noteService, times(1)).deleteNoteById(anyLong());
        assertEquals("redirect:/notes", result);
    }

    @Test
    public void testGetAllNotes() {
        List<NoteEntity> notes = new ArrayList<>();
        when(noteService.getAllNotes()).thenReturn(notes);

        String result = noteController.getAllNotes(model);

        verify(model, times(1)).addAttribute(eq("notes"), eq(notes));
        assertEquals("notes", result);
    }

    @Test
    public void testAddNewNote() {
        String result = noteController.addNewNote(model);

        verify(model, times(1)).addAttribute(eq("noteForm"), any(NoteEntity.class));
        assertEquals("add_note.html", result);
    }
}
