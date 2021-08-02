package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getOneFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getUserFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileName = #{fileName} and userId = #{userId}")
    File getConflictingFiles(String fileName, Integer userId);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int saveFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(Integer fileId);
}
