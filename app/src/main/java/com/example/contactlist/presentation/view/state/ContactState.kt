package com.example.contactlist.presentation.view.state

import com.example.contactlist.data.SortType
import com.example.contactlist.data.SortType.*
import com.example.contactlist.domain.model.Contact
import com.example.contactlist.presentation.view.action.ContactAction

data class ContactState(
    val contacts: List<Contact> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = FIRST_NAME
)