package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.ScheduleItem
import com.example.data.model.TaskItem
import com.example.ui.components.CsvDialog
import com.example.ui.components.ScheduleDialog
import com.example.ui.components.TaskDialog
import com.example.ui.screens.PresetsScreen
import com.example.ui.screens.ScheduleListScreen
import com.example.ui.screens.TasksScreen
import com.example.ui.screens.TodayScreen
import com.example.ui.viewmodel.ScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: ScheduleViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Hari Ini, 1 = Jadwal, 2 = Tugas, 3 = Preset/CSV
    val snackbarHostState = remember { SnackbarHostState() }

    val allSchedules by viewModel.allSchedules.collectAsStateWithLifecycle()
    val filteredSchedules by viewModel.filteredSchedules.collectAsStateWithLifecycle()
    val allTasks by viewModel.allTasks.collectAsStateWithLifecycle()
    val selectedDayFilter by viewModel.selectedDayFilter.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val snackbarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle()

    var showScheduleDialog by remember { mutableStateOf(false) }
    var editingSchedule by remember { mutableStateOf<ScheduleItem?>(null) }

    var showTaskDialog by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<TaskItem?>(null) }

    var showCsvDialog by remember { mutableStateOf(false) }

    // Handle back button: if on sub-tab, return to tab 0 (Hari Ini)
    BackHandler(enabled = selectedTab != 0) {
        selectedTab = 0
    }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbarMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (selectedTab) {
                            0 -> "Jadwal • Hari Ini"
                            1 -> "Semua Jadwal Pelajaran"
                            2 -> "Tugas & PR"
                            3 -> "Preset & Kelola Data"
                            else -> "Jadwal"
                        },
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.testTag("main_top_app_bar")
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.testTag("main_bottom_nav")
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Today, contentDescription = null) },
                    label = { Text("Hari Ini") },
                    modifier = Modifier.testTag("nav_item_today")
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) },
                    label = { Text("Jadwal") },
                    modifier = Modifier.testTag("nav_item_schedules")
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Assignment, contentDescription = null) },
                    label = { Text("Tugas") },
                    modifier = Modifier.testTag("nav_item_tasks")
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.Category, contentDescription = null) },
                    label = { Text("Preset") },
                    modifier = Modifier.testTag("nav_item_presets")
                )
            }
        },
        floatingActionButton = {
            when (selectedTab) {
                0, 1 -> {
                    FloatingActionButton(
                        onClick = {
                            editingSchedule = null
                            showScheduleDialog = true
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.testTag("fab_add_schedule")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah Jadwal")
                    }
                }
                2 -> {
                    FloatingActionButton(
                        onClick = {
                            editingTask = null
                            showTaskDialog = true
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.testTag("fab_add_task")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah Tugas")
                    }
                }
                else -> {}
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> {
                    TodayScreen(
                        viewModel = viewModel,
                        schedules = allSchedules,
                        tasks = allTasks,
                        onAddSchedule = {
                            editingSchedule = null
                            showScheduleDialog = true
                        },
                        onEditSchedule = { item ->
                            editingSchedule = item
                            showScheduleDialog = true
                        },
                        onDeleteSchedule = { item ->
                            viewModel.deleteSchedule(item)
                        },
                        onAddTask = {
                            editingTask = null
                            showTaskDialog = true
                        },
                        onNavigateToAllSchedules = {
                            selectedTab = 1
                        }
                    )
                }
                1 -> {
                    ScheduleListScreen(
                        viewModel = viewModel,
                        schedules = filteredSchedules,
                        selectedDayFilter = selectedDayFilter,
                        searchQuery = searchQuery,
                        onDayFilterSelected = { day ->
                            viewModel.setSelectedDayFilter(day)
                        },
                        onSearchQueryChanged = { query ->
                            viewModel.setSearchQuery(query)
                        },
                        onAddSchedule = {
                            editingSchedule = null
                            showScheduleDialog = true
                        },
                        onEditSchedule = { item ->
                            editingSchedule = item
                            showScheduleDialog = true
                        },
                        onDeleteSchedule = { item ->
                            viewModel.deleteSchedule(item)
                        }
                    )
                }
                2 -> {
                    TasksScreen(
                        viewModel = viewModel,
                        tasks = allTasks,
                        onAddTask = {
                            editingTask = null
                            showTaskDialog = true
                        },
                        onEditTask = { task ->
                            editingTask = task
                            showTaskDialog = true
                        },
                        onDeleteTask = { task ->
                            viewModel.deleteTask(task)
                        }
                    )
                }
                3 -> {
                    PresetsScreen(
                        viewModel = viewModel,
                        schedules = allSchedules,
                        tasks = allTasks,
                        onOpenCsvDialog = {
                            showCsvDialog = true
                        }
                    )
                }
            }
        }
    }

    // Schedule Dialog
    if (showScheduleDialog) {
        ScheduleDialog(
            initialSchedule = editingSchedule,
            onDismiss = { showScheduleDialog = false },
            onSave = { item ->
                if (editingSchedule == null) {
                    viewModel.addSchedule(item)
                } else {
                    viewModel.updateSchedule(item)
                }
                showScheduleDialog = false
            }
        )
    }

    // Task Dialog
    if (showTaskDialog) {
        val availableSubjects = remember(allSchedules) {
            allSchedules.map { it.subject }.distinct()
        }
        TaskDialog(
            initialTask = editingTask,
            availableSubjects = availableSubjects,
            onDismiss = { showTaskDialog = false },
            onSave = { task ->
                if (editingTask == null) {
                    viewModel.addTask(task)
                } else {
                    viewModel.updateTask(task)
                }
                showTaskDialog = false
            }
        )
    }

    // CSV Dialog
    if (showCsvDialog) {
        CsvDialog(
            exportData = viewModel.exportCsv(),
            onDismiss = { showCsvDialog = false },
            onImport = { csvText ->
                viewModel.importCsv(csvText)
            }
        )
    }
}
