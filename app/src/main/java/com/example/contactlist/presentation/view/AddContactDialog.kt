import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.contactlist.presentation.view.action.ContactAction
import com.example.contactlist.presentation.view.state.ContactState

@Composable
fun AddContactDialog(
    state: ContactState,
    onEvent: (ContactAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isAddingContact) {
        androidx.compose.ui.window.Dialog(
            onDismissRequest = {
                onEvent(ContactAction.HideDialog)
            }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Add Contact", modifier = Modifier.align(Alignment.CenterHorizontally))

                    TextField(
                        value = state.firstName,
                        onValueChange = { onEvent(ContactAction.SetFirstName(it)) },
                        placeholder = { Text("First name") },
                        singleLine = true
                    )
                    TextField(
                        value = state.lastName,
                        onValueChange = { onEvent(ContactAction.SetLastName(it)) },
                        placeholder = { Text("Last name") },
                        singleLine = true
                    )
                    TextField(
                        value = state.phoneNumber,
                        onValueChange = { onEvent(ContactAction.SetPhoneNumber(it)) },
                        placeholder = { Text("Phone number") },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                onEvent(ContactAction.SaveContact)
                                onEvent(ContactAction.HideDialog) // Fecha o diálogo
                            }
                        ) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}
