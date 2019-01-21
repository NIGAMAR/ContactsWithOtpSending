package com.nigamar.contactswithotpsending.ui.info.contactdetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController

import com.nigamar.contactswithotpsending.R
import com.nigamar.contactswithotpsending.ui.base.ScopedFragment
import com.nigamar.contactswithotpsending.ui.info.composemessage.ComposeMessageDirections
import kotlinx.android.synthetic.main.contact_details_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ContactDetails : ScopedFragment(), KodeinAware{
    override val kodein by closestKodein()
    private val viewModelFactory: ContactDetailsViewModelFactory by instance()
    private lateinit var viewModel: ContactDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(ContactDetailsViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        val contactId=ContactDetailsArgs.fromBundle(arguments).contactId
        bindUI(contactId)
    }

    private fun bindUI(contactId: Int) =launch{
        val contact=viewModel.getContactDetail(contactId)
        contact.observe(this@ContactDetails, Observer {contact->
            if(contact==null) return@Observer
            // update the ui over here
            contactName.text= " ${contact.firstName}  ${contact.lastName} "
            contactNumber.text="${contact.number}"
            sendSms.setOnClickListener {view->
                val directions= ContactDetailsDirections.toComposeMessage();
                directions.setContactName(" ${contact.firstName}  ${contact.lastName} ")
                view.findNavController().navigate(directions)
            }
        })

    }

}
