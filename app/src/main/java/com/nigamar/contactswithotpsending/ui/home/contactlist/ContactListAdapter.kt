package com.nigamar.contactswithotpsending.ui.home.contactlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nigamar.contactswithotpsending.R
import com.nigamar.contactswithotpsending.data.db.entities.Contact
import com.nigamar.contactswithotpsending.ui.info.contactdetails.ContactDetailsArgs
import com.nigamar.contactswithotpsending.ui.info.contactdetails.ContactDetailsDirections
import kotlinx.android.synthetic.main.contact_list_item_view.view.*

class ContactListAdapter:RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder>(){
    var contactList= mutableListOf<Contact>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item_view,parent,false)
        return ContactListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        val contact=contactList[position]
        holder.bindUi(contact)
    }

    fun updateData(listOfContacts:List<Contact>){
        contactList= listOfContacts as MutableList<Contact>
        notifyDataSetChanged()
    }


    inner class ContactListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
         fun bindUi(contact:Contact) {
             itemView.name.text= "${contact.firstName}  ${contact.lastName}"
             itemView.setOnClickListener{
                 val directions =ContactListDirections.toContactDetails()
                 directions.setContactId(contact.id)
                 findNavController(itemView).navigate(directions)
             }
        }
    }
}