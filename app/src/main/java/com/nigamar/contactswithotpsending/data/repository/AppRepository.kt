package com.nigamar.contactswithotpsending.data.repository

import androidx.lifecycle.LiveData
import com.nigamar.contactswithotpsending.data.db.entities.Contact
import com.nigamar.contactswithotpsending.data.db.entities.SentMessage
import com.nigamar.contactswithotpsending.data.network.MessageObj
import kotlinx.coroutines.Deferred

interface AppRepository {
    suspend fun insertContact(contact: Contact)
    suspend fun getAllContacts():Deferred<LiveData<List<Contact>>>
    suspend fun getContactById(contactId:Int):Deferred<LiveData<Contact>>
    suspend fun insertMessage(message: SentMessage)
    suspend fun sendTextMessage(messageObj: MessageObj)
    suspend fun getAllSentMessages():Deferred<LiveData<List<SentMessage>>>
}