package com.nigamar.contactswithotpsending.ui.home.messagedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nigamar.contactswithotpsending.R
import com.nigamar.contactswithotpsending.data.db.entities.SentMessage
import kotlinx.android.synthetic.main.message_list_item_view.view.*
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.temporal.ChronoUnit


class MessageListAdapter : RecyclerView.Adapter<MessageListAdapter.MessageListViewHolder>() {
    private var messageList= mutableListOf<SentMessage>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageListViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.message_list_item_view,parent,false)
        return MessageListViewHolder(view)
    }

    override fun getItemCount(): Int {
       return messageList.size
    }

    override fun onBindViewHolder(holder: MessageListViewHolder, position: Int) {
        holder.bindUI(messageList[position])
    }

    fun updateData(listOfMessages:List<SentMessage>){
        messageList = listOfMessages as MutableList<SentMessage>
        notifyDataSetChanged()
    }


    inner class MessageListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindUI(sentMessage: SentMessage) {
            itemView.otp_txt_view.text= "${sentMessage.otp}"
            itemView.name_txt_view.text=sentMessage.name
            val nowMinutes=OffsetDateTime.now().minute
            val savedMinutes=sentMessage.date.minute
            val difference=nowMinutes-savedMinutes
            itemView.timeAgo.text=" Sent ${difference} mins ago "
        }
    }
}