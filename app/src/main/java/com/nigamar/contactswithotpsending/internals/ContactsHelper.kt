package com.nigamar.contactswithotpsending.internals

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nigamar.contactswithotpsending.data.db.entities.Contact
import com.nigamar.contactswithotpsending.data.repository.AppRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.BufferedReader
import java.io.InputStreamReader

class ContactsHelper(private val appRepository: AppRepository,val context: Context){

    private fun getListOfContacts():Deferred<List<Contact>>{
        return GlobalScope.async {
            val inputStreamReader= InputStreamReader(context.assets.open("contacts.json"))
            val bufferedReader= BufferedReader(inputStreamReader)
            val gson: Gson = Gson()
            val listType =  object: TypeToken<ArrayList<Contact>>(){}.type
            val contactList= gson.fromJson(bufferedReader,listType) as List<Contact>
            contactList
        }
    }

    suspend fun loadContacts(){
        val contactList:List<Contact> =getListOfContacts().await()
        contactList.forEach {
            appRepository.insertContact(it)
        }
    }
}