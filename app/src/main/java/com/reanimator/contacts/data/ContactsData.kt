package com.reanimator.contacts.data

import com.reanimator.contacts.model.Contact

object ContactsData {
    private var _contactsData = mutableListOf<Contact>()

    init {
        _contactsData = mutableListOf(
            Contact(
                id = 0,
                name = "Irakli",
                phone = "+995559453823",
                imageResource = "https://picsum.photos/id/1/200"
            ),
            Contact(
                id = 1,
                name = "Ivan",
                phone = "+375338720124",
                imageResource = "https://picsum.photos/id/2/200"
            ),
            Contact(
                id = 2,
                name = "Esenia",
                phone = "+380449201243",
                imageResource = "https://picsum.photos/id/3/200"
            ),
            Contact(
                id = 3,
                name = "Helga",
                phone = "+491729394685",
                imageResource = "https://picsum.photos/id/4/200"
            )
        )
        fillContacts()
    }

    private fun fillContacts() {
        val addedContacts = mutableListOf<Contact>()
        for (i in 4..100) {
            addedContacts.add(
                Contact(
                    id = i,
                    name = "User${i + 1}",
                    phone = "+375292549" + (100..999).random(),
                    imageResource = "https://picsum.photos/id/${i + 1}/200"
                )
            )
        }
        _contactsData.addAll(addedContacts)
    }

    fun getContactsData() = _contactsData.toList()

    fun updateContact(contact: Contact) {
        val contactToEdit = _contactsData.find { it.id == contact.id }
        val contactToEditId = _contactsData.indexOf(contactToEdit)
        _contactsData[contactToEditId] = contact
    }

    fun deleteContact(contact: Contact) {
        _contactsData.remove(contact)
    }

    fun getFilteredData(query: String)  = _contactsData.filter {
        it.name.lowercase().contains(query) || it.phone.lowercase().contains(query)
    }
}