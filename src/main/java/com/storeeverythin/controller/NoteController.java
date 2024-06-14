package com.storeeverythin.controller;

import com.storeeverythin.model.NoteEntity;
import com.storeeverythin.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public String addNote(
            @Valid NoteEntity note,
            BindingResult bindingResult,
            Model model
    ) {

        note.setPublicationDate(LocalDate.now());
        noteService.save(note);

        List<NoteEntity> notes = noteService.getAllNotes();

        model.addAttribute("notes", notes);
        return "redirect:/notes";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditNoteForm(@PathVariable Long id, Model model) {
        NoteEntity note = noteService.findById(id);
        if (note != null) {
            model.addAttribute("noteForm", note);
        }
        return "editNote.html";
    }

    @PostMapping("/edit/{id}")
    public String editNote(@PathVariable Long id, @Valid @ModelAttribute("noteForm") NoteEntity note, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "editNote.html";
        }
        
        NoteEntity existingNote = noteService.findById(id);
        note.setId(id);
        note.setPublicationDate(existingNote.getPublicationDate()); // Keep the original publication date
        noteService.save(note);
        return "redirect:/notes";
    }

    @GetMapping("{id}")
    public String viewNote(@PathVariable Long id, Model model) {
        NoteEntity note = noteService.findById(id);
        model.addAttribute("note", note);
        return "viewNote";
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(
    		@PathVariable long id,
            Model model
    ) {
        try {
            noteService.deleteNoteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/notes";
    }

    @GetMapping
    public String getAllNotes(Model model) {
        var notes =  noteService.getAllNotes();

        model.addAttribute("notes", notes);

        return "notes";
    }

    @GetMapping("/new")
    public String addNewNote(Model model) {

        model.addAttribute("noteForm", new NoteEntity());


        return "add_note.html";
    }


}
