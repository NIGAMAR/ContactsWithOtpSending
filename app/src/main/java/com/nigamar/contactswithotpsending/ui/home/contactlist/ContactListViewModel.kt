package com.nigamar.contactswithotpsending.ui.home.contactlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.nigamar.contactswithotpsending.data.db.entities.Contact
import com.nigamar.contactswithotpsending.data.repository.AppRepository

class ContactListViewModel(private val appRepository: AppRepository): ViewModel() {

 suspend fun getAllContacts():LiveData<List<Contact>>{
    return appRepository.getAllContacts().await()
 }
}
