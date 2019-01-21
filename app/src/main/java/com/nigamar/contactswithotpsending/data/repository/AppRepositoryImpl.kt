package com.nigamar.contactswithotpsending.data.repository

import androidx.lifecycle.LiveData
import com.nigamar.contactswithotpsending.data.db.dao.ContactsDao
import com.nigamar.contactswithotpsending.data.db.dao.MessagesDao
import com.nigamar.contactswithotpsending.data.db.entities.Contact
import com.nigamar.contactswithotpsending.data.db.entities.SentMessage
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AppRepositoryImpl(private val contactsDao: ContactsDao, private val messagesDao: MessagesDao) : AppRepository {

    override suspend fun insertMessage(message: SentMessage) {
        GlobalScope.launch {
            messagesDao.insertMessage(message)
        }
    }

    override suspend fun getAllSentMessages(): Deferred<LiveData<List<SentMessage>>> {
        return GlobalScope.async {
            messagesDao.getAllSentMessages()
        }
    }

    override suspend fun getContactById(contactId: Int): Deferred<LiveData<Contact>> {
        return GlobalScope.async {
            contactsDao.getContact(contactId)
        }
    }

    override suspend fun insertContact(contact: Contact) {
        GlobalScope.launch {
            contactsDao.insertContact(contact)
        }
    }

    override suspend fun getAllContacts(): Deferred<LiveData<List<Contact>>> {
        return GlobalScope.async{
            contactsDao.getAllContacts()
        }
    }
}