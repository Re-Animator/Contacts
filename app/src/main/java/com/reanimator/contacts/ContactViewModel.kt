package com.reanimator.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reanimator.contacts.data.ContactsData
import com.reanimator.contacts.model.Contact

class ContactViewModel : ViewModel() {
    private val _searchBarText = MutableLiveData("")
    val searchBarText: LiveData<String>
        get() = _searchBarText

    private var _currentContact: MutableLiveData<Contact> = MutableLiveData()
    val currentContact: LiveData<Contact>
        get() = _currentContact

    private val _contactsData: MutableLiveData<List<Contact>> =
        MutableLiveData(ContactsData.getContactsData())
    val contactsData: LiveData<List<Contact>>
        get() = _contactsData

    init {
        _currentContact.value = _contactsData.value?.get(0)
    }

    fun updateCurrentContact(contact: Contact) {
        _currentContact.value = contact
    }

    private fun getData() = ContactsData.getContactsData()

    fun updateContactInfo(name: String, phoneNumber: String) {
        val editedContact = _currentContact.value?.copy(name = name, phone = phoneNumber)
        if (editedContact != null) {
            ContactsData.updateContact(editedContact)
            _contactsData.value = getData()
        }
    }

    fun deleteContact() {
        ContactsData.deleteContact(_currentContact.value!!)
        _contactsData.value = getData()
    }

    fun filteredData(query: String) {
        _searchBarText.value = query
        _contactsData.value = ContactsData.getFilteredData(query.lowercase())
    }

    fun getSearchText(): String = searchBarText.value.toString()
}
