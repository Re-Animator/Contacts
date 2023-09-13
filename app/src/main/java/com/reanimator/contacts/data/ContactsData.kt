package com.reanimator.contacts.data

import com.reanimator.contacts.R
import com.reanimator.contacts.model.Contact

object ContactsData {
    private val _contactsData = mutableListOf(
        Contact(
            id = 1,
            name = "Irakli",
            phone = "+995559453823",
            imageResourceId = R.drawable.ic_contact_circle
        ),
        Contact(
            id = 2,
            name = "Ivan",
            phone = "+375338720124",
            imageResourceId = R.drawable.ic_contact_circle
        ),
        Contact(
            id = 3,
            name = "Esenia",
            phone = "+380449201243",
            imageResourceId = R.drawable.ic_contact_circle
        ),
        Contact(
            id = 4,
            name = "Helga",
            phone = "+491729394685",
            imageResourceId = R.drawable.ic_contact_circle
        )
    )

    fun getContactsData() = _contactsData.toList()

    fun updateContact(contact: Contact) {
        _contactsData[contact.id - 1] = contact
    }
}