package com.reanimator.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.reanimator.contacts.databinding.FragmentContactsListBinding

private const val ALERT_CONTACT_DELETED = "Contact deleted"
private const val NOTIFICATION_CONTACT_DELETE_CANCELED = "Deletion canceled"
private const val DELETE_DIALOG_FRAGMENT_TAG = "ContactDeleteDialogFragment"

class ContactsListFragment : Fragment(), ContactDeleteDialogFragment.ConfirmationListener {
    private val contactsViewModel: ContactViewModel by activityViewModels()

    private var _binding: FragmentContactsListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setQuery(contactsViewModel.getSearchText(), true)

        val slidingPaneLayout = binding.slidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            ContactsListOnBackPressedCallback(slidingPaneLayout, requireActivity())
        )

        val adapter = ContactsAdapter(
            onItemClicked = {
                binding.detailContainer.visibility = View.VISIBLE
                replaceFragmentContainerWithNewFragment()
                contactsViewModel.updateCurrentContact(it)
                binding.slidingPaneLayout.openPane()
            },
            onItemLongClicked = {
                contactsViewModel.updateCurrentContact(it)
                showConfirmationDialog()
            }
        )

        binding.contactsList.adapter = adapter
        binding.contactsList.addItemDecoration(
            ContactItemDecorator(
                ContextCompat.getDrawable(requireContext(), R.drawable.divider_contact_item)!!
            )
        )

        contactsViewModel.contactsData.observe(this.viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(searchedText: String?): Boolean {
                if (searchedText != null) {
                    contactsViewModel.filteredData(searchedText)
                }
                return true
            }

        })
    }

    private fun showConfirmationDialog() {
        ContactDeleteDialogFragment()
            .show(childFragmentManager, DELETE_DIALOG_FRAGMENT_TAG)
    }

    override fun confirmButtonClicked() {
        showContextualNotification(ALERT_CONTACT_DELETED)
        contactsViewModel.deleteContact()
        binding.detailContainer.visibility = View.GONE
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun cancelButtonClicked() {
        showContextualNotification(NOTIFICATION_CONTACT_DELETE_CANCELED)
    }

    private fun replaceFragmentContainerWithNewFragment() {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.detail_container, ContactDetailFragment())
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun showContextualNotification(message: String) {
        Toast.makeText(
            requireActivity(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}

class ContactsListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout,
    private val activity: FragmentActivity,
) : OnBackPressedCallback(slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen),
    SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

    override fun handleOnBackPressed() {
        activity.supportFragmentManager.popBackStack()
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {
    }

    override fun onPanelOpened(panel: View) {
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {
        isEnabled = false
    }
}