package com.nigamar.contactswithotpsending.ui.home.contactlist


import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.nigamar.contactswithotpsending.data.db.entities.Contact
import com.nigamar.contactswithotpsending.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.contact_list_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.io.BufferedReader
import java.io.InputStreamReader
import com.google.gson.reflect.TypeToken
import com.nigamar.contactswithotpsending.R
import com.nigamar.contactswithotpsending.internals.*
import com.nigamar.contactswithotpsending.ui.home.MainActivity
import kotlinx.coroutines.launch


class ContactList : ScopedFragment() , KodeinAware{
    override val kodein by closestKodein()
    val contactsHelper: ContactsHelper by instance()
    val viewModelFactory: ContactsViewModelFactory by instance()
    private lateinit var viewModel: ContactListViewModel
    private lateinit var contactListAdapter: ContactListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager=LinearLayoutManager(activity)
        contactListAdapter= ContactListAdapter()
        contacts_recycler_view.layoutManager=layoutManager
        contacts_recycler_view.adapter=contactListAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(ContactListViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()
        // this method here binds the ui to this fragment
        bindUI()
    }

    private fun bindUI() = launch {
        if (checkFirstInstall()) {
            contactsHelper.loadContacts()
        }
        viewModel.getAllContacts().observe(this@ContactList, Observer {
            if (it==null) return@Observer
            println(it.size)
            contactListAdapter.updateData(it)
        })
    }

    private fun checkFirstInstall(): Boolean {
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val resultCode=sharedPreferences?.getInt(APP_INSTALLED, NOT_FOUND)
        when(resultCode){
            NOT_FOUND ->{
                sharedPreferences.edit().putInt(APP_INSTALLED,FOUND).apply()
                return true
            }
        }
        return false
    }

}
