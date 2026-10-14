package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.PresetRepository
import com.example.data.model.ScheduleItem
import com.example.data.model.TaskItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val scheduleDao = db.scheduleDao()
    private val taskDao = db.taskDao()

    private val _selectedDayFilter = MutableStateFlow(0) // 0 = Semua, 1 = Senin ... 7 = Minggu
    val selectedDayFilter: StateFlow<Int> = _selectedDayFilter.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    val allSchedules: StateFlow<List<ScheduleItem>> = scheduleDao.getAllSchedules()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allTasks: StateFlow<List<TaskItem>> = taskDao.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredSchedules: StateFlow<List<ScheduleItem>> = combine(
        allSchedules,
        _selectedDayFilter,
        _searchQuery
    ) { schedules, dayFilter, query ->
        schedules.filter { item ->
            val matchDay = (dayFilter == 0 || item.dayOfWeek == dayFilter)
            val matchQuery = if (query.isBlank()) true else {
                item.subject.contains(query, ignoreCase = true) ||
                item.teacher.contains(query, ignoreCase = true) ||
                item.room.contains(query, ignoreCase = true) ||
                item.notes.contains(query, ignoreCase = true)
            }
            matchDay && matchQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Pre-populate on initial start if empty
        viewModelScope.launch {
            if (scheduleDao.getCount() == 0) {
                scheduleDao.insertAll(PresetRepository.getDefaultInitialSchedules())
            }
            if (taskDao.getCount() == 0) {
                taskDao.insertAll(PresetRepository.getDefaultInitialTasks())
            }
        }
    }

    fun setSelectedDayFilter(day: Int) {
        _selectedDayFilter.value = day
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }

    fun addSchedule(schedule: ScheduleItem) {
        viewModelScope.launch {
            scheduleDao.insertSchedule(schedule)
            _snackbarMessage.value = "Jadwal ${schedule.subject} berhasil ditambahkan"
        }
    }

    fun updateSchedule(schedule: ScheduleItem) {
        viewModelScope.launch {
            scheduleDao.updateSchedule(schedule)
            _snackbarMessage.value = "Jadwal ${schedule.subject} berhasil diperbarui"
        }
    }

    fun deleteSchedule(schedule: ScheduleItem) {
        viewModelScope.launch {
            scheduleDao.deleteSchedule(schedule)
            _snackbarMessage.value = "Jadwal ${schedule.subject} dihapus"
        }
    }

    fun addTask(task: TaskItem) {
        viewModelScope.launch {
            taskDao.insertTask(task)
            _snackbarMessage.value = "Tugas '${task.title}' berhasil ditambahkan"
        }
    }

    fun updateTask(task: TaskItem) {
        viewModelScope.launch {
            taskDao.updateTask(task)
            _snackbarMessage.value = "Tugas diperbarui"
        }
    }

    fun toggleTaskCompleted(task: TaskItem) {
        viewModelScope.launch {
            taskDao.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }

    fun deleteTask(task: TaskItem) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
            _snackbarMessage.value = "Tugas '${task.title}' dihapus"
        }
    }

    fun applyPreset(presetType: String) {
        viewModelScope.launch {
            val presetList = when (presetType) {
                "sma_ipa" -> PresetRepository.getSmaIpaPreset()
                "smk_rpl" -> PresetRepository.getSmkRplPreset()
                "kuliah_ti" -> PresetRepository.getKuliahTiPreset()
                else -> PresetRepository.getSmaIpaPreset()
            }
            scheduleDao.deleteAll()
            scheduleDao.insertAll(presetList)
            _snackbarMessage.value = "Preset berhasil diterapkan (${presetList.size} mata pelajaran)"
        }
    }

    fun importCsv(csvContent: String) {
        viewModelScope.launch {
            try {
                val parsed = PresetRepository.parseCsv(csvContent)
                if (parsed.isNotEmpty()) {
                    scheduleDao.insertAll(parsed)
                    _snackbarMessage.value = "Berhasil mengimpor ${parsed.size} jadwal dari CSV"
                } else {
                    _snackbarMessage.value = "Format CSV tidak valid atau kosong"
                }
            } catch (e: Exception) {
                _snackbarMessage.value = "Gagal mengimpor CSV: ${e.localizedMessage}"
            }
        }
    }

    fun exportCsv(): String {
        return PresetRepository.exportToCsv(allSchedules.value)
    }

    fun resetToDefault() {
        viewModelScope.launch {
            scheduleDao.deleteAll()
            taskDao.deleteAll()
            scheduleDao.insertAll(PresetRepository.getDefaultInitialSchedules())
            taskDao.insertAll(PresetRepository.getDefaultInitialTasks())
            _snackbarMessage.value = "Data berhasil direset ke setelan awal"
        }
    }

    fun clearAllSchedules() {
        viewModelScope.launch {
            scheduleDao.deleteAll()
            _snackbarMessage.value = "Semua jadwal telah dihapus"
        }
    }

    /**
     * Helper to get current day of week in Indonesian standard (1 = Senin, ... 7 = Minggu)
     */
    fun getCurrentDayOfWeek(): Int {
        val cal = Calendar.getInstance()
        return when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            Calendar.SUNDAY -> 7
            else -> 1
        }
    }

    fun getCurrentDayName(): String {
        return when (getCurrentDayOfWeek()) {
            1 -> "Senin"
            2 -> "Selasa"
            3 -> "Rabu"
            4 -> "Kamis"
            5 -> "Jumat"
            6 -> "Sabtu"
            7 -> "Minggu"
            else -> "Senin"
        }
    }

    fun getCurrentDateFormatted(): String {
        val sdf = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID"))
        return sdf.format(Calendar.getInstance().time)
    }

    /**
     * Current ongoing class and upcoming class for today
     */
    fun getScheduleStatus(item: ScheduleItem): ScheduleStatus {
        val cal = Calendar.getInstance()
        val currentMinutes = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)

        val startMinutes = parseTimeToMinutes(item.startTime)
        val endMinutes = parseTimeToMinutes(item.endTime)

        return when {
            currentMinutes in startMinutes..endMinutes -> ScheduleStatus.ONGOING
            currentMinutes < startMinutes -> ScheduleStatus.UPCOMING
            else -> ScheduleStatus.FINISHED
        }
    }

    fun parseTimeToMinutes(timeStr: String): Int {
        return try {
            val parts = timeStr.split(":").map { it.toInt() }
            parts[0] * 60 + parts[1]
        } catch (e: Exception) {
            0
        }
    }
}

enum class ScheduleStatus {
    ONGOING,
    UPCOMING,
    FINISHED
}
