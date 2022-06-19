package com.example.targertchat.data.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IContactDao {
    @Query("SELECT * FROM contact")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM contact WHERE contactID = :contactID")
    Contact getContactByID(String contactID);

    @Insert
    void insert(Contact... contacts);

    @Insert
    void insertAll(List<Contact> contacts);

    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact... contacts);

    @Query("DELETE FROM contact")
    public void clear();
}
