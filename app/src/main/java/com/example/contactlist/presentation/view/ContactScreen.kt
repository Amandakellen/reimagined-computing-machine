import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton // Certifique-se de usar o RadioButton correto
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactlist.data.SortType
import com.example.contactlist.presentation.view.action.ContactAction
import com.example.contactlist.presentation.view.state.ContactState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContactScreen(
    state: ContactState,
    onEvent: (ContactAction) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ContactAction.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add contact"
                )
            }
        },
    ) { _ ->
        if (state.isAddingContact) {
            AddContactDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(ContactAction.SortContacts(sortType))
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(ContactAction.SortContacts(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.contacts.size) { index ->
                val contact = state.contacts[index]
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${contact.firstName} ${contact.lastName}",
                            fontSize = 20.sp
                        )
                        Text(text = contact.phoneNumber, fontSize = 12.sp)
                    }
                    IconButton(onClick = {
                        onEvent(ContactAction.DeleteContacts(contact))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete contact"
                        )
                    }
                }
            }
        }
    }
}
