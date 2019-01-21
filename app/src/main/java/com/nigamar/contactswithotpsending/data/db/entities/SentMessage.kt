package com.nigamar.contactswithotpsending.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "messages")
data class SentMessage (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="sent_otp")
    val otp:Int,
    @ColumnInfo(name="contact_name")
    val name: String,
    @ColumnInfo(name="otp_send_time")
    val date:OffsetDateTime
)