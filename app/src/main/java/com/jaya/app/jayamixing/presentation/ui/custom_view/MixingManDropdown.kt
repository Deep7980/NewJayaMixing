package com.jaya.app.jayamixing.presentation.ui.custom_view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaya.app.core.domain.model.MixingMantype
import com.jaya.app.core.domain.model.ProductType
import com.jaya.app.jayamixing.extensions.bottomToUp
import com.jaya.app.jayamixing.extensions.screenHeight
import com.jaya.app.jayamixing.extensions.screenWidth
import com.jaya.app.jayamixing.extensions.upToBottom
import com.jaya.app.jayamixing.presentation.viewmodels.AddProductViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MixingManDropdown(
    viewModel: AddProductViewModel,
    loading: Boolean,
    dataList: List<MixingMantype>,
    onSelect: (String) -> Unit
) {

    var mExpanded by remember { mutableStateOf(false) }
    //var mSelectedText by remember { mutableStateOf("") }

    //  mSelectedText=baseViewModel.prefilledExpenseType
    if (viewModel.selectedMixingMan.value.isEmpty()) {
       viewModel.selectedMixingMan.value = "Mixing Man"
    }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    AnimatedContent(
        targetState = loading,
        transitionSpec = {
            if (targetState && !initialState) {
                upToBottom()
            } else {
                bottomToUp()
            }
        }
    ) {
        if (!it) {

            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
                Surface(
                    border = BorderStroke(1.dp, Color.Gray),
                    shape = RoundedCornerShape(4.dp),
                    //backgroundColor = Color.Yellow
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    ) {

                        Row(modifier = Modifier
                            .clickable { mExpanded = !mExpanded }
                            .fillMaxHeight()
                            .weight(1f)
                            .padding(start = 15.dp)) {
                            Text(
                                text = viewModel.selectedMixingMan.value,
                                //label = label,
                                color = Color.DarkGray,
                                fontSize = 17.sp,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                        Icon(icon, "contentDescription",
                            Modifier
                                .padding(end = 4.dp)
                                .align(Alignment.CenterVertically)
                                .clickable { mExpanded = !mExpanded })

                    }
                }

//------------------------------------------------------------------------------------//


                DropdownMenu(
                    modifier= Modifier.wrapContentHeight().fillMaxWidth(0.90f).background(Color.LightGray),
                    expanded = mExpanded,
                    onDismissRequest = { mExpanded = false },
                ) {
                    dataList.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(text = item.name)
                                    Divider(
                                        color = Color.Black,
                                        thickness = 0.5.dp,
                                        modifier = Modifier.padding(top = 10.dp)
                                    )
                                }
                            },
                            onClick = {
                                viewModel.selectedMixingMan.value = item.name
                                mExpanded = false
                                onSelect(viewModel.selectedMixingMan.value)
                            })

                    }
                }//DropdownMenu





            }

        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(width = screenWidth * 0.15f, height = screenHeight * 0.15f)
                    .padding(bottom = screenHeight * 0.05f),
                color = Color.Black,
                strokeWidth = 5.dp,
            )
        }
    }
}