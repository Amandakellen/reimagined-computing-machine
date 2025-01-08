package com.example.contactlist.presentation.view.action

import com.example.contactlist.data.SortType
import com.example.contactlist.domain.model.Contact

sealed interface ContactAction {
    object SaveContact : ContactAction
    data class SetFirstName(val firstName: String) : ContactAction
    data class SetLastName(val lastName: String) : ContactAction
    data class SetPhoneNumber(val phoneNumber: String) : ContactAction
    object ShowDialog : ContactAction
    object HideDialog : ContactAction
    data class SortContacts(val sortType: SortType) : ContactAction
    data class DeleteContacts(val contact: Contact) : ContactAction
}