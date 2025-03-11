package com.example.demo.Controller;

import com.example.demo.Dto.NoteDto;
import com.example.demo.Model.Entity.Note;
import com.example.demo.Services.NoteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("x-cargo")
@RestController
public class NoteController {

    @Autowired
    NoteServices noteServices;
    @PostMapping("/note")

    public ResponseEntity<?> noteNote(@RequestBody NoteDto noteDto){

        Note note = new Note(
                noteDto.getTipo(),
                noteDto.getAsunto(),
                noteDto.getFecha()

                //noteDto.getId_key
        );

        noteServices.savenotes(note);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
