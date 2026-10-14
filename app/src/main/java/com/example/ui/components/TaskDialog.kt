package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.TaskItem

@Composable
fun TaskDialog(
    initialTask: TaskItem? = null,
    availableSubjects: List<String> = emptyList(),
    onDismiss: () -> Unit,
    onSave: (TaskItem) -> Unit
) {
    var title by remember { mutableStateOf(initialTask?.title ?: "") }
    var subject by remember { mutableStateOf(initialTask?.subject ?: "") }
    var dueDate by remember { mutableStateOf(initialTask?.dueDate ?: "Besok") }
    var priority by remember { mutableStateOf(initialTask?.priority ?: "Sedang") }
    var notes by remember { mutableStateOf(initialTask?.notes ?: "") }

    var titleError by remember { mutableStateOf(false) }

    val priorities = listOf("Tinggi", "Sedang", "Rendah")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initialTask == null) "Tambah Tugas / PR Baru" else "Edit Tugas",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Judul Tugas
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        if (it.isNotBlank()) titleError = false
                    },
                    label = { Text("Judul Tugas / PR *") },
                    placeholder = { Text("cth: Latihan Matematika Hal. 40") },
                    isError = titleError,
                    supportingText = if (titleError) {
                        { Text("Judul tugas tidak boleh kosong") }
                    } else null,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_task_title")
                )

                // Mata Pelajaran
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Mata Pelajaran") },
                    placeholder = { Text("cth: Matematika") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_task_subject")
                )

                // Tenggat Waktu (Deadline)
                OutlinedTextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Tenggat Waktu / Deadline") },
                    placeholder = { Text("cth: Besok 23:59, atau 18 Okt") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_task_due_date")
                )

                // Prioritas
                Text(
                    text = "Prioritas",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    priorities.forEach { p ->
                        FilterChip(
                            selected = priority == p,
                            onClick = { priority = p },
                            label = { Text(p) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("priority_chip_$p")
                        )
                    }
                }

                // Catatan
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Catatan Tugas (Opsional)") },
                    placeholder = { Text("cth: Dikerjakan per kelompok 3 orang") },
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_task_notes")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isBlank()) {
                        titleError = true
                        return@Button
                    }
                    val item = TaskItem(
                        id = initialTask?.id ?: 0,
                        title = title.trim(),
                        subject = subject.trim(),
                        dueDate = dueDate.trim(),
                        isCompleted = initialTask?.isCompleted ?: false,
                        priority = priority,
                        notes = notes.trim()
                    )
                    onSave(item)
                },
                modifier = Modifier.testTag("btn_save_task")
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("btn_cancel_task")
            ) {
                Text("Batal")
            }
        }
    )
}
