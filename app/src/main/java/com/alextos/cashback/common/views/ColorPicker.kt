package com.alextos.cashback.common.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R
import com.alextos.cashback.common.makeColor
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    title: String,
    color: String,
    onColorChange: (String) -> Unit
) {
    var isPickerShown by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(makeColor(color))
                .clickable {
                    isPickerShown = true
                }
        ) {}
    }

    if (isPickerShown) {
        androidx.compose.ui.window.Dialog(onDismissRequest = {
            isPickerShown = false
        }) {
            val controller = rememberColorPickerController()

            Surface(Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    HsvColorPicker(
                        modifier = Modifier
                            .size(300.dp)
                            .padding(16.dp),
                        controller = controller,
                        onColorChanged = { colorEnvelope: ColorEnvelope ->
                            onColorChange(colorEnvelope.hexCode)
                        },
                        initialColor = makeColor(color)
                    )

                    Button(onClick = {
                        isPickerShown = false
                    }, modifier = Modifier.padding(bottom = 16.dp)) {
                        Text(text = stringResource(R.string.common_done))
                    }
                }
            }
        }
    }
}