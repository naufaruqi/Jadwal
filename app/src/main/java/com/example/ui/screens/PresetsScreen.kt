package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ScheduleItem
import com.example.data.model.TaskItem
import com.example.ui.viewmodel.ScheduleViewModel

@Composable
fun PresetsScreen(
    viewModel: ScheduleViewModel,
    schedules: List<ScheduleItem>,
    tasks: List<TaskItem>,
    onOpenCsvDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    var confirmPresetDialog by remember { mutableStateOf<String?>(null) }
    var confirmResetDialog by remember { mutableStateOf(false) }
    var confirmClearDialog by remember { mutableStateOf(false) }

    val totalHours = remember(schedules) {
        val totalMinutes = schedules.sumOf { it.durationMinutes }
        totalMinutes / 60
    }
    val activeDays = remember(schedules) {
        schedules.map { it.dayOfWeek }.distinct().size
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("presets_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Statistik Ringkasan
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Ringkasan Akademik",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatItem(
                            label = "Total Mapel",
                            value = "${schedules.size}",
                            color = MaterialTheme.colorScheme.primary
                        )
                        StatItem(
                            label = "Jam / Minggu",
                            value = "~${totalHours} Jam",
                            color = Color(0xFF10B981)
                        )
                        StatItem(
                            label = "Hari Aktif",
                            value = "$activeDays Hari",
                            color = Color(0xFF8B5CF6)
                        )
                        StatItem(
                            label = "Total Tugas",
                            value = "${tasks.size}",
                            color = Color(0xFFF59E0B)
                        )
                    }
                }
            }
        }

        // CSV Impor / Ekspor
        item {
            Text(
                text = "Cadangan & Sinkronisasi CSV",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.TableChart,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Format CSV Jadwal",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Impor jadwal dari file/teks CSV atau ekspor untuk dicadangkan",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilledTonalButton(
                            onClick = onOpenCsvDialog,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_open_csv_manager")
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Buka Kelola CSV", fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // Preset Templates
        item {
            Text(
                text = "Preset Template Jadwal",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        item {
            PresetCard(
                title = "SMA - Jurusan IPA",
                subtitle = "Matematika Peminatan, Fisika, Kimia, Biologi, Praktikum Lab & Mapel Wajib",
                icon = Icons.Default.School,
                accentColor = Color(0xFF3B82F6),
                onApply = { confirmPresetDialog = "sma_ipa" }
            )
        }

        item {
            PresetCard(
                title = "SMK - Rekayasa Perangkat Lunak (RPL)",
                subtitle = "Pemrograman Web & Mobile, Basis Data SQL, OOP, UI/UX, Cloud & Proyek Industri",
                icon = Icons.Default.Code,
                accentColor = Color(0xFF10B981),
                onApply = { confirmPresetDialog = "smk_rpl" }
            )
        }

        item {
            PresetCard(
                title = "Perguruan Tinggi - Teknik Informatika",
                subtitle = "Algoritma Lanjut, Kalkulus II, Mobile Compose, Arsitektur Komputer & AI",
                icon = Icons.Default.AutoAwesome,
                accentColor = Color(0xFF8B5CF6),
                onApply = { confirmPresetDialog = "kuliah_ti" }
            )
        }

        // Pengaturan Ulang & Hapus
        item {
            Text(
                text = "Pengaturan Data",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { confirmResetDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_reset_default_data")
                    ) {
                        Icon(Icons.Default.RestartAlt, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Reset ke Data Contoh Awal")
                    }

                    OutlinedButton(
                        onClick = { confirmClearDialog = true },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_clear_all_schedules")
                    ) {
                        Icon(Icons.Default.DeleteSweep, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Hapus Semua Jadwal")
                    }
                }
            }
        }
    }

    // Dialog Konfirmasi Preset
    if (confirmPresetDialog != null) {
        val targetPreset = confirmPresetDialog!!
        AlertDialog(
            onDismissRequest = { confirmPresetDialog = null },
            title = { Text("Terapkan Preset Jadwal?") },
            text = {
                Text("Menerapkan preset ini akan menggantikan jadwal Anda saat ini dengan susunan jadwal baru.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.applyPreset(targetPreset)
                        confirmPresetDialog = null
                    },
                    modifier = Modifier.testTag("btn_confirm_apply_preset")
                ) {
                    Text("Terapkan")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { confirmPresetDialog = null }) {
                    Text("Batal")
                }
            }
        )
    }

    // Dialog Konfirmasi Reset
    if (confirmResetDialog) {
        AlertDialog(
            onDismissRequest = { confirmResetDialog = false },
            title = { Text("Kembalikan ke Setelan Awal?") },
            text = {
                Text("Semua perubahan akan diganti dengan data contoh jadwal dan tugas standar.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetToDefault()
                        confirmResetDialog = false
                    },
                    modifier = Modifier.testTag("btn_confirm_reset")
                ) {
                    Text("Reset")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { confirmResetDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    // Dialog Konfirmasi Clear
    if (confirmClearDialog) {
        AlertDialog(
            onDismissRequest = { confirmClearDialog = false },
            title = { Text("Hapus Semua Jadwal?", color = MaterialTheme.colorScheme.error) },
            text = {
                Text("Tindakan ini akan mengosongkan seluruh mata pelajaran pada semua hari.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearAllSchedules()
                        confirmClearDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.testTag("btn_confirm_clear")
                ) {
                    Text("Hapus Semua")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { confirmClearDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
private fun StatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun PresetCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onApply: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = accentColor.copy(alpha = 0.15f),
                modifier = Modifier.size(46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onApply,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                ) {
                    Text("Gunakan Preset", fontSize = 12.sp)
                }
            }
        }
    }
}
