package com.example.contactlist.presentation.view

import ContactScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.contactlist.data.local.ContactDatabase
import com.example.contactlist.presentation.view.theme.ContactListTheme
import com.example.contactlist.presentation.viewModel.ContactViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contacts.db"
        ).build()
    }

    private val viewModel by viewModels<ContactViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
                        return ContactViewModel(db.dao) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactListTheme {
                val state by viewModel.state.collectAsState()
                ContactScreen(state = state, onEvent = viewModel::onAction)
            }
        }
    }
}