package com.example.contactlist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.contactlist.domain.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDAO {

    @Upsert
    suspend fun upsertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact ORDER BY firstname ASC")
    suspend fun getContactsOrderByFirstName(): Flow<List<Contact>>

    @Query("SELECT * FROM contact ORDER BY lastName ASC")
    suspend fun getContactsOrderByLastName(): Flow<List<Contact>>

    @Query("SELECT * FROM contact ORDER BY phoneNumber ASC")
    suspend fun getContactsOrderByPhoneNumber(): Flow<List<Contact>>


}