package presentation.viewmodel

import data.network.rest.model.requests.AssignTaskRequest
import domain.common.Result
import domain.model.Programmer
import domain.model.Task
import domain.repository.ProjectRepository
import domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _availableProgrammers = MutableStateFlow<List<Programmer>>(emptyList())
    val availableProgrammers: StateFlow<List<Programmer>> = _availableProgrammers.asStateFlow()

    private val _currentTask = MutableStateFlow<Task?>(null)
    val currentTask: StateFlow<Task?> = _currentTask.asStateFlow()

    private val _currentProgrammer = MutableStateFlow<Programmer?>(null)
    val currentProgrammer: StateFlow<Programmer?> = _currentProgrammer.asStateFlow()

    fun initTask(task: Task) {
        _currentTask.value = task
        loadAvailableProgrammers()
        if (task.programmerId != null) {
            loadProgrammerDetails(task.programmerId)
        }
    }

    private fun loadProgrammerDetails(programmerId: Int) {
        scope.launch {
            when (val result = projectRepository.getProjectProgrammers(_currentTask.value?.projectId ?: return@launch)) {
                is Result.Success -> {
                    _currentProgrammer.value = result.data.find { it.id == programmerId }
                }
                is Result.Error -> {
                    println("[ERROR] Failed to load programmer details: ${result.exception.message}")
                }
            }
        }
    }

    private fun loadAvailableProgrammers() {
        scope.launch {
            when (val result = projectRepository.getAllAvailableProgrammers()) {
                is Result.Success -> {
                    _availableProgrammers.value = result.data
                }
                is Result.Error -> {
                    _availableProgrammers.value = emptyList()
                    println("[ERROR] Failed to load available programmers: ${result.exception.message}")
                }
            }
        }
    }

    fun assignProgrammer(programmerId: Int) {
        scope.launch {
            _currentTask.value?.let { task ->
                if (task.programmerId != null) {
                    return@launch
                }

                val request = AssignTaskRequest(
                    programmer = programmerId,
                    task = task.id
                )

                when (val result = taskRepository.assignTask(request)) {
                    is Result.Success -> {
                        _currentTask.value = task.copy(programmerId = programmerId)
                        loadProgrammerDetails(programmerId)
                    }
                    is Result.Error -> {
                        println("[ERROR] Failed to assign programmer: ${result.exception.message}")
                    }
                }
            }
        }
    }
}