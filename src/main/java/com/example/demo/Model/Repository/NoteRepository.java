package com.example.demo.Model.Repository;

import com.example.demo.Model.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long>{
}
