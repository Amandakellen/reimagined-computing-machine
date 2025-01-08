package com.example.contactlist.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firstname: String,
    val lastName: String,
    val phoneNumber: String
)