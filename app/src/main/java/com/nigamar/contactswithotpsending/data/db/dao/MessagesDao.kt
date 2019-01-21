package com.nigamar.contactswithotpsending.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nigamar.contactswithotpsending.data.db.entities.SentMessage

@Dao
interface MessagesDao {
    @Insert
    fun insertMessage(message: SentMessage)

    @Query("select * from messages order by otp_send_time")
    fun getAllSentMessages():LiveData<List<SentMessage>>
}