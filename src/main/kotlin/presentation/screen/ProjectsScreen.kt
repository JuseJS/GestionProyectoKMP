package presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import presentation.components.*
import presentation.components.project.EmptyProjectsMessage
import presentation.components.project.ProjectsHeader
import presentation.theme.Theme
import java.time.LocalDate

class ProjectsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var selectedItem by remember { mutableStateOf(1) }
        var showOnlyUserProjects by remember { mutableStateOf(false) }

        // Datos de ejemplo
        val allProjects = remember {
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
                    isUserAssigned = false
                ),
                ProjectData(
                    id = 3,
                    name = "CRM Empresarial",
                    description = "Sistema de gestión de relaciones con clientes personalizado",
                    startDate = LocalDate.of(2024, 3, 1),
                    clientCompany = "Business Solutions Corp",
                    isUserAssigned = true
                )
            )
        }

        val displayedProjects = if (showOnlyUserProjects) {
            allProjects.filter { it.isUserAssigned }
        } else {
            allProjects
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
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Header con título y filtro
                        ProjectsHeader(
                            showOnlyUserProjects = showOnlyUserProjects,
                            onFilterChange = { showOnlyUserProjects = it }
                        )

                        // Lista de proyectos
                        if (displayedProjects.isNotEmpty()) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(displayedProjects) { project ->
                                    ProjectCard(
                                        project = project,
                                        onClick = { clickedProject ->
                                            //navigator?.push(ProjectDetailScreen(clickedProject))
                                        }
                                    )
                                }
                            }
                        } else {
                            EmptyProjectsMessage()
                        }
                    }
                }
            }
        }
    }
}