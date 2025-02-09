package presentation.states

import domain.model.Task

data class TasksState(
    val unassignedTasks: List<Task> = emptyList(),
    val assignedTasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList()
)