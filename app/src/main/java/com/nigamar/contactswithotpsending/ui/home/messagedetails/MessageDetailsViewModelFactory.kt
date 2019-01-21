package com.nigamar.contactswithotpsending.ui.home.messagedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nigamar.contactswithotpsending.data.repository.AppRepository

class MessageDetailsViewModelFactory(private val appRepository: AppRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MessageDetailsViewModel(appRepository) as T
    }
}