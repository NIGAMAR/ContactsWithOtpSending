package com.nigamar.contactswithotpsending.ui.info.contactdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.nigamar.contactswithotpsending.data.db.entities.Contact
import com.nigamar.contactswithotpsending.data.repository.AppRepository

class ContactDetailsViewModel(private val appRepository: AppRepository): ViewModel() {
    suspend fun getContactDetail(contactId:Int):LiveData<Contact>{
        return appRepository.getContactById(contactId).await()
    }
}
