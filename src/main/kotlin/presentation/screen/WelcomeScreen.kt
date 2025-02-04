package presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import presentation.components.*
import presentation.theme.Theme
import java.time.LocalDate

class WelcomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var selectedItem by remember { mutableStateOf(0) }

        // Datos de ejemplo
        val activeProjects = remember {
            listOf(
                ProjectData(
                    id = 1,
                    name = "Sistema de Gestión de Inventario",
                    description = "Desarrollo de un sistema completo para la gestión de inventario con seguimiento en tiempo real",
                    startDate = LocalDate.of(2024, 1, 15),
                    clientCompany = "TechnoSolutions S.A.",
                    isUserAssigned = true
                ),
                ProjectData(
                    id = 2,
                    name = "App de Delivery",
                    description = "Aplicación móvil para servicio de entrega a domicilio con tracking en tiempo real",
                    startDate = LocalDate.of(2024, 2, 1),
                    clientCompany = "FastDelivery Inc.",
                    isUserAssigned = true
                )
            )
        }

        val completedProjects = remember {
            listOf(
                ProjectData(
                    id = 3,
                    name = "Portal Web Corporativo",
                    description = "Diseño y desarrollo de portal web corporativo con integración de CMS",
                    startDate = LocalDate.of(2023, 10, 1),
                    clientCompany = "InnovaCorp",
                    endDate = LocalDate.of(2024, 1, 15),
                    isUserAssigned = true
                ),
                ProjectData(
                    id = 4,
                    name = "Sistema de Facturación",
                    description = "Sistema de facturación electrónica con integración a SAT",
                    startDate = LocalDate.of(2023, 8, 15),
                    clientCompany = "ContaPlus",
                    endDate = LocalDate.of(2023, 12, 20),
                    isUserAssigned = false
                )
            )
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Theme.materialColors.background
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                // SideMenu
                SidebarMenu(
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it },
                    onLogout = { navigator?.pop() },
                    navigator = navigator
                )

                // Contenido principal
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(24.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Título y datos del usuario
                        item {
                            WelcomeSection()
                        }

                        // Proyectos Activos
                        item {
                            Text(
                                text = "Proyectos Activos",
                                style = MaterialTheme.typography.h5,
                                color = Theme.materialColors.onBackground,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }

                        items(activeProjects) { project ->
                            ProjectCard(
                                project = project,
                                onClick = { clickedProject ->
                                    navigator?.push(ProjectDetailScreen(clickedProject))
                                }
                            )
                        }

                        // Proyectos Terminados
                        item {
                            Text(
                                text = "Proyectos Terminados",
                                style = MaterialTheme.typography.h5,
                                color = Theme.materialColors.onBackground,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }

                        items(completedProjects) { project ->
                            ProjectCard(
                                project = project,
                                onClick = { clickedProject ->
                                    navigator?.push(ProjectDetailScreen(clickedProject))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WelcomeSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Bienvenido",
            style = MaterialTheme.typography.h4,
            color = Theme.materialColors.onBackground
        )

        ContentCard {
            Row(
                modifier = Modifier.padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Usuario",
                    tint = Theme.materialColors.primary
                )
                Column {
                    Text(
                        text = "¡Bienvenido, Carlos!",
                        style = MaterialTheme.typography.h6,
                        color = Theme.materialColors.onBackground
                    )
                    Text(
                        text = "Rol: Gestor de Proyectos",
                        style = MaterialTheme.typography.subtitle1,
                        color = Theme.colors.textSecondary
                    )
                }
            }
        }
    }
}