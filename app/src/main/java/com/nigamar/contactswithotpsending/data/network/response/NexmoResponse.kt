package com.nigamar.contactswithotpsending.data.network.response

import com.google.gson.annotations.SerializedName


class NexmoResponse(
    @SerializedName("message-count")
    val messageCount:Int
)