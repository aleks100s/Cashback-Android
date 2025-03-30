package com.alextos.cashback.common.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import com.alextos.cashback.R
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.OffsetTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    date: LocalDate,
    onDateChange: (LocalDate) -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current
    val todayMillis = remember { Clock.systemDefaultZone().millis() }
    val formatter = remember { DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru_RU")) }
    var isPickerShown by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = todayMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= todayMillis
            }
        }
    )

    CustomWideButton(title = formatter.format(date)) {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        isPickerShown = true
    }
    if (isPickerShown) {
        DatePickerDialog(
            onDismissRequest = { isPickerShown = false },
            confirmButton = {
                TextButton(onClick = {
                    isPickerShown = false
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val date = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateChange(date)
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                }) {
                    Text(stringResource(R.string.common_done))
                }
            },
            dismissButton = {
                TextButton(onClick = { isPickerShown = false }) {
                    Text(stringResource(R.string.common_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }

        DatePicker(
            modifier = Modifier.fillMaxWidth(),
            state = DatePickerState(
                locale = CalendarLocale.getDefault(),
                initialSelectedDateMillis = date.atTime(OffsetTime.now(ZoneId.systemDefault())).toEpochSecond(),
                selectableDates = DatePickerDefaults.AllDates
            )
        )
    }
}