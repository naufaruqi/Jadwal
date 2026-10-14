package com.example.data

import com.example.data.model.ScheduleItem
import com.example.data.model.TaskItem

object PresetRepository {

    fun getDefaultInitialSchedules(): List<ScheduleItem> {
        return getSmaIpaPreset()
    }

    fun getDefaultInitialTasks(): List<TaskItem> {
        return listOf(
            TaskItem(
                title = "Latihan Soal Matematika Bab 2",
                subject = "Matematika Peminatan",
                dueDate = "Besok, 23:59",
                isCompleted = false,
                priority = "Tinggi",
                notes = "Kerjakan nomor 1 - 15 di buku catatan."
            ),
            TaskItem(
                title = "Laporan Praktikum Kinematika",
                subject = "Fisika",
                dueDate = "Kamis, 12:00",
                isCompleted = false,
                priority = "Tinggi",
                notes = "Format PDF, kirim via email guru."
            ),
            TaskItem(
                title = "Membaca Modul Reaksi Kimia",
                subject = "Kimia",
                dueDate = "Jumat",
                isCompleted = true,
                priority = "Sedang",
                notes = "Halaman 45-60 untuk persiapan kuis."
            ),
            TaskItem(
                title = "Esai Bahasa Indonesia: Teks Argumentasi",
                subject = "Bahasa Indonesia",
                dueDate = "Minggu depan",
                isCompleted = false,
                priority = "Sedang",
                notes = "Minimal 500 kata tema lingkungan."
            )
        )
    }

    fun getSmaIpaPreset(): List<ScheduleItem> {
        return listOf(
            // Senin
            ScheduleItem(subject = "Upacara Bendera", teacher = "Kesiswaan", room = "Lapangan Utama", dayOfWeek = 1, startTime = "07:00", endTime = "07:45", colorHex = "#EF4444", notes = "Gunakan seragam lengkap & dasi"),
            ScheduleItem(subject = "Matematika Peminatan", teacher = "Drs. Budi Santoso", room = "Kelas XII IPA 1", dayOfWeek = 1, startTime = "07:45", endTime = "09:15", colorHex = "#3B82F6", notes = "Kalkulus & Vektor"),
            ScheduleItem(subject = "Fisika", teacher = "Hendra Wijaya, M.Pd", room = "Kelas XII IPA 1", dayOfWeek = 1, startTime = "09:30", endTime = "11:00", colorHex = "#8B5CF6", notes = "Mekanika Klasik"),
            ScheduleItem(subject = "Bahasa Indonesia", teacher = "Dra. Sri Mulyani", room = "Kelas XII IPA 1", dayOfWeek = 1, startTime = "11:00", endTime = "12:30", colorHex = "#10B981", notes = "Analisis Teks Kritik"),

            // Selasa
            ScheduleItem(subject = "Kimia", teacher = "Rina Marlina, S.Si", room = "Lab Kimia", dayOfWeek = 2, startTime = "07:00", endTime = "08:30", colorHex = "#F59E0B", notes = "Bawa jas lab"),
            ScheduleItem(subject = "Biologi", teacher = "Ahmad Zaki, M.Si", room = "Lab Biologi", dayOfWeek = 2, startTime = "08:30", endTime = "10:00", colorHex = "#10B981", notes = "Genetika & DNA"),
            ScheduleItem(subject = "Bahasa Inggris", teacher = "Sarah Johnson, M.A", room = "Kelas XII IPA 1", dayOfWeek = 2, startTime = "10:15", endTime = "11:45", colorHex = "#06B6D4", notes = "Discussion Text"),
            ScheduleItem(subject = "Sejarah Indonesia", teacher = "Bambang P., S.Pd", room = "Kelas XII IPA 1", dayOfWeek = 2, startTime = "11:45", endTime = "13:15", colorHex = "#EC4899", notes = "Era Reformasi"),

            // Rabu
            ScheduleItem(subject = "Matematika Wajib", teacher = "Budi Santoso, M.Pd", room = "Kelas XII IPA 1", dayOfWeek = 3, startTime = "07:00", endTime = "08:30", colorHex = "#3B82F6", notes = "Statistika"),
            ScheduleItem(subject = "Pendidikan Agama", teacher = "Ust. Mansyur, S.Ag", room = "Kelas XII IPA 1", dayOfWeek = 3, startTime = "08:30", endTime = "10:00", colorHex = "#6366F1", notes = "Budi Pekerti"),
            ScheduleItem(subject = "Pendidikan Pancasila & KN", teacher = "Wahyudi, S.Pd", room = "Kelas XII IPA 1", dayOfWeek = 3, startTime = "10:15", endTime = "11:45", colorHex = "#14B8A6", notes = "Hukum & Keadilan"),
            ScheduleItem(subject = "Seni Budaya", teacher = "Nurul Azizah, S.Sn", room = "Ruang Seni", dayOfWeek = 3, startTime = "11:45", endTime = "13:15", colorHex = "#F97316", notes = "Seni Rupa Modern"),

            // Kamis
            ScheduleItem(subject = "Praktikum Fisika", teacher = "Hendra Wijaya, M.Pd", room = "Lab Fisika", dayOfWeek = 4, startTime = "07:00", endTime = "09:15", colorHex = "#8B5CF6", notes = "Eksperimen Bandul Fisis"),
            ScheduleItem(subject = "Praktikum Kimia", teacher = "Rina Marlina, S.Si", room = "Lab Kimia", dayOfWeek = 4, startTime = "09:30", endTime = "11:45", colorHex = "#F59E0B", notes = "Titrasi Asam Basa"),
            ScheduleItem(subject = "PJOK / Olahraga", teacher = "Joko Susilo, S.Pd", room = "GOR Olahraga", dayOfWeek = 4, startTime = "12:15", endTime = "13:45", colorHex = "#10B981", notes = "Bawa baju olahraga & sepatu"),

            // Jumat
            ScheduleItem(subject = "Senam Pagi & Literasi", teacher = "Wali Kelas", room = "Lapangan", dayOfWeek = 5, startTime = "07:00", endTime = "07:45", colorHex = "#06B6D4", notes = "Baju olahraga sekolah"),
            ScheduleItem(subject = "Biologi Sel", teacher = "Ahmad Zaki, M.Si", room = "Kelas XII IPA 1", dayOfWeek = 5, startTime = "07:45", endTime = "09:15", colorHex = "#10B981", notes = "Mikrobiologi"),
            ScheduleItem(subject = "Prakarya & Kewirausahaan", teacher = "Dewi Kartika, S.E", room = "Ruang PKWU", dayOfWeek = 5, startTime = "09:30", endTime = "11:00", colorHex = "#F59E0B", notes = "Proposal Usaha Kreatif")
        )
    }

