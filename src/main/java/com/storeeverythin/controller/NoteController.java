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

    @PutMapping("/{id}")
    public void editNote(@PathVariable long id, @RequestBody NoteEntity note) {
        note.setId(id);
        noteService.editNote(note);
    }

    @DeleteMapping("/{id}")
    public String deleteNote(
            @PathVariable long id,
            Model model
    ) {
        System.out.println("delete");
        try {
            noteService.deleteNote(id);

            List<NoteEntity> notes = noteService.getAllNotes();

            model.addAttribute("notes", notes);
            model.addAttribute("noteToDelete", new NoteEntity());
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
