package com.jaya.app.jayamixing.presentation.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.jayamixing.R
import com.jaya.app.jayamixing.extensions.BackPressHandler
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
import com.jaya.app.jayamixing.ui.theme.SplashGreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
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

    BackPressHandler(onBackPressed = {})
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

            BodyComponent(
                currentScreen = currentScreen.value,
                openDrawer = { coroutineScope.launch { drawerState.open() } },
                viewModel = hiltViewModel(),
                baseViewModel
            )
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
            .background(Color.White)
    ) {

        Card(
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.addBtnDeepGreenColor)),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 7.dp, bottom = 15.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, SplashGreen),
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    //.height(100.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = viewModel.userName.value,
                    color = Color.White, // Header Color
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                )
                Text(
                    text = viewModel.userId.value,
                    color = Color.White, // Header Color
                    fontSize = 15.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                    //.padding(top = 8.dp)
                )
                Text(
                    text = viewModel.emailId.value,
                    color = Color.White, // Header Color
                    fontSize = 15.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                    // .padding(top = 8.dp)
                )
                Text(
                    text = viewModel.designation.value,
                    color = Color.White,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp)
                )

            }
        }

        //  Spacer(modifier = Modifier.padding(top = 80.dp))

        for (index in DrawerAppScreen.values().indices) {
            val screen = getScreenBasedOnIndex(index)
            Column(Modifier.clickable(onClick = {
                addProductViewModel.navigate()
                currentScreen.value = screen
                closeDrawer()
            }), content = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = if (currentScreen.value == screen) {
                        AppBarYellow
                    } else {
                        Color.LightGray
                    }
                ) {
                    Text(text = screen.name, modifier = Modifier.padding(16.dp))
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

                Text(
                    text = "Logout",
                    color = Color.White,
                    fontSize = 17.sp,
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