package com.luca020400.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.layout.ColumnScope.weight
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Clear
import androidx.ui.material.icons.filled.Info
import androidx.ui.material.icons.outlined.Email
import androidx.ui.material.icons.outlined.Person
import androidx.ui.material.icons.outlined.Phone
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.text.SoftwareKeyboardController
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

@Composable
fun Content() {
    MainHolder {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            IconTextFieldHint(
                hint = "First name",
                asset = Icons.Outlined.Person,
                imeAction = ImeAction.Next
            )
            IconTextFieldHint(
                hint = "Last name",
                imeAction = ImeAction.Next
            )
            IconTextFieldHint(
                hint = "Phone",
                asset = Icons.Outlined.Phone,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
            IconTextFieldHint(
                hint = "Email",
                asset = Icons.Outlined.Email,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            )
        }
    }
}

@Composable
fun MainHolder(
    content: @Composable() () -> Unit
) {
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
                Spacer(Modifier.weight(1f, true))
                IconButton(
                    onClick = {
                        /* TODO: Clear all text fields */
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
                    /* TODO: Gather all text fields data */
                }
            )
        },
        floatingActionButtonPosition = Scaffold.FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        content()
    }
}

@Composable
fun InfoDialogButton() {
    val showDialog = state { false }

    IconButton(
        onClick = {
            showDialog.value = true
        }
    ) {
        Icon(Icons.Filled.Info)
    }

    if (showDialog.value) {
        AlertDialog(
            onCloseRequest = {
                showDialog.value = false
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
                        showDialog.value = false
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
    hint: String,
    asset: VectorAsset? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Unspecified
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
        TextFieldHint(hint, keyboardType, imeAction)
    }
}

@Composable
fun TextFieldHint(
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Unspecified
) {
    val state = savedInstanceState(saver = TextFieldValue.Saver) { TextFieldValue() }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.value,
        onValueChange = {
            state.value = it
        },
        label = {
            Text(hint)
        },
        keyboardType = keyboardType,
        imeAction = imeAction,
        onImeActionPerformed = { action: ImeAction,
                                 softwareKeyboardController: SoftwareKeyboardController? ->
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
