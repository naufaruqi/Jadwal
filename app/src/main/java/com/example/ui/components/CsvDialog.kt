package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CsvDialog(
    exportData: String,
    onDismiss: () -> Unit,
    onImport: (String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var importText by remember { mutableStateOf("") }
    val context = LocalContext.current

    val sampleCsv = """Hari,Mata_Pelajaran,Guru_Dosen,Ruangan,Jam_Mulai,Jam_Selesai,Warna,Catatan
Senin,Matematika Peminatan,Drs. Budi Santoso,Kelas XII IPA 1,07:30,09:00,#3B82F6,Kalkulus
Senin,Fisika,Hendra Wijaya M.Pd,Lab Fisika,09:15,10:45,#8B5CF6,Bawa modul praktikum
Selasa,Bahasa Inggris,Sarah Johnson,Kelas XII IPA 1,08:00,09:30,#06B6D4,Speaking test
Rabu,Pemrograman Web,Faisal M.Kom,Lab Komputer,07:30,11:00,#10B981,Proyek Compose""".trimIndent()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Kelola Data CSV Jadwal",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                TabRow(
                    selectedTabIndex = selectedTab
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Ekspor CSV") }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Impor CSV") }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (selectedTab == 0) {
                    // Ekspor Tab
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Berikut adalah data jadwal Anda dalam format CSV. Anda dapat menyalinnya untuk dicadangkan atau dibagikan.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        OutlinedTextField(
                            value = exportData,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp
                            ),
                            maxLines = 10,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .testTag("export_csv_textfield")
                        )

                        FilledTonalButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Jadwal CSV", exportData)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "CSV berhasil disalin ke clipboard!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("btn_copy_csv")
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null)
                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                            Text("Salin ke Clipboard")
                        }
                    }
                } else {
                    // Impor Tab
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Tempel teks CSV atau muat format contoh jadwal di bawah ini:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = {
                                    importText = sampleCsv
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_load_sample_csv")
                            ) {
                                Text("Gunakan Contoh CSV", fontSize = 12.sp)
                            }
                        }

                        OutlinedTextField(
                            value = importText,
                            onValueChange = { importText = it },
                            placeholder = {
                                Text(
                                    "Hari,Mata_Pelajaran,Guru_Dosen,Ruangan,Jam_Mulai,Jam_Selesai,Warna,Catatan...",
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            },
                            textStyle = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp
                            ),
                            maxLines = 10,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .testTag("import_csv_textfield")
                        )

                        Button(
                            onClick = {
                                if (importText.isNotBlank()) {
                                    onImport(importText)
                                    onDismiss()
                                }
                            },
                            enabled = importText.isNotBlank(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("btn_execute_import_csv")
                        ) {
                            Icon(Icons.Default.FileUpload, contentDescription = null)
                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                            Text("Impor ke Jadwal")
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("btn_close_csv_dialog")
            ) {
                Text("Tutup")
            }
        }
    )
}
