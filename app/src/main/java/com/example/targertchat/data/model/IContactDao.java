package com.example.targertchat.data.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
/**
 * ContactDao.
 */
public interface IContactDao {
    /**
     * getAllContacts - get all contacts from dao.
     * @return - LiveData consists of all contacts from dao.
     */
    @Query("SELECT * FROM contact")
    LiveData<List<Contact>> getAllContacts();

    /**
     * getContactByID - get specific contact by ID.
     * @param contactID - desired contact
     * @return desired contact.
     */
    @Query("SELECT * FROM contact WHERE contactID = :contactID")
    Contact getContactByID(String contactID);

    /**
     * insert - insert new contact.
     * @param contacts - the contact.
     */
    @Insert
    void insert(Contact... contacts);

    /**
     * insertAll - insert list of contacts to dao.
     * @param contacts - contacts.
     */
    @Insert
    void insertAll(List<Contact> contacts);

    /**
     * update - update an existing contact
     * @param contacts - contacts.
     */
    @Update
    void update(Contact... contacts);

    /**
     * Clear contact list.
     */
    @Query("DELETE FROM contact")
    public void clear();
}
