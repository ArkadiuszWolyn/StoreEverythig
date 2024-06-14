package com.storeeverythin;

import com.storeeverythin.controller.NoteController;
import com.storeeverythin.model.NoteEntity;
import com.storeeverythin.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class NoteControllerTest {

    @InjectMocks
    private NoteController noteController;

    @Mock
    private NoteService noteService;

    @Mock
    private Model model;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
    }


    @Test
    public void testEditNote() throws Exception {
        NoteEntity note = new NoteEntity();
        note.setTitle("Updated Note");
        note.setContent("Updated Content");

        mockMvc.perform(put("/notes/{id}", 1L)
                        .contentType("application/json")
                        .content("{\"title\":\"Updated Note\",\"content\":\"Updated Content\"}"))
                .andExpect(status().isOk());

        verify(noteService, times(1)).editNote(any(NoteEntity.class));
    }

    @Test
    public void testDeleteNote() throws Exception {
        mockMvc.perform(post("/notes/delete/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));

        verify(noteService, times(1)).deleteNoteById(1L);
    }


    @Test
    public void testAddNewNote() throws Exception {
        mockMvc.perform(get("/notes/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("add_note.html"))
                .andExpect(model().attributeExists("noteForm"));
    }
}