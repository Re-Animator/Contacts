package com.reanimator.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.reanimator.contacts.databinding.FragmentContactDetailBinding

class ContactDetailFragment : Fragment() {
    private val sportsViewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentContactDetailBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentContactDetailBinding.bind(view)

        // Attach an observer on the currentSport to update the UI automatically when the data
        // changes.
        sportsViewModel.currentContact.observe(this.viewLifecycleOwner) {
            binding.contactsName.text = getString(it.name)
            binding.contactsPhone.text = getString(it.phone)
            binding.contactsImage.load(it.imageResourceId)
        }
    }
}