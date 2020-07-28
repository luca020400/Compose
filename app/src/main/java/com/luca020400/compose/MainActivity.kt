package com.luca020400.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.foundation.ProvideTextStyle
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.layout.RowScope.weight
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.ArrowDropDown
import androidx.ui.material.icons.filled.Clear
import androidx.ui.material.icons.filled.Info
import androidx.ui.material.icons.outlined.Email
import androidx.ui.material.icons.outlined.Person
import androidx.ui.material.icons.outlined.Phone
import androidx.ui.material.icons.rounded.Email
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Content()
        }
    }
}

/* TODO: Fetch them */
val accounts = listOf(
    Account(
        name = "Luca Stefani",
        email = "luca.stefani.ge1@gmail.com"
    ),
    Account(
        name = "luca020400",
        email = "luca020400@lineageos.org"
    )
)

@Composable
fun Content() {
    MainHolder { contact, onContactChange, currentAccount, onAccountChange ->
        Column {
            AccountRow(
                accounts = accounts,
                currentAccount = currentAccount,
                onAccountChange = onAccountChange,
            )
            TextFieldColumn(
                contact = contact,
                onContactChange = onContactChange,
            )
        }
    }
}

@Composable
fun MainHolder(
    content: @Composable (
        Contact,
        (Contact) -> Unit,
        Account,
        (Account) -> Unit
    ) -> Unit
) {
    var contact by state { Contact() }
    var account by state { accounts[0] }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add contact",
                        modifier = Modifier.weight(1f),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                },
                backgroundColor = Color.White
            )
        },
        bottomBar = {
            BottomAppBar(
                cutoutShape = CircleShape
            ) {
                InfoDialogButton()
                Spacer(
                    modifier = Modifier.weight(1f, true)
                )
                IconButton(
                    onClick = {
                        contact = Contact()
                    }
                ) {
                    Icon(Icons.Filled.Clear)
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(text = "Save contact")
                },
                contentColor = Color.White,
                onClick = {
                    /* TODO: Save contact data */
                }
            )
        },
        floatingActionButtonPosition = Scaffold.FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        content(contact, {
            contact = it
        }, account, {
            account = it
        })
    }
}

@Composable
fun AccountRow(
    currentAccount: Account,
    onAccountChange: (Account) -> Unit,
    accounts: List<Account>
) {
    Row(
        verticalGravity = Alignment.CenterVertically
    ) {
        Text(
            text = "Save to",
            style = TextStyle(
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier.padding(16.dp)
        )

        Card(
            shape = CircleShape,
            modifier = Modifier.padding(10.dp)
        ) {
            var expanded by state { false }

            DropdownMenu(
                toggle = {
                    Row(
                        verticalGravity = Alignment.CenterVertically,
                        modifier = Modifier.clickable(
                            onClick = {
                                expanded = true
                            }
                        )
                    ) {
                        Icon(
                            asset = Icons.Rounded.Email,
                            modifier = Modifier.padding(10.dp)
                        )
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            val text = @Composable {
                                Text(
                                    text = currentAccount.name
                                )
                            }
                            val secondaryText = @Composable {
                                Text(
                                    text = currentAccount.email
                                )
                            }
                            ProvideEmphasis(EmphasisAmbient.current.high) {
                                ProvideTextStyle(MaterialTheme.typography.subtitle1, text)
                            }
                            ProvideEmphasis(EmphasisAmbient.current.medium) {
                                ProvideTextStyle(MaterialTheme.typography.body2, secondaryText)
                            }
                        }
                        Spacer(
                            modifier = Modifier.weight(1f, true),
                        )
                        Icon(
                            asset = Icons.Default.ArrowDropDown,
                            modifier = Modifier.padding(10.dp),
                        )
                    }
                },
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
            ) {
                for (account in accounts) {
                    ListItem(
                        text = {
                            Text(account.name)
                        },
                        secondaryText = {
                            Text(account.email)
                        },
                        icon = {
                            Icon(asset = Icons.Rounded.Email)
                        },
                        onClick = {
                            onAccountChange(account)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TextFieldColumn(
    contact: Contact,
    onContactChange: (Contact) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        IconTextFieldHint(
            value = TextFieldValue(contact.firstName),
            onValueChange = {
                onContactChange(
                    contact.copy(
                        firstName = it.text
                    )
                )
            },
            hint = "First name",
            asset = Icons.Outlined.Person,
            imeAction = ImeAction.Next,
        )
        IconTextFieldHint(
            value = TextFieldValue(contact.lastSane),
            onValueChange = {
                onContactChange(
                    contact.copy(
                        lastSane = it.text
                    )
                )
            },
            hint = "Last name",
            imeAction = ImeAction.Next,
        )
        IconTextFieldHint(
            value = TextFieldValue(contact.number),
            onValueChange = {
                onContactChange(
                    contact.copy(
                        number = it.text
                    )
                )
            },
            hint = "Phone",
            asset = Icons.Outlined.Phone,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
        )
        IconTextFieldHint(
            value = TextFieldValue(contact.email),
            onValueChange = {
                onContactChange(
                    contact.copy(
                        email = it.text
                    )
                )
            },
            hint = "Email",
            asset = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
        )
    }
}

@Composable
fun InfoDialogButton() {
    var showDialog by state { false }

    IconButton(
        onClick = {
            showDialog = true
        }
    ) {
        Icon(Icons.Filled.Info)
    }

    if (showDialog) {
        AlertDialog(
            onCloseRequest = {
                showDialog = false
            },
            title = {
                Text(text = "Information")
            },
            text = {
                Text(text = "Add contact")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}

@Composable
fun IconTextFieldHint(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    hint: String,
    asset: VectorAsset? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Unspecified,
) {
    Row {
        val padding = Modifier.padding(
            top = 24.dp,
            end = 24.dp,
            bottom = 24.dp
        )
        if (asset != null) {
            Icon(
                asset = asset,
                modifier = padding /* Center the image */
            )
        } else {
            Spacer(
                modifier = padding.padding(
                    /* Icon is 24x24, add these dimensions back */
                    start = 24.dp,
                    top = 24.dp
                )
            )
        }
        TextFieldHint(value, onValueChange, hint, keyboardType, imeAction)
    }
}

@Composable
fun TextFieldHint(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Unspecified
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(hint)
        },
        keyboardType = keyboardType,
        imeAction = imeAction,
        onImeActionPerformed = { action, softwareKeyboardController ->
            if (action == ImeAction.Done) {
                softwareKeyboardController?.hideSoftwareKeyboard()
            }
            /* TODO: Make the Next ImeAction work */
        }
    )
}

@Preview
@Composable
fun Preview() {
    Content()
}
