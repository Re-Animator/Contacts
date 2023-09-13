package com.reanimator.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import coil.load
import com.reanimator.contacts.databinding.FragmentContactDetailBinding

private const val EDIT_STATE = true
private const val SAVE_STATE = false
private const val INVALID_INPUT = "Invalid input"

class ContactDetailFragment : Fragment() {
    private val contactsViewModel: ContactViewModel by activityViewModels()

    private var _binding: FragmentContactDetailBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactsViewModel.currentContact.observe(this.viewLifecycleOwner) {
            with(binding) {
                contactsName.setText(it.name)
                contactsPhone.setText(it.phone)
                contactsImage.load(it.imageResourceId)
            }
        }

        with(binding) {
            editButton.setOnClickListener {
                if (editButton.tag.equals(getString(R.string.button_tag_edit))) {
                    editButtonChangeState(EDIT_STATE)
                } else {
                    if (checkForValidInput()) {
                        contactsViewModel.updateContactInfo(
                            name = contactsName.text.toString(),
                            phoneNumber = contactsPhone.text.toString()
                        )
                        editButtonChangeState(SAVE_STATE)
                    }
                }
            }
        }
    }

    private fun checkForValidInput(): Boolean {
        with(binding) {
            if (contactsName.text.isEmpty() || contactsPhone.text.isEmpty()) {
                Toast.makeText(requireActivity(), INVALID_INPUT, Toast.LENGTH_SHORT).show()
                return false
            } else {
                return true
            }
        }
    }

    override fun onStop() {
        super.onStop()
        editButtonChangeState(SAVE_STATE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun editButtonChangeState(state: Boolean) {
        with(binding) {
            contactsName.isEnabled = state
            contactsPhone.isEnabled = state
            if (state == EDIT_STATE) {
                editButton.tag = getString(R.string.button_tag_save)
                editButton.setImageResource(R.drawable.ic_contact_save)
            } else {
                editButton.tag = getString(R.string.button_tag_edit)
                editButton.setImageResource(R.drawable.ic_contact_edit)
            }
        }
    }
}