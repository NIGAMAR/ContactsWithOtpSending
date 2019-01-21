package com.nigamar.contactswithotpsending.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nigamar.contactswithotpsending.data.db.dao.ContactsDao
import com.nigamar.contactswithotpsending.data.db.dao.MessagesDao
import com.nigamar.contactswithotpsending.data.db.entities.Contact
import com.nigamar.contactswithotpsending.data.db.entities.SentMessage
import com.nigamar.contactswithotpsending.internals.RoomDateTimeConverter

@Database(entities = arrayOf(Contact::class,SentMessage::class),version = 1)
@TypeConverters(RoomDateTimeConverter::class)
abstract class AppDb:RoomDatabase() {
    abstract fun getContactsDao():ContactsDao
    abstract fun getMessageDao():MessagesDao
    companion object {
        @Volatile private var INSTANCE:AppDb?=null
        private val LOCK=Any()
        fun getAppDb(context: Context):AppDb{
             synchronized(LOCK){
                 if (INSTANCE==null)
                     buildDatabase(context).also { appDb->
                         INSTANCE=appDb
                     }
             }
             return INSTANCE!!
         }

        fun buildDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            AppDb::class.java,
            "contacts.db"
        ).build()
    }
}