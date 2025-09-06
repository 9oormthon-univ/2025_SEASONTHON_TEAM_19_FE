package com.example.synergy.ui.mentorapply

import android.R.attr.onClick
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import android.app.DatePickerDialog
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview
import com.example.synergy.ui.theme.SYNERGYTheme
import com.example.synergy.ui.theme.White

@Composable
fun MentorApplyScreen(
    mentorId: Int,
    viewModel: MentorApplyViewModel = viewModel(),
    onSubmitted: (() -> Unit)? = null
) {
    val ui by viewModel.ui.collectAsState()
    val ctx = LocalContext.current

    // 성공 알림
    LaunchedEffect(ui.success) {
        ui.success?.let {
            Toast.makeText(ctx, "신청이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            onSubmitted?.invoke()
        }
    }

    val dateText = ui.date?.format(DateTimeFormatter.ISO_DATE) ?: ""
    val timeText = ui.time?.let { DateTimeFormatter.ofPattern("HH:mm").format(it) } ?: ""

    val openDatePicker = {
        val today = LocalDate.now()
        DatePickerDialog(
            ctx,
            { _, y, m, d -> viewModel.onDateChange(LocalDate.of(y, m + 1, d)) },
            ui.date?.year ?: today.year,
            (ui.date?.monthValue ?: today.monthValue) - 1,
            ui.date?.dayOfMonth ?: today.dayOfMonth
        ).show()
    }

    val openTimePicker = {
        val initial = ui.time ?: LocalTime.of(9, 0)
        TimePickerDialog(
            ctx,
            { _, hh, mm -> viewModel.onTimeChange(LocalTime.of(hh, mm)) },
            initial.hour,
            initial.minute,
            true
        ).show()
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(Modifier.height(12.dp))
            Text("멘티 정보", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            // 이름
            TextField(
                value = ui.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("이름") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                )
            )

            Spacer(Modifier.height(12.dp))

            // 휴대폰
            TextField(
                value = ui.phone,
                onValueChange = viewModel::onPhoneChange,
                label = { Text("휴대폰 번호") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                )
            )

            Spacer(Modifier.height(12.dp))

            // 날짜 / 시간
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                UnderlinePickerField(
                    label = "날짜",
                    text = dateText,
                    onClick = openDatePicker,
                    modifier = Modifier.weight(1f)
                )
                UnderlinePickerField(
                    label = "시간",
                    text = timeText,
                    onClick = openTimePicker,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(12.dp))

            // 내용
            TextField(
                value = ui.content,
                onValueChange = viewModel::onContentChange,
                label = { Text("내용") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 140.dp),
                maxLines = 6,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                )
            )

            if (ui.error != null) {
                Spacer(Modifier.height(8.dp))
                Text(ui.error!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(18.dp))

            Button(
                onClick = { viewModel.submit(mentorId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = White,
                )
            ) {
                if (ui.loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 3.dp
                    )
                    Spacer(Modifier.width(12.dp))
                }
                Text("완료")
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun UnderlinePickerField(
    label: String,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.heightIn(min = 56.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier.matchParentSize(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent
            )
        )
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onClick() }
        )
    }
}

@Preview(showBackground = true, name = "Mentor Apply Screen")
@Composable
fun MentorApplyScreenPreview() {
    val vm: MentorApplyViewModel = viewModel()

    SYNERGYTheme {
        MentorApplyScreen(
            mentorId = 1,
            viewModel = vm,
            onSubmitted = null
        )
    }
}