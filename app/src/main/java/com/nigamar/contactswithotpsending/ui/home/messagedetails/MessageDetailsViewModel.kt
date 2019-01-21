package com.nigamar.contactswithotpsending.ui.home.messagedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.nigamar.contactswithotpsending.data.db.entities.SentMessage
import com.nigamar.contactswithotpsending.data.repository.AppRepository

class MessageDetailsViewModel(private val appRepository: AppRepository) : ViewModel() {
    suspend fun getAllMessages():LiveData<List<SentMessage>>{
        return appRepository.getAllSentMessages().await()
    }
}
