package ru.anasttruh.cproject.carParts.addPart

import android.icu.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale

fun calculateWearPercent(
    installDate: String,
    initialMileage: Int,
    currentMileage: Int,
    specs: PartSpecs
): Int {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val startDate = sdf.parse(installDate) ?: Date()
    val now = Date()

    val diffMillis = now.time - startDate.time
    val daysUsed = (diffMillis / (1000 * 60 * 60 * 24)).toInt()

    val mileageUsed = currentMileage - initialMileage

    val percentByMileage = (mileageUsed * 100) / specs.mileageRange.last
    val percentByDays = (daysUsed * 100) / (specs.lifespanRange.last * 365)

    val wear = maxOf(percentByMileage, percentByDays).coerceIn(0, 100)
    return wear
}