package com.example.targertchat.data.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface IMessageDao {

    @Query("SELECT * FROM Message WHERE contactID = :contactID")
    LiveData<List<Message>> getAllMessages(String contactID);

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

    @Query("DELETE FROM Message WHERE contactID = :contactID")
    public void clear(String contactID);
}