    fun getSmkRplPreset(): List<ScheduleItem> {
        return listOf(
            // Senin
            ScheduleItem(subject = "Apel Pagi", teacher = "Staff Guru", room = "Lapangan", dayOfWeek = 1, startTime = "07:00", endTime = "07:30", colorHex = "#EF4444", notes = "Disiplin kerja"),
            ScheduleItem(subject = "Pemrograman Web & Mobile", teacher = "Faisal Pratama, M.Kom", room = "Lab Komputer 1", dayOfWeek = 1, startTime = "07:30", endTime = "11:00", colorHex = "#3B82F6", notes = "Kotlin Android & Jetpack Compose"),
            ScheduleItem(subject = "Basis Data (SQL & NoSQL)", teacher = "Indra Gunawan, S.Kom", room = "Lab Komputer 2", dayOfWeek = 1, startTime = "11:30", endTime = "14:00", colorHex = "#8B5CF6", notes = "Relational queries & Room"),

            // Selasa
            ScheduleItem(subject = "Pemrograman Berorientasi Objek", teacher = "Faisal Pratama, M.Kom", room = "Lab Komputer 1", dayOfWeek = 2, startTime = "07:00", endTime = "10:30", colorHex = "#3B82F6", notes = "Design Patterns & OOP"),
            ScheduleItem(subject = "Pemodelan Perangkat Lunak", teacher = "Siti Rahma, S.Kom", room = "R. Teori RPL", dayOfWeek = 2, startTime = "10:45", endTime = "12:30", colorHex = "#14B8A6", notes = "UML Diagram & ERD"),
            ScheduleItem(subject = "Bahasa Inggris Teknis", teacher = "Dian Permata, M.Hum", room = "R. Teori RPL", dayOfWeek = 2, startTime = "13:00", endTime = "14:30", colorHex = "#06B6D4", notes = "Technical Documentation"),

            // Rabu
            ScheduleItem(subject = "Desain Antarmuka (UI/UX)", teacher = "Gilang Ramadhan, S.Sn", room = "Lab Multimedia", dayOfWeek = 3, startTime = "07:00", endTime = "10:00", colorHex = "#EC4899", notes = "Figma & Design Tokens"),
            ScheduleItem(subject = "Cloud & DevOps Dasar", teacher = "Indra Gunawan, S.Kom", room = "Lab Komputer 2", dayOfWeek = 3, startTime = "10:15", endTime = "12:30", colorHex = "#F59E0B", notes = "Git & CI/CD Pipelines"),
            ScheduleItem(subject = "Matematika Terapan", teacher = "Dr. Lukman Hakim", room = "R. Teori RPL", dayOfWeek = 3, startTime = "13:00", endTime = "14:30", colorHex = "#6366F1", notes = "Logika Matematika"),

            // Kamis
            ScheduleItem(subject = "Proyek Kolaborasi Industri", teacher = "Tim Mentor Industri", room = "Studio RPL", dayOfWeek = 4, startTime = "07:00", endTime = "12:00", colorHex = "#10B981", notes = "Scrum Sprint Review"),
            ScheduleItem(subject = "Keamanan Informasi & Jaringan", teacher = "Rizky Fauzi, S.Kom", room = "Lab Cyber", dayOfWeek = 4, startTime = "12:30", endTime = "14:30", colorHex = "#F97316", notes = "Security Best Practices"),

            // Jumat
            ScheduleItem(subject = "Karakter Bangsa & Agama", teacher = "Drs. H. Muhsin", room = "Aula", dayOfWeek = 5, startTime = "07:00", endTime = "08:30", colorHex = "#10B981", notes = "Soft skills"),
            ScheduleItem(subject = "Produk Kreatif & Kewirausahaan", teacher = "Mega Utami, S.E", room = "Studio RPL", dayOfWeek = 5, startTime = "08:45", endTime = "11:15", colorHex = "#F59E0B", notes = "Presentasi Pitch Deck Startup")
        )
    }

    fun getKuliahTiPreset(): List<ScheduleItem> {
        return listOf(
            // Senin
            ScheduleItem(subject = "Algoritma & Pemrograman Lanjut", teacher = "Dr. Eng. Wahyu, M.T", room = "Lab C 201", dayOfWeek = 1, startTime = "08:00", endTime = "10:30", colorHex = "#3B82F6", notes = "3 SKS - Tree, Graph, DP"),
            ScheduleItem(subject = "Kalkulus Lanjut", teacher = "Prof. Hartono, M.Sc", room = "Gedung Kuliah Bersama 304", dayOfWeek = 1, startTime = "13:00", endTime = "15:30", colorHex = "#8B5CF6", notes = "3 SKS - Persamaan Diferensial"),

            // Selasa
            ScheduleItem(subject = "Arsitektur Komputer", teacher = "Ir. Rudi Hermawan, Ph.D", room = "Ruang Teori 205", dayOfWeek = 2, startTime = "09:00", endTime = "11:30", colorHex = "#F59E0B", notes = "3 SKS - Pipeline, RISC-V"),
            ScheduleItem(subject = "Pengembangan Aplikasi Mobile", teacher = "Ade Irawan, M.Kom", room = "Lab Multimedia", dayOfWeek = 2, startTime = "13:30", endTime = "16:00", colorHex = "#10B981", notes = "3 SKS - Jetpack Compose"),

            // Rabu
            ScheduleItem(subject = "Sistem Manajemen Basis Data", teacher = "Dr. Ani Wijayanti", room = "Lab Jaringan", dayOfWeek = 3, startTime = "08:00", endTime = "10:30", colorHex = "#06B6D4", notes = "3 SKS - Indexing & Query Opt"),
            ScheduleItem(subject = "Kecerdasan Buatan & ML", teacher = "Prof. Dian Kusuma, Ph.D", room = "Ruang Multimedia 402", dayOfWeek = 3, startTime = "11:00", endTime = "13:30", colorHex = "#EC4899", notes = "3 SKS - Deep Learning basics"),

            // Kamis
            ScheduleItem(subject = "Rekayasa Kebutuhan Perangkat Lunak", teacher = "Eko Prasetyo, M.T", room = "Ruang 305", dayOfWeek = 4, startTime = "10:00", endTime = "12:30", colorHex = "#14B8A6", notes = "3 SKS - Agile Requirements"),

            // Jumat
            ScheduleItem(subject = "Etika Profesi & Hukum Siber", teacher = "Bambang S., S.H., M.H", room = "Rektorat Lt. 2", dayOfWeek = 5, startTime = "08:30", endTime = "10:30", colorHex = "#6366F1", notes = "2 SKS - UU ITE & HAKI")
        )
    }

    fun exportToCsv(schedules: List<ScheduleItem>): String {
        val sb = StringBuilder()
        sb.append("Hari,Mata_Pelajaran,Guru_Dosen,Ruangan,Jam_Mulai,Jam_Selesai,Warna,Catatan\n")
        schedules.sortedWith(compareBy({ it.dayOfWeek }, { it.startTime })).forEach { item ->
            val escapedSubject = escapeCsv(item.subject)
            val escapedTeacher = escapeCsv(item.teacher)
            val escapedRoom = escapeCsv(item.room)
            val escapedNotes = escapeCsv(item.notes)
            sb.append("${item.dayName},$escapedSubject,$escapedTeacher,$escapedRoom,${item.startTime},${item.endTime},${item.colorHex},$escapedNotes\n")
        }
        return sb.toString()
    }

    fun parseCsv(csvText: String): List<ScheduleItem> {
        val result = mutableListOf<ScheduleItem>()
        val lines = csvText.lines()
        for (line in lines) {
            val trimmed = line.trim()
            if (trimmed.isEmpty()) continue
            // Skip header if line starts with Hari or Day
            if (trimmed.startsWith("Hari", ignoreCase = true) || trimmed.startsWith("Day", ignoreCase = true)) {
                continue
            }

            val tokens = parseCsvLine(trimmed)
            if (tokens.size >= 4) {
                val dayStr = tokens.getOrNull(0)?.trim() ?: "Senin"
                val subject = tokens.getOrNull(1)?.trim() ?: ""
                if (subject.isEmpty()) continue
                val teacher = tokens.getOrNull(2)?.trim() ?: ""
                val room = tokens.getOrNull(3)?.trim() ?: ""
                val startTime = tokens.getOrNull(4)?.trim()?.takeIf { it.contains(":") } ?: "08:00"
                val endTime = tokens.getOrNull(5)?.trim()?.takeIf { it.contains(":") } ?: "09:30"
                val color = tokens.getOrNull(6)?.trim()?.takeIf { it.startsWith("#") } ?: "#3B82F6"
                val notes = tokens.getOrNull(7)?.trim() ?: ""

                val dayOfWeek = parseDayStringToNumber(dayStr)
                result.add(
                    ScheduleItem(
                        subject = subject,
                        teacher = teacher,
                        room = room,
                        dayOfWeek = dayOfWeek,
                        startTime = startTime,
                        endTime = endTime,
                        colorHex = color,
                        notes = notes
                    )
                )
            }
        }
        return result
    }

    private fun parseDayStringToNumber(dayStr: String): Int {
        val lower = dayStr.lowercase()
        return when {
            lower.contains("senin") || lower.contains("mon") -> 1
            lower.contains("selasa") || lower.contains("tue") -> 2
            lower.contains("rabu") || lower.contains("wed") -> 3
            lower.contains("kamis") || lower.contains("thu") -> 4
            lower.contains("jumat") || lower.contains("jum'at") || lower.contains("fri") -> 5
            lower.contains("sabtu") || lower.contains("sat") -> 6
            lower.contains("minggu") || lower.contains("sun") || lower.contains("ahad") -> 7
            else -> try {
                val num = dayStr.toInt()
                if (num in 1..7) num else 1
            } catch (e: Exception) {
                1
            }
        }
    }

    private fun escapeCsv(value: String): String {
        return if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            "\"" + value.replace("\"", "\"\"") + "\""
        } else {
            value
        }
    }

    private fun parseCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        val current = StringBuilder()
        var inQuotes = false
        var i = 0
        while (i < line.length) {
            val c = line[i]
            when {
                c == '\"' -> {
                    if (inQuotes && i + 1 < line.length && line[i + 1] == '\"') {
                        current.append('\"')
                        i++
                    } else {
                        inQuotes = !inQuotes
                    }
                }
                c == ',' && !inQuotes -> {
                    result.add(current.toString())
                    current.clear()
                }
                else -> {
                    current.append(c)
                }
            }
            i++
        }
        result.add(current.toString())
        return result
    }
}
