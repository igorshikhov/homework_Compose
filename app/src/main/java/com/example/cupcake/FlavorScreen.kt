/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cupcake.model.OrderViewModel

@Composable
fun FlavorScreen(sharedViewModel : OrderViewModel = viewModel(), navController : NavHostController? = null) {
    val flavors = listOf(
        stringResource(R.string.vanilla),
        stringResource(R.string.chocolate),
        stringResource(R.string.red_velvet),
        stringResource(R.string.salted_caramel),
        stringResource(R.string.coffee)
    )
    // If no flavor is set in the view model yet, select vanilla as default flavor
    if (sharedViewModel.hasNoFlavorSet()) {
        sharedViewModel.setFlavor(flavors.first())
    }
    val flavor = sharedViewModel.flavor.value ?: flavors.first()
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(flavor) }
    val margin = dimensionResource(R.dimen.side_margin)
    val height = dimensionResource(R.dimen.button_height)
    val width = dimensionResource(R.dimen.nav_button_width)
    val space = dimensionResource(R.dimen.margin_between_elements)

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        navController?.navigate(Routes.Start.route)
    }
    fun nextScreen() {
        sharedViewModel.setFlavor(selectedOption)
        navController?.navigate(Routes.Pickup.route)
    }

    Column(modifier = Modifier.fillMaxWidth(1f).padding(margin)) {
        Column(modifier = Modifier.selectableGroup()) {
            for (it in flavors) {
                Row(modifier = Modifier
                    .align(Alignment.Start)
                    .selectable(selected = selectedOption == it, onClick = { onOptionSelected(it) }),
                        verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = selectedOption == it, onClick = { onOptionSelected(it) })
                    Text(text = it, fontSize = 20.sp)
                }
            }
        }
        HorizontalDivider()
        Text(text = stringResource(R.string.subtotal_price, sharedViewModel.getPrice()),
            modifier = Modifier
                .offset(0.dp, space)
                .align(Alignment.End),
            fontSize = 24.sp)

        Row(modifier = Modifier.padding(margin), horizontalArrangement = Arrangement.SpaceAround) {
            Button(onClick = { cancelOrder() },
                modifier = Modifier
                    .width(width)
                    .height(height)
                    .padding(margin, space)) {
                Text(stringResource(R.string.cancel), fontSize = 24.sp)
            }
            Button(onClick = { nextScreen() },
                modifier = Modifier
                    .width(width)
                    .height(height)
                    .padding(margin, space)) {
                Text(stringResource(R.string.next), fontSize = 24.sp)
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun PreviewFlavorScreen() {
    FlavorScreen()
}
