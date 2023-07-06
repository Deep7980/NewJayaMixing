package com.jaya.app.jayamixing.presentation.ui.screen

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.jaya.app.jayamixing.BuildConfig
import com.jaya.app.jayamixing.R
import com.jaya.app.jayamixing.extensions.BackPressHandler
import com.jaya.app.jayamixing.extensions.screenHeight
import com.jaya.app.jayamixing.extensions.screenWidth
import com.jaya.app.jayamixing.presentation.ui.custom_view.CuttingManDropDown
import com.jaya.app.jayamixing.presentation.ui.custom_view.FloorManagerDropdown
import com.jaya.app.jayamixing.presentation.ui.custom_view.MixingManDropdown
import com.jaya.app.jayamixing.presentation.ui.custom_view.OvenManDropdown
import com.jaya.app.jayamixing.presentation.ui.custom_view.PackingSupervisorDropdown
import com.jaya.app.jayamixing.presentation.ui.custom_view.PlantDropdown
import com.jaya.app.jayamixing.presentation.ui.custom_view.ProductsDropdown
import com.jaya.app.jayamixing.presentation.viewmodels.AddProductViewModel
import com.jaya.app.jayamixing.presentation.viewmodels.BaseViewModel
import com.jaya.app.jayamixing.ui.theme.AppBarYellow
import com.jaya.app.jayamixing.ui.theme.LogoutRed
import com.jaya.app.jayamixing.ui.theme.SplashGreen
import java.util.Locale
import java.util.Objects


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AddProductFormPage(viewModel:AddProductViewModel,baseViewModel: BaseViewModel) {

    val context = LocalContext.current
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val mCheckedState = remember{ mutableStateOf(false)}
    val currentShiftState = remember { mutableStateOf(viewModel.isShiftAselected.value) }

    BackPressHandler(onBackPressed = {viewModel.onBackDialog()})
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            OutlinedButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    viewModel.isShiftAselected.value = true
                    viewModel.shiftABtnBackColor.value = SplashGreen
                    viewModel.shiftATxtColor.value = Color.White

                    viewModel.isShiftBselected.value = false
                    viewModel.shiftBBtnBackColor.value = Color.White
                    viewModel.shiftBTxtColor.value = Color.DarkGray

                    viewModel.isShiftCselected.value = false
                    viewModel.shiftCBtnBackColor.value = Color.White
                    viewModel.shiftCTxtColor.value = Color.DarkGray

                    viewModel.selectedFloor.value = "Floor Manager"

                    currentShiftState.value = true


                },
                colors = ButtonDefaults.buttonColors(viewModel.shiftABtnBackColor.value),
                border = BorderStroke(0.5.dp, Color.LightGray),
                elevation = ButtonDefaults.buttonElevation(20.dp)

            ) {
                Text(
                    text = "Shift A",
                    color = viewModel.shiftATxtColor.value,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.width(5.dp))

            OutlinedButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    viewModel.isShiftBselected.value = true
                    viewModel.shiftBBtnBackColor.value = SplashGreen
                    viewModel.shiftBTxtColor.value = Color.White

                    viewModel.isShiftAselected.value = false
                    viewModel.shiftABtnBackColor.value = Color.White
                    viewModel.shiftATxtColor.value = Color.DarkGray

                    viewModel.isShiftCselected.value = false
                    viewModel.shiftCBtnBackColor.value = Color.White
                    viewModel.shiftCTxtColor.value = Color.DarkGray

                    viewModel.selectedFloor.value = ""
                    Toast.makeText(context,"Shift B selected",Toast.LENGTH_SHORT).show()

                },
                colors = ButtonDefaults.buttonColors(viewModel.shiftBBtnBackColor.value),
                border = BorderStroke(0.5.dp, Color.LightGray),
                elevation = ButtonDefaults.buttonElevation(20.dp)

            ) {
                Text(
                    text = "Shift B",
                    color = viewModel.shiftBTxtColor.value,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.width(5.dp))

            OutlinedButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    viewModel.isShiftCselected.value = true
                    viewModel.shiftCBtnBackColor.value = SplashGreen
                    viewModel.shiftCTxtColor.value = Color.White

                    viewModel.isShiftBselected.value = false
                    viewModel.shiftBBtnBackColor.value = Color.White
                    viewModel.shiftBTxtColor.value = Color.DarkGray

                    viewModel.isShiftAselected.value = false
                    viewModel.shiftABtnBackColor.value = Color.White
                    viewModel.shiftATxtColor.value = Color.DarkGray

                    Toast.makeText(context,"Shift C selected",Toast.LENGTH_SHORT).show()

                },
                colors = ButtonDefaults.buttonColors(viewModel.shiftCBtnBackColor.value),
                border = BorderStroke(0.5.dp, Color.LightGray),
                elevation = ButtonDefaults.buttonElevation(20.dp)

            ) {
                Text(
                    text = "Shift C",
                    color = viewModel.shiftCTxtColor.value,
                    fontSize = 15.sp
                )
            }


        }

        Card(
            border = BorderStroke(1.dp, Color.LightGray),
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(60.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .background(AppBarYellow)
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    Text(
                        text = "Plant",
                        color = Color.DarkGray,
                        fontSize = 17.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(20.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier

                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    PlantDropdown(
                        false,
                        listOf("Plant1", "Plant2", "Plant3", "Plant4"),
                        viewModel,
                        onSelect = {
                            baseViewModel.getStartedSelectedPlant.value=it
                            //Toast.makeText(context, baseViewModel.getStartedSelectedPlant.value, Toast.LENGTH_SHORT).show()
                            //viewModel.selectedPincode.value = it
                        })
                }
            }//row
        }//card

        Column(
            modifier=Modifier.verticalScroll(rememberScrollState())
        ) {
            FloorManagerDropdown(
                viewModel,
                false,
                viewModel.floorTypes.collectAsState().value,
                onSelect = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    //viewModel.selectedPincode.value = it
                })

            ProductsDropdown(
                viewModel,
                false ,
                viewModel.productTypes.collectAsState().value ,
                onSelect = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            )

            OutlinedTextField(
                value = viewModel.initialName.value,
                onValueChange = {
                    viewModel.initialName.value = it
                },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(65.dp)
                    .padding(top = 5.dp, start = 20.dp, bottom = 5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.DarkGray,
                    unfocusedBorderColor = Color.Black)
            )
            MixingManDropdown(
                viewModel,
                false ,
                viewModel.MixingManTypes.collectAsState().value ,
                onSelect = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            )
            CuttingManDropDown(
                viewModel,
                false ,
                viewModel.CuttingManTypes.collectAsState().value ,
                onSelect = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            )
            OvenManDropdown(
                viewModel,
                false ,
                viewModel.OvenManTypes.collectAsState().value ,
                onSelect = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            )
            PackingSupervisorDropdown(
                viewModel,
                false ,
                viewModel.PackingSupervisorTypes.collectAsState().value ,
                onSelect = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            )

//        Row(
//            modifier = Modifier.padding(top = 5.dp, start = 20.dp)
//        ) {
//            OutlinedTextField(
//                value = textState.value,
//                onValueChange = {
//                    textState.value = it
//                },
//                leadingIcon = {
//                    Icon(Icons.Default.Search, contentDescription = null)
//                },
//                placeholder = { Text(text = "Mixing Labour")}
//            )
//            OutlinedButton(
//                modifier = Modifier
//                    .width(67.dp)
//                    .height(57.dp)
//                    .padding(start = 5.dp),
//                shape = RoundedCornerShape(5.dp),
//                onClick = { },
//                colors = ButtonDefaults.buttonColors(SplashGreen),
//                border = BorderStroke(0.5.dp, Color.LightGray),
//                elevation = ButtonDefaults.buttonElevation(20.dp)
//
//            ) {
//                Icon(
//                    Icons.Filled.Add,
//                    contentDescription = "Add",
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//        }

            Row(
                modifier = Modifier
                    .padding(top = 15.dp, start = 20.dp, end = 15.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
//                    value = viewModel.mixingLabourTextName.value,
//                    onValueChange = {textValue->
////                        viewModel.getMixingLabourTypes(textValue)
//                        viewModel.mixingLabourTextName.value=textValue
//                    },
                    value = viewModel.mixingLabourSearchQuery,
                    onValueChange = viewModel::getMixingLabourTypes,
                    //label = { Text("your mobile number") },
                    placeholder = { Text("Mixing Labour", color = Color.Gray) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(end = 5.dp),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "",
                            modifier = Modifier
                                .height(32.dp)
                                .width(32.dp),
                            tint = Color.LightGray
                        )
                    },
                )

                Button(
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        viewModel.addMixingLabourToList()
                    },
                    colors = ButtonDefaults.buttonColors(SplashGreen),
                    modifier = Modifier
                        .height(55.dp)
                        .width(53.dp)
                        .align(Alignment.CenterVertically),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Icon(
                        Icons.Default.Add,
                        tint = Color.White,
                        contentDescription = "",
                        // modifier = Modifier.padding(end = 8.dp)
                    )
                    //Icon(Icons.Filled.Add, "add")
                }

            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                val items= viewModel.mixingLabours.collectAsState().value
                Column(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top)
                {

                    //// selected labours will show here....
//                    viewModel.selectedMixingLabourList.forEach {name->
//                        Text(text = name)
//                    }

                    for ((index, mixingLaboursName) in viewModel.selectedMixingLabourList.withIndex() ) {

                        Row(modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                            .fillMaxWidth()

                        ) {
                            Text(
                                text = "${index+1}. $mixingLaboursName",
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically),
                                //   .wrapContentSize(),
                                fontSize = 17.sp,
                                color = Color.DarkGray,
                                // fontWeight = FontWeight.Bold
                            )
                            IconButton(onClick = {
                                //  viewModel.showHidepasswordText.value = false
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.delete_labour_svg),
                                    contentDescription = null,
                                    tint = LogoutRed,
                                    modifier = Modifier
                                        .width(screenWidth * 0.15f)
                                        .align(Alignment.CenterVertically)
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() },
                                            onClick = {
                                                viewModel.selectedMixingLabourList.removeAt(index)
                                            },
                                            role = Role.Image
                                        )
                                )
                            }

                        }
                    }


                }
                AnimatedContent(targetState = items.isNotEmpty()) {state->
                   when(state) {
                       true -> Column(
                           modifier = Modifier.fillMaxWidth(.90f),
                           horizontalAlignment = Alignment.CenterHorizontally,
                           verticalArrangement = Arrangement.Top)
                       {
                           if(viewModel.mixingLabourSearchQuery.text.isNotEmpty()){
                               items.forEach { item ->
                                   TextButton(
                                       onClick = {
                                           viewModel.onSelectMixingLabour(item)
                                       },
                                       modifier = Modifier
                                           .fillMaxWidth()
                                           .background(Color.LightGray))
                                   {
                                       Column {
                                           Text(text = item.name, color = Color.Black)
                                           Divider(
                                               color = Color.Black,
                                               thickness = 0.5.dp,
                                               modifier = Modifier.padding(top = 10.dp)
                                           )
                                       }

                                   }
                               }
                           }

                       }
                       false -> {}
                   }
                }
            }
            //row

           /* for ((index, mixingLaboursName) in viewModel.mixingLabourList.withIndex() ) {

                Row(modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                    .fillMaxWidth()

                ) {
                    Text(
                        text = "${index+1}. $mixingLaboursName",
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        //   .wrapContentSize(),
                        fontSize = 17.sp,
                        color = Color.DarkGray,
                        // fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = {
                        //  viewModel.showHidepasswordText.value = false
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete_labour_svg),
                            contentDescription = null,
                            tint = LogoutRed,
                            modifier = Modifier
                                .width(screenWidth * 0.15f)
                                .align(Alignment.CenterVertically)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        viewModel.mixingLabourList.removeAt(index)
                                    },
                                    role = Role.Image
                                )
                        )
                    }

                }
            }*/





            //For Cutting Labours display--------------->
            Row(
                modifier = Modifier
                    .padding(top = 15.dp, start = 20.dp, end = 15.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = viewModel.cuttingLabourSearchQuery,
                    onValueChange = viewModel::getCuttingLabourTypes,
                    //label = { Text("your mobile number") },
                    placeholder = { Text("Cutting Labour", color = Color.Gray) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(end = 5.dp),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "",
                            modifier = Modifier
                                .height(32.dp)
                                .width(32.dp),
                            tint = Color.LightGray
                        )
                    },
                )

                Button(
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        viewModel.addCuttingLabourToList()
                    },
                    colors = ButtonDefaults.buttonColors(SplashGreen),
                    modifier = Modifier
                        .height(55.dp)
                        .width(53.dp)
                        .align(Alignment.CenterVertically),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Icon(
                        Icons.Default.Add,
                        tint = Color.White,
                        contentDescription = "",
                        // modifier = Modifier.padding(end = 8.dp)
                    )
                    //Icon(Icons.Filled.Add, "add")
                }

            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                val items= viewModel.cuttingLabours.collectAsState().value
                Column(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top)
                {

                    //// selected labours will show here....
//                    viewModel.selectedMixingLabourList.forEach {name->
//                        Text(text = name)
//                    }

                    for ((index, cuttingLabourName) in viewModel.selectedCuttingLabourList.withIndex() ) {

                        Row(modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                            .fillMaxWidth()

                        ) {
                            Text(
                                text = "${index+1}. $cuttingLabourName",
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically),
                                //   .wrapContentSize(),
                                fontSize = 17.sp,
                                color = Color.DarkGray,
                                // fontWeight = FontWeight.Bold
                            )
                            IconButton(onClick = {
                                //  viewModel.showHidepasswordText.value = false
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.delete_labour_svg),
                                    contentDescription = null,
                                    tint = LogoutRed,
                                    modifier = Modifier
                                        .width(screenWidth * 0.15f)
                                        .align(Alignment.CenterVertically)
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() },
                                            onClick = {
                                                viewModel.selectedCuttingLabourList.removeAt(index)
                                            },
                                            role = Role.Image
                                        )
                                )
                            }

                        }
                    }


                }
                AnimatedContent(targetState = items.isNotEmpty()) {state->
                    when(state) {
                        true -> Column(
                            modifier = Modifier.fillMaxWidth(.90f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top)
                        {
                            if(viewModel.cuttingLabourSearchQuery.text.isNotEmpty()){
                                items.forEach { item ->
                                    TextButton(
                                        onClick = {
                                            viewModel.onSelectCuttingLabour(item)
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.LightGray))
                                    {
                                        Column {
                                            Text(text = item.name, color = Color.Black)
                                            Divider(
                                                color = Color.Black,
                                                thickness = 0.5.dp,
                                                modifier = Modifier.padding(top = 10.dp)
                                            )
                                        }

                                    }
                                }
                            }

                        }
                        false -> {}
                    }
                }
            }

//            for ((index, cuttingLaboursName) in viewModel.cuttingLabourList.withIndex() ) {
//
//                Row(modifier = Modifier
//                    .padding(start = 20.dp, end = 20.dp, top = 10.dp)
//                    .fillMaxWidth()
//
//                ) {
//                    Text(
//                        text = "${index+1}. $cuttingLaboursName",
//                        modifier = Modifier
//                            .weight(1f)
//                            .align(Alignment.CenterVertically),
//                        //   .wrapContentSize(),
//                        fontSize = 17.sp,
//                        color = Color.DarkGray,
//                        // fontWeight = FontWeight.Bold
//                    )
//                    IconButton(onClick = {
//                        //  viewModel.showHidepasswordText.value = false
//                    }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.delete_labour_svg),
//                            contentDescription = null,
//                            tint = LogoutRed,
//                            modifier = Modifier
//                                .width(screenWidth * 0.15f)
//                                .align(Alignment.CenterVertically)
//                                .clickable(
//                                    indication = null,
//                                    interactionSource = remember { MutableInteractionSource() },
//                                    onClick = {
//                                        viewModel.cuttingLabourList.removeAt(index)
//                                    },
//                                    role = Role.Image
//                                )
//                        )
//                    }
//
//                }
//            }


            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 10.dp)
                    .fillMaxWidth(1f)

            ) {

                Text(
                    text = "Broken and Left Dough Used",
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Switch(
                    checked = mCheckedState.value,
                    onCheckedChange = {
                        mCheckedState.value = it
                    },
                    modifier = Modifier
                        .padding(start = 50.dp, end = 10.dp, bottom = 20.dp)

                        .scale(scale = 1.5f),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = SplashGreen,
                        uncheckedThumbColor =  Color.White,
                        uncheckedTrackColor =  Color.Gray
                    )
                    )
            }

            if(mCheckedState.value){
                Card(
                    border = BorderStroke(1.dp, Color.LightGray),
                    elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.90f)
                        .height(80.dp)
                        .padding(bottom = 20.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .background(AppBarYellow)
                                .weight(1f)
                                .fillMaxHeight(),
                        ) {
                            Text(
                                text = "Left Dough Added",
                                color = Color.DarkGray,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(
                                        bottom = 10.dp,
                                        end = 10.dp,
                                        top = 10.dp,
                                        start = 15.dp
                                    ),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                        ) {

                            TextField(
                                value = viewModel.leftAddedKG.value,
                                onValueChange ={
                                    if(it.length <=5)
                                        viewModel.leftAddedKG.value = it
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.White,
                                    textColor = Color.Black,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .width(100.dp)
                                    .padding(start = 10.dp),
                                singleLine = true
                            )
                            Text(
                                text = "Kgs",
                                color = Color.DarkGray,
                                fontSize = 17.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 15.dp),
                                textAlign = TextAlign.Center,
                                //fontWeight = FontWeight.Bold
                            )
                        }
                    }//row
                }//card


                Card(
                    border = BorderStroke(1.dp, Color.LightGray),
                    elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.90f)
                        .height(80.dp)
                        .padding(bottom = 20.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .background(AppBarYellow)
                                .weight(1f)
                                .fillMaxHeight(),
                        ) {
                            Text(
                                text = "Broken Added",
                                color = Color.DarkGray,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(
                                        bottom = 10.dp,
                                        end = 10.dp,
                                        top = 10.dp,
                                        start = 15.dp
                                    ),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                        ) {

                            TextField(
                                value = viewModel.brokenAddedKG.value,
                                onValueChange ={
                                    if(it.length <=5)
                                        viewModel.brokenAddedKG.value = it
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.White,
                                    textColor = Color.Black,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .width(100.dp)
                                    .padding(start = 10.dp),
                                singleLine = true
                            )
                            Text(
                                text = "Kgs",
                                color = Color.DarkGray,
                                fontSize = 17.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 15.dp),
                                textAlign = TextAlign.Center,
                                //fontWeight = FontWeight.Bold
                            )
                        }
                    }//row
                }
                OutlinedTextField(
                    value = viewModel.productDesc.value,
                    onValueChange = {
                        viewModel.productDesc.value = it
                    },
                    placeholder = { Text(text = "Products", textAlign = TextAlign.Center)},
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .height(85.dp)
                        .padding(top = 5.dp, start = 20.dp, bottom = 20.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.DarkGray,
                        unfocusedBorderColor = Color.Black)
                )//card
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, bottom = 40.dp, top = 5.dp)
                        .fillMaxWidth(1f)

                ){
                    //RequestContentPermission(viewModel)
                    PickImageFromCamera()
                    //cameraButtonClick(viewModel)

                }
            }
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .height(80.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(5.dp),
                onClick = {viewModel.confirmSubmit()},
                colors = ButtonDefaults.buttonColors(SplashGreen),
                border = BorderStroke(0.5.dp, Color.LightGray),
                elevation = ButtonDefaults.buttonElevation(20.dp)

            ) {
                Text(
                    text = "Confirm Now",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }


        }

    }
}

//@Composable
//fun cameraButtonClick(viewModel: AddProductViewModel){
//
//
//    Button(onClick = {viewModel.onBackDialog()},
//        colors = ButtonDefaults.outlinedButtonColors(containerColor= Color.White),
//        modifier = Modifier
//            .padding(end = 10.dp)
//            .width(70.dp)
//            .height(50.dp)
//            ,
//        border = BorderStroke(width = 1.dp,color = Color.DarkGray),
//        shape = RoundedCornerShape(5.dp)
//    ) {
//        Icon(
//            painter = painterResource(id = R.drawable.camera),
//            contentDescription = "camera",
//            modifier = Modifier.fillMaxSize(1f)
//        )
//    }
//}

@Composable
fun PickImageFromCamera() {
    var bitmap  by remember{ mutableStateOf<Bitmap?>(null)}
    val uploadImageTextState = remember { mutableStateOf(true) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()){
        bitmap = it
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = {
            launcher.launch()
            uploadImageTextState.value = false},
            colors = ButtonDefaults.outlinedButtonColors(containerColor= Color.White),
            modifier = Modifier
                .padding(end = 10.dp)
                .width(70.dp)
                .height(50.dp)
                .align(Alignment.CenterVertically),
            border = BorderStroke(width = 1.dp,color = Color.DarkGray),
            shape = RoundedCornerShape(5.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = "camera",
                modifier = Modifier.fillMaxSize(1f)
            )
        }

//        Spacer(modifier = Modifier.height(12.dp))
        if(uploadImageTextState.value){
            Text(text = "Upload Image", modifier = Modifier.padding(top = 15.dp))
        }else{
            bitmap?.let {
                Image(bitmap = bitmap?.asImageBitmap()!!, contentDescription = "", modifier = Modifier.size(200.dp))
            }
        }

    }

}



