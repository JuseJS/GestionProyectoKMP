package presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import presentation.theme.Theme

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

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
                        // Logo
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

                        // Formulario de Login
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

                            OutlinedTextField(
                                value = username,
                                onValueChange = { username = it },
                                label = {
                                    Text(
                                        "Nombre de usuario",
                                        color = Theme.materialColors.onBackground
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Theme.materialColors.onBackground,
                                    focusedBorderColor = Theme.materialColors.primary,
                                    unfocusedBorderColor = Theme.colors.outline,
                                    focusedLabelColor = Theme.materialColors.primary,
                                    unfocusedLabelColor = Theme.colors.textSecondary,
                                    cursorColor = Theme.materialColors.primary,
                                    backgroundColor = Theme.colors.surfaceContainer
                                )
                            )

                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = {
                                    Text(
                                        "Contraseña",
                                        color = Theme.materialColors.onBackground
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Theme.materialColors.onBackground,
                                    focusedBorderColor = Theme.materialColors.primary,
                                    unfocusedBorderColor = Theme.colors.outline,
                                    focusedLabelColor = Theme.materialColors.primary,
                                    unfocusedLabelColor = Theme.colors.textSecondary,
                                    cursorColor = Theme.materialColors.primary,
                                    backgroundColor = Theme.colors.surfaceContainer
                                )
                            )

                            Button(
                                onClick = {
                                    navigator?.push(WelcomeScreen())
                                },
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