package com.reanimator.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reanimator.contacts.data.ContactsData
import com.reanimator.contacts.model.Contact

class ContactViewModel : ViewModel(){
    private var _currentContact: MutableLiveData<Contact> = MutableLiveData()
    val currentContact: LiveData<Contact>
        get() = _currentContact

    private var _contactsData: ArrayList<Contact> = ArrayList()
    val contactsData: ArrayList<Contact>
        get() = _contactsData

    init {
        _contactsData = ContactsData.getContactsData()
        _currentContact.value = _contactsData[0]
    }

    fun updateCurrentContact(contact: Contact) {
        _currentContact.value = contact
    }
}
