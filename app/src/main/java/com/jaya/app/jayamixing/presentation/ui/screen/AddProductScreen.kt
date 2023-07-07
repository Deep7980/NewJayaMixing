package com.jaya.app.jayamixing.presentation.ui.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.jayamixing.R
import com.jaya.app.jayamixing.extensions.BackPressHandler
import com.jaya.app.jayamixing.extensions.Image
import com.jaya.app.jayamixing.extensions.Text
import com.jaya.app.jayamixing.presentation.ui.custom_view.CuttingManDropDown
import com.jaya.app.jayamixing.presentation.ui.custom_view.FloorManagerDropdown
import com.jaya.app.jayamixing.presentation.ui.custom_view.MixingManDropdown
import com.jaya.app.jayamixing.presentation.ui.custom_view.OvenManDropdown
import com.jaya.app.jayamixing.presentation.ui.custom_view.PackingSupervisorDropdown
import com.jaya.app.jayamixing.presentation.ui.custom_view.PlantDropdown
import com.jaya.app.jayamixing.presentation.ui.custom_view.ProductsDropdown
import com.jaya.app.jayamixing.presentation.viewmodels.AddProductViewModel
import com.jaya.app.jayamixing.presentation.viewmodels.BaseViewModel
import com.jaya.app.jayamixing.presentation.viewmodels.DashboardViewModel
import com.jaya.app.jayamixing.ui.theme.AppBarYellow
import com.jaya.app.jayamixing.ui.theme.LogoutRed
import com.jaya.app.jayamixing.ui.theme.Primary
import com.jaya.app.jayamixing.ui.theme.Secondary
import com.jaya.app.jayamixing.ui.theme.SplashGreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AddProductScreen(
    dashboardViewModel: DashboardViewModel= hiltViewModel(),
    addProductViewModel: AddProductViewModel = hiltViewModel(),
    baseViewModel: BaseViewModel
) {
    val currentScreen = remember { mutableStateOf(DrawerAppScreen.Dashboard) }
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val ctx = LocalContext.current

    BackPressHandler(onBackPressed = {addProductViewModel.backToDashboardPage()})
    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxSize(),
        drawerContent = {
            DrawerComponent(
                currentScreen = currentScreen,
                closeDrawer = { coroutineScope.launch { drawerState.close() } },
                dashboardViewModel,
                addProductViewModel
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(AppBarYellow)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth()
                ) {
                    IconButton(
                        modifier = Modifier.padding(top = 5.dp),
                        onClick = {
                            coroutineScope.launch { drawerState.open() }
                            // Toast.makeText(ctx, "drawer contents", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(Icons.Filled.Menu, "")
                    }

                    Image(
                        painter = painterResource(id = R.drawable.cropped_logo),
                        contentDescription = "jayaLogo",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(8.dp)
                    )

                    IconButton(
                        onClick = {
                            Toast.makeText(ctx, "Notifications", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 5.dp)
                    ) {
                        Icon(Icons.Filled.Notifications, "")
                    }
                }
            }

            AnimatedContent(
                targetState = addProductViewModel.dataLoading,
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                transitionSpec = {
                    fadeIn(animationSpec = tween(700)) + slideInVertically(animationSpec = tween(400),
                        initialOffsetY = { fullHeight -> fullHeight }) with
                            fadeOut(animationSpec = tween(400))
                }
            ) {targetState ->
                when(targetState){
                    true -> Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier.size(150.dp), contentAlignment = Alignment.Center
                        ) {
                            Surface(modifier = Modifier.fillMaxSize(),
                                shape = CircleShape,
                                color = com.jaya.app.jayamixing.ui.theme.Surface,
                                content = {})
                            CircularProgressIndicator(
                                color = Secondary, modifier = Modifier.fillMaxSize()
                            )
                            R.drawable.cropped_logo.Image(modifier = Modifier.size(100.dp))
                        }
                    }
                    false -> BodyComponent(
                        currentScreen = currentScreen.value,
                        openDrawer = { coroutineScope.launch { drawerState.open() } },
                        viewModel = hiltViewModel(),
                        baseViewModel
                    )
                }
            }

//            BodyComponent(
//                currentScreen = currentScreen.value,
//                openDrawer = { coroutineScope.launch { drawerState.open() } },
//                viewModel = hiltViewModel(),
//                baseViewModel
//            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyComponent(
    currentScreen: DrawerAppScreen,
    openDrawer: () -> Unit,
    viewModel: AddProductViewModel,
    baseViewModel: BaseViewModel
) {

    AddProductFormPage(viewModel = viewModel, baseViewModel = baseViewModel)

}



@Composable
fun DrawerComponent(
    currentScreen: MutableState<DrawerAppScreen>,
    closeDrawer: () -> Unit,
    viewModel: DashboardViewModel,
    addProductViewModel: AddProductViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(270.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Primary)
    ) {

        Image(
            painter = painterResource(id = R.drawable.cropped_logo),
            contentDescription = "Jaya Logo",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.10f)
                .padding(top = 20.dp),
        )

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    //.height(100.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = viewModel.userName.value,
                    color = Color.White, // Header Color
                    fontSize = 28.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp)
                )
//                Text(
//                    text = viewModel.userId.value,
//                    color = Color.White, // Header Color
//                    fontSize = 15.sp,
//                    textAlign = TextAlign.Start,
//                    fontWeight = FontWeight.Normal,
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
//                    //.padding(top = 8.dp)
//                )
//                Text(
//                    text = viewModel.emailId.value,
//                    color = Color.White, // Header Color
//                    fontSize = 15.sp,
//                    textAlign = TextAlign.Start,
//                    fontWeight = FontWeight.Normal,
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
//                    // .padding(top = 8.dp)
//                )
                Text(
                    text = viewModel.designation.value,
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp)
                )

            }
        Divider(color = Color.White)

        //  Spacer(modifier = Modifier.padding(top = 80.dp))

        for (index in DrawerAppScreen.values().indices) {
            val screen = getScreenBasedOnIndex(index)
            Column(
                 content = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(1.2f)
                        .padding(20.dp)
                        .clickable(
                            onClick = {
                                addProductViewModel.backToDashboardPage()
                                closeDrawer()
                            }
                        ),
                    shape = RoundedCornerShape(20.dp),
                    color = if (currentScreen.value == screen) {
                        White
                    } else {
                        Color.LightGray
                    }
                ) {
                    Text(text = screen.name,modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Medium)
                }
            })
        }


        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(LogoutRed)
                .clickable {
                    viewModel.onLogoutFromDashboard()
                }) {

//                Text(
//                    text = R.string.logout.toString(),
//                    color = Color.White,
//                    fontSize = 17.sp,
//                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .padding(start = 20.dp)
//                        .weight(1f)
//                )
                R.string.logout.Text(
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 17.sp,
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 20.dp)
                        .weight(1f)
                )

                Image(
                    painter = painterResource(id = R.drawable.baseline_logout_24),
                    contentDescription = "LogOut img",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 10.dp)
                )

            }
        }
    }
}