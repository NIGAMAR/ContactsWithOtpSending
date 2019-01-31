package com.nigamar.contactswithotpsending.ui.info.composemessage

import androidx.lifecycle.ViewModel;
import com.nigamar.contactswithotpsending.data.db.entities.SentMessage
import com.nigamar.contactswithotpsending.data.network.MessageObj
import com.nigamar.contactswithotpsending.data.repository.AppRepository

class ComposeMessageViewModel(private val appRepository: AppRepository) : ViewModel() {
    suspend fun insertMesage(message: SentMessage){
        appRepository.insertMessage(message)
    }

    suspend fun sendTextMessage(messageObj: MessageObj){
        appRepository.sendTextMessage(messageObj)
    }
}
