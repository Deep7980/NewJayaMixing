package com.jaya.app.jayamixing.presentation.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.jayamixing.R
import com.jaya.app.jayamixing.extensions.Text
import com.jaya.app.jayamixing.extensions.screenWidth
import com.jaya.app.jayamixing.presentation.viewmodels.BaseViewModel
import com.jaya.app.jayamixing.presentation.viewmodels.RecordSubmissionViewModel
import com.jaya.app.jayamixing.ui.theme.AppBarYellow
import com.jaya.app.jayamixing.ui.theme.Primary
import com.jaya.app.jayamixing.ui.theme.SplashGreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordSubmissionScreen(
    baseViewModel: BaseViewModel,
    recordSubmissionViewModel: RecordSubmissionViewModel = hiltViewModel(),
) {

    Surface(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current

        Column(
            modifier = Modifier.fillMaxSize().fillMaxHeight(),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(AppBarYellow)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back_svg),
                    contentDescription = null,
                    // tint = Color.Gray,
                    modifier = Modifier
                        .width(screenWidth * 0.15f)
                        .padding(start = 10.dp)
                        .align(Alignment.CenterVertically)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
//                                CoroutineScope(Dispatchers.Default).launch {
//                                    viewModel.popScreen()
//                                }
                                recordSubmissionViewModel.onBackToAddProductPage()
                            },
                            role = Role.Image
                        )
                )

                Text(
                    text = "Thank you",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 10.dp),
                    color = Color.DarkGray,
                    style = LocalTextStyle.current.copy(fontSize = 22.sp),
                    fontWeight = FontWeight.Bold
                )

            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                R.string.record_submission_message.Text(
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp
                    )
                )
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .height(85.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 30.dp),
                    shape = RoundedCornerShape(5.dp),
                    onClick = {recordSubmissionViewModel.onBackToDashboardPage()},
                    colors = ButtonDefaults.buttonColors(SplashGreen),
                    border = BorderStroke(0.5.dp, Color.LightGray),
                    elevation = ButtonDefaults.buttonElevation(20.dp)

                ) {
                    Text(
                        text = "Back to Dashboard",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }

        }
    }

}

@Composable
fun bodyContent(){
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
}
