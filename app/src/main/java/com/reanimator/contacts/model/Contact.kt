package com.reanimator.contacts.model

import android.graphics.drawable.Drawable

data class Contact(
    val id: Int,
    val name: String,
    val phone: String,
    val imageResourceId: Int,
)
