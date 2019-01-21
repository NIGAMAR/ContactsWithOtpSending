package com.nigamar.contactswithotpsending.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nigamar.contactswithotpsending.data.db.entities.Contact

@Dao
interface ContactsDao {

    @Insert
    fun insertContact(contact: Contact)

    @Query("select * from contacts_table")
    fun getAllContacts():LiveData<List<Contact>>

    @Query("select * from contacts_table where id=:contactId")
    fun getContact(contactId:Int):LiveData<Contact>
}