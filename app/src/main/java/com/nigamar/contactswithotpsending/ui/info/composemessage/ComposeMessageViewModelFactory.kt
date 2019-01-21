package com.nigamar.contactswithotpsending.ui.info.composemessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nigamar.contactswithotpsending.data.repository.AppRepository

class ComposeMessageViewModelFactory(private val appRepository: AppRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ComposeMessageViewModel(appRepository) as T
    }
}