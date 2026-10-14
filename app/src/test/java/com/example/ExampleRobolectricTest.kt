package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.PresetRepository
import com.example.data.model.ScheduleItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Jadwal", appName)
    }

    @Test
    fun `test presets and csv export-import`() {
        val ipaPresets = PresetRepository.getSmaIpaPreset()
        assertTrue(ipaPresets.isNotEmpty())

        val exportedCsv = PresetRepository.exportToCsv(ipaPresets)
        assertTrue(exportedCsv.contains("Mata_Pelajaran"))
        assertTrue(exportedCsv.contains("Matematika Peminatan"))

        val imported = PresetRepository.parseCsv(exportedCsv)
        assertEquals(ipaPresets.size, imported.size)
        assertEquals("Matematika Peminatan", imported.firstOrNull { it.subject == "Matematika Peminatan" }?.subject)
    }

    @Test
    fun `test schedule duration calculation`() {
        val schedule = ScheduleItem(
            subject = "Pemrograman Web",
            dayOfWeek = 1,
            startTime = "08:00",
            endTime = "10:30"
        )
        assertEquals(150, schedule.durationMinutes)
    }
}
