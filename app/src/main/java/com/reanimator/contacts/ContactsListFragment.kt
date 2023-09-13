package com.reanimator.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.reanimator.contacts.databinding.FragmentContactsListBinding

class ContactsListFragment : Fragment() {
    private val contactsViewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentContactsListBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentContactsListBinding.bind(view)
        val slidingPaneLayout = binding.slidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            ContactsListOnBackPressedCallback(slidingPaneLayout, requireActivity())
        )

        val adapter = ContactsAdapter {
            contactsViewModel.updateCurrentContact(it)
            binding.slidingPaneLayout.openPane()
        }
        binding.contactsList.adapter = adapter

        contactsViewModel.contactsData.observe(this.viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }
}

class ContactsListOnBackPressedCallback(slidingPaneLayout: SlidingPaneLayout,
                                        private val activity: FragmentActivity)
    : OnBackPressedCallback(slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen),
        SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

    override fun handleOnBackPressed() {
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {
    }

    override fun onPanelOpened(panel: View) {
        activity.supportFragmentManager.commit {
            add(R.id.detail_container, ContactDetailFragment())
            setReorderingAllowed(true)
            addToBackStack("detail")
        }
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {
        activity.supportFragmentManager.popBackStack()
        isEnabled = false
    }
}