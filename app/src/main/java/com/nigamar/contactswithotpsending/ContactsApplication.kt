package com.nigamar.contactswithotpsending

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.nigamar.contactswithotpsending.data.db.AppDb
import com.nigamar.contactswithotpsending.data.network.NexmoSmsService
import com.nigamar.contactswithotpsending.data.repository.AppRepository
import com.nigamar.contactswithotpsending.data.repository.AppRepositoryImpl
import com.nigamar.contactswithotpsending.internals.ContactsHelper
import com.nigamar.contactswithotpsending.ui.home.contactlist.ContactsViewModelFactory
import com.nigamar.contactswithotpsending.ui.home.messagedetails.MessageDetailsViewModelFactory
import com.nigamar.contactswithotpsending.ui.info.composemessage.ComposeMessageViewModelFactory
import com.nigamar.contactswithotpsending.ui.info.contactdetails.ContactDetailsViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ContactsApplication : Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@ContactsApplication))
        bind() from singleton { AppDb.getAppDb(instance()) }
        bind() from singleton { instance<AppDb>().getContactsDao() }
        bind() from singleton { instance<AppDb>().getMessageDao() }
        bind() from singleton { NexmoSmsService.createService() }
        bind<AppRepository>() with singleton { AppRepositoryImpl(instance(),instance(),instance()) }
        bind() from singleton { ContactsHelper(instance(),instance()) }
        bind() from provider { ContactsViewModelFactory(instance()) }
        bind() from provider { ComposeMessageViewModelFactory(instance()) }
        bind() from provider { MessageDetailsViewModelFactory(instance()) }
        bind() from provider { ContactDetailsViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}