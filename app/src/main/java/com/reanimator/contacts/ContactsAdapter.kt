package com.reanimator.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reanimator.contacts.databinding.ContactsListItemBinding
import com.reanimator.contacts.model.Contact

class ContactsAdapter(private val onItemClicked: (Contact) -> Unit) :
    ListAdapter<Contact, ContactsAdapter.ContactsViewHolder>(DiffCallback) {

    class ContactsViewHolder(private var binding: ContactsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.apply {
                contactsName.text = contact.name
                contactsPhone.text = contact.phone
                contactsImage.setImageResource(contact.imageResourceId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            ContactsListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                oldItem.name == newItem.name && oldItem.phone == newItem.phone
        }
    }
}