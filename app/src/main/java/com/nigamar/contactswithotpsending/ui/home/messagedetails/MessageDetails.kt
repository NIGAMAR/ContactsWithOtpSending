package com.nigamar.contactswithotpsending.ui.home.messagedetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nigamar.contactswithotpsending.R
import com.nigamar.contactswithotpsending.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.message_details_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MessageDetails : ScopedFragment(),KodeinAware {
    override val kodein by closestKodein()
    private lateinit var viewModel: MessageDetailsViewModel
    private val viewModelFactory: MessageDetailsViewModelFactory by instance()
    private lateinit var messageListAdapter:MessageListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.message_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager=LinearLayoutManager(context)
        messagesRecyclerView.layoutManager=layoutManager
        messageListAdapter= MessageListAdapter()
        messagesRecyclerView.adapter=messageListAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(MessageDetailsViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        bindUI()
    }

    private fun bindUI()= launch {
        val messages=viewModel.getAllMessages()
        messages.observe(this@MessageDetails, Observer { messagesList->
            if (messagesList==null){
                noContent.visibility=View.VISIBLE
                return@Observer
            }
            messageListAdapter.updateData(messagesList)
        })
    }

}
