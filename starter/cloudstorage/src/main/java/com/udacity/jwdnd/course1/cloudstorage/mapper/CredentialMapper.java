package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getOneCredential(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getUsersCredentials(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url} AND username = #{username} AND userId = #{userId}")
    Credential getConflictingCredential(String url, String username, Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int saveCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, userName = #{username}, key = #{key}, password = #{password} WHERE credentialId = #{credentialId}")
    int updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int deleteCredential(Integer credentialId);
}
