package com.reanimator.contacts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.reanimator.contacts.databinding.ContactsListItemBinding

import com.reanimator.contacts.model.Contact

class ContactsAdapter(private val onItemClicked: (Contact) -> Unit) :
    ListAdapter<Contact, ContactsAdapter.ContactsViewHolder>(DiffCallback) {

    private lateinit var context: Context

    class ContactsViewHolder(private var binding: ContactsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact, context: Context) {
            binding.contactsName.text = context.getString(contact.name)
            binding.contactsPhone.text = context.getString(contact.phone)
            binding.contactsImage.load(contact.imageResourceId)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): ContactsViewHolder {
        context = parent.context
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
        holder.bind(current, context)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return (oldItem.id == newItem.id ||
                        oldItem.name == newItem.name ||
                        oldItem.phone == newItem.phone ||
                        oldItem.imageResourceId == newItem.imageResourceId
                        )
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }
        }
    }
}