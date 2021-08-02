package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getOneNote(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getUserNotes(Integer userId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId} AND noteTitle = #{noteTitle}")
    Note getConflictingNotes(Integer userId, String noteTitle);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int saveNote(Note note);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId}")
    int updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int deleteNote(Integer noteId);
}
