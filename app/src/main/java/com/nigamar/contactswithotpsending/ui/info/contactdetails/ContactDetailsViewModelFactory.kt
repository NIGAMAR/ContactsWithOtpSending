package com.nigamar.contactswithotpsending.ui.info.contactdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nigamar.contactswithotpsending.data.repository.AppRepository

class ContactDetailsViewModelFactory(private val appRepository: AppRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactDetailsViewModel(appRepository) as T
    }
}