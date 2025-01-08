package com.example.contactlist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactlist.domain.model.Contact

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactDatabase: RoomDatabase() {

    abstract  val dao : ContactDAO
}