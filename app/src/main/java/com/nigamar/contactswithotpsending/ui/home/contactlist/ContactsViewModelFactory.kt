package com.nigamar.contactswithotpsending.ui.home.contactlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nigamar.contactswithotpsending.data.repository.AppRepository

class ContactsViewModelFactory(private val appRepository: AppRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactListViewModel(appRepository) as T
    }
}