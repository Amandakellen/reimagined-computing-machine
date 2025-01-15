package com.example.contactlist.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactlist.data.SortType
import com.example.contactlist.data.local.ContactDAO
import com.example.contactlist.domain.model.Contact
import com.example.contactlist.presentation.view.action.ContactAction
import com.example.contactlist.presentation.view.state.ContactState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ContactViewModel(
    private val dao: ContactDAO
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _contacts = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.FIRST_NAME -> dao.getContactsOrderByFirstName()
                SortType.LAST_NAME -> dao.getContactsOrderByLastName()
                SortType.PHONE_NUMBER -> dao.getContactsOrderByPhoneNumber()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ContactState())
    val state = combine(_state, _sortType, _contacts) { state, sortType, contacts ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())


    fun onAction(action: ContactAction) {
        when (action) {
            is ContactAction.DeleteContacts -> {
                viewModelScope.launch {
                    dao.deleteContact(action.contact)
                }
            }

            is ContactAction.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = false
                    )
                }

            }

            is ContactAction.SaveContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber

                if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
                    return
                }

                val contact = Contact(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber
                )
                viewModelScope.launch {
                    dao.upsertContact(contact)
                }

            }

            is ContactAction.SetFirstName -> {
                _state.update {
                    it.copy(
                        firstName = it.firstName
                    )
                }
            }

            is ContactAction.SetLastName -> {
                _state.update {
                    it.copy(
                        lastName = action.lastName
                    )
                }
            }

            is ContactAction.SetPhoneNumber -> {
                _state.update {
                    it.copy(
                        phoneNumber = action.phoneNumber
                    )
                }
            }

            ContactAction.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = true
                    )
                }

            }

            is ContactAction.SortContacts -> {
                _sortType.value = action.sortType
            }
        }
    }

}