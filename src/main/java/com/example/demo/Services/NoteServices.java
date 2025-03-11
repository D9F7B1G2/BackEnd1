package com.example.demo.Services;

import com.example.demo.Model.Entity.Note;
import com.example.demo.Model.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class NoteServices {

    @Autowired
    NoteRepository noteRepository;

    public void savenotes(Note note){
        noteRepository.save(note);
    }

}
