package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int saveNote(Note note, Integer userId){
        note.setUserId(userId);
        note.setNoteId(null);
        return noteMapper.saveNote(note);
    }

    public List<Note> getUserNotes(Integer userId){
        return noteMapper.getUserNotes(userId);
    }

    public boolean isTitleAvailable(Integer userId, String noteTitle){
        return noteMapper.getConflictingNotes(userId, noteTitle) == null;
    }

    public int editNote(Note note){
        return noteMapper.updateNote(note);
    }

    public boolean isDescriptionTooLong(Note note){
        return note.getNoteDescription().length() > 1000;
    }

    public int deleteNote(Integer noteId){
        return noteMapper.deleteNote(noteId);
    }
}
