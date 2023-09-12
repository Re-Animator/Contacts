package com.reanimator.contacts.data

import com.reanimator.contacts.R
import com.reanimator.contacts.model.Contact

object ContactsData {
    fun getContactsData(): ArrayList<Contact> {
        return arrayListOf(
            Contact(
                id = 1,
                name = R.string.irakli,
                phone = R.string.irakli_phone,
                imageResourceId = R.drawable.ic_contact_circle
            ),
            Contact(
                id = 2,
                name = R.string.ivan,
                phone = R.string.ivan_phone,
                imageResourceId = R.drawable.ic_contact_circle
            ),
            Contact(
                id = 2,
                name = R.string.esenia,
                phone = R.string.esenia_phone,
                imageResourceId = R.drawable.ic_contact_circle
            ),
            Contact(
                id = 2,
                name = R.string.helga,
                phone = R.string.helga_phone,
                imageResourceId = R.drawable.ic_contact_circle
            )
        )
    }
}