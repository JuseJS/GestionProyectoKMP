package presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import presentation.components.ProjectCard
import presentation.components.ProjectData
import presentation.components.SidebarMenu
import presentation.theme.Theme
import java.time.LocalDate

class ProjectsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var selectedItem by remember { mutableStateOf(1) } // 1 para la sección de Proyectos
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
                    isUserAssigned = true,
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
                        // Encabezado con título y filtro
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Proyectos Activos",
                                style = MaterialTheme.typography.h4,
                                color = Theme.materialColors.onBackground
                            )

                            // Filtro
                            FilterChip(
                                onClick = { showOnlyUserProjects = !showOnlyUserProjects },
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.height(40.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.FilterList,
                                        contentDescription = "Filtrar",
                                        tint = if (showOnlyUserProjects)
                                            Theme.materialColors.primary
                                        else
                                            Theme.colors.textSecondary
                                    )
                                    Text(
                                        text = if (showOnlyUserProjects)
                                            "Mis Proyectos"
                                        else
                                            "Todos los Proyectos",
                                        color = if (showOnlyUserProjects)
                                            Theme.materialColors.primary
                                        else
                                            Theme.colors.textSecondary
                                    )
                                }
                            }
                        }

                        // Lista de proyectos
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(displayedProjects) { project ->
                                ProjectCard(
                                    project = project,
                                    onClick = { clickedProject ->
                                        // Aquí iría la navegación al detalle del proyecto
                                        println("Proyecto clickeado: ${clickedProject.name}")
                                    }
                                )
                            }
                        }

                        // Mostrar mensaje si no hay proyectos
                        if (displayedProjects.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No hay proyectos para mostrar",
                                    style = MaterialTheme.typography.h6,
                                    color = Theme.colors.textSecondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterChip(
    onClick: () -> Unit,
    verticalAlignment: Alignment.Vertical,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .height(40.dp)
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Theme.colors.surfaceContainer,
        elevation = 0.dp
    ) {
        TextButton(
            onClick = onClick,
            contentPadding = PaddingValues(horizontal = 16.dp),
            colors = ButtonDefaults.textButtonColors(
                contentColor = Theme.colors.textSecondary
            )
        ) {
            content()
        }
    }
}