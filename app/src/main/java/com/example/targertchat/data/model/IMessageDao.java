package com.example.targertchat.data.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface IMessageDao {

    @Query("SELECT * FROM Message")
    List<Message> getAllMessages();

    @Query("SELECT * FROM Message WHERE id = :identity")
    Message getMessageById(int identity);

    @Insert
    void insert(Message... message);

    @Insert
    void insertAll(List<Message> messages);

    @Update
    void update(Message... messages);

    @Delete
    void delete(Message... messages);

}
