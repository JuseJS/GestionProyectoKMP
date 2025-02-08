package presentation.screen

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import data.store.UserStore
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.common.UiState
import presentation.theme.Theme
import presentation.viewmodel.LoginViewModel

class LoginScreen : Screen, KoinComponent {
    private val viewModel: LoginViewModel by inject()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var showError by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf("") }

        val loginState by viewModel.loginState.collectAsState()

        LaunchedEffect(loginState) {
            when (loginState) {
                is UiState.Success -> {
                    if (UserStore.isLoggedIn()) {
                        navigator?.push(WelcomeScreen())
                    }
                }
                is UiState.Error -> {
                    showError = true
                    errorMessage = (loginState as UiState.Error).message
                }
                is UiState.Loading -> {
                    showError = false
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Theme.materialColors.background
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .width(900.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Theme.colors.surfaceContainer),
                    elevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Logo section
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource("NeoTech.png"),
                                contentDescription = "Logo",
                                modifier = Modifier.size(500.dp)
                            )
                        }

                        // Login form section
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            Text(
                                text = "Bienvenido",
                                style = MaterialTheme.typography.h4,
                                color = Theme.materialColors.onBackground
                            )

                            Text(
                                text = "Inicia sesión para continuar",
                                style = MaterialTheme.typography.subtitle1,
                                color = Theme.colors.textSecondary
                            )

                            // Username field
                            OutlinedTextField(
                                value = username,
                                onValueChange = {
                                    username = it
                                    showError = false
                                },
                                label = {
                                    Text(
                                        "Nombre de usuario",
                                        color = Theme.materialColors.onBackground
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                isError = showError,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Theme.materialColors.onBackground,
                                    focusedBorderColor = Theme.materialColors.primary,
                                    unfocusedBorderColor = Theme.colors.outline,
                                    focusedLabelColor = Theme.materialColors.primary,
                                    unfocusedLabelColor = Theme.colors.textSecondary,
                                    cursorColor = Theme.materialColors.primary,
                                    backgroundColor = Theme.colors.surfaceContainer,
                                    errorBorderColor = Theme.materialColors.error,
                                    errorLabelColor = Theme.materialColors.error
                                )
                            )

                            // Password field
                            OutlinedTextField(
                                value = password,
                                onValueChange = {
                                    password = it
                                    showError = false
                                },
                                label = {
                                    Text(
                                        "Contraseña",
                                        color = Theme.materialColors.onBackground
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                visualTransformation = if (passwordVisible)
                                    VisualTransformation.None
                                else
                                    PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password
                                ),
                                isError = showError,
                                trailingIcon = {
                                    IconButton(
                                        onClick = { passwordVisible = !passwordVisible }
                                    ) {
                                        Icon(
                                            imageVector = if (passwordVisible)
                                                Icons.Default.Visibility
                                            else
                                                Icons.Default.VisibilityOff,
                                            contentDescription = if (passwordVisible)
                                                "Ocultar contraseña"
                                            else
                                                "Mostrar contraseña",
                                            tint = Theme.colors.textSecondary
                                        )
                                    }
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Theme.materialColors.onBackground,
                                    focusedBorderColor = Theme.materialColors.primary,
                                    unfocusedBorderColor = Theme.colors.outline,
                                    focusedLabelColor = Theme.materialColors.primary,
                                    unfocusedLabelColor = Theme.colors.textSecondary,
                                    cursorColor = Theme.materialColors.primary,
                                    backgroundColor = Theme.colors.surfaceContainer,
                                    errorBorderColor = Theme.materialColors.error,
                                    errorLabelColor = Theme.materialColors.error
                                )
                            )

                            // Error message
                            AnimatedVisibility(
                                visible = showError,
                                enter = fadeIn() + expandVertically(),
                                exit = fadeOut() + shrinkVertically()
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Error,
                                        contentDescription = "Error",
                                        tint = Theme.materialColors.error,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = errorMessage,
                                        color = Theme.materialColors.error,
                                        style = MaterialTheme.typography.caption
                                    )
                                }
                            }

                            // Login button
                            Button(
                                onClick = {
                                    viewModel.login(username, password)
                                },
                                enabled = username.isNotBlank() &&
                                        password.isNotBlank() &&
                                        loginState !is UiState.Loading,
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Theme.materialColors.primary,
                                    contentColor = Theme.materialColors.onPrimary,
                                    disabledBackgroundColor = Theme.colors.primaryHover
                                        .copy(alpha = 0.38f)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                if (loginState is UiState.Loading) {
                                    CircularProgressIndicator(
                                        color = Theme.materialColors.onPrimary,
                                        modifier = Modifier.size(24.dp),
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        "Iniciar Sesión",
                                        style = MaterialTheme.typography.button
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}