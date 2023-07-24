package com.jaya.app.jayamixing.presentation.ui.screen

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.jayamixing.R
import com.jaya.app.jayamixing.extensions.OnEffect
import com.jaya.app.jayamixing.extensions.Text
import com.jaya.app.jayamixing.extensions.openPlayStore
import com.jaya.app.jayamixing.presentation.viewmodels.SplashViewModel
import com.jaya.app.jayamixing.ui.theme.AppBarYellow
import com.jaya.app.jayamixing.ui.theme.LogoutRed
import com.jaya.app.jayamixing.ui.theme.Primary
import com.jaya.app.jayamixing.ui.theme.SplashGreen
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(viewModel: SplashViewModel = hiltViewModel()) {

    val animation =
        updateTransition(targetState = viewModel.splashAnimation.value, label = "").animateDp(
            label = ""
        ) {
            when (it) {
                true -> 0.dp
                false -> 220.dp
            }
        }
    val fontAnimation =
        updateTransition(
            targetState = viewModel.splashAnimation.value,
            label = ""
        ).animateFloat(label = "", transitionSpec = {
            tween(500)
        }) {
            when (it) {
                true -> 0f
                false -> 1f
            }
        }

    Column(
        modifier = Modifier
            .background(Primary)
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
    ) {

        val scale = remember {
            Animatable(0f)
        }
        // AnimationEffect
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 0.7f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = {
                        OvershootInterpolator(4f).getInterpolation(it)
                    })
            )
            delay(5000L)
            //navController.navigate(AppRoutes.MOBILE_NO)
            //  viewModel.onSplashToLogin()
        }

        // Image
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.wrapContentSize(),horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center)
            {
                Image(
                    painter = painterResource(R.drawable.cropped_logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(animation.value)
//                        .padding(start = 60.dp)
                       ,
                )
                R.string.app_name.Text(
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Red
                    ),
                    modifier = Modifier.graphicsLayer {
                        scaleX = fontAnimation.value
                        scaleY = fontAnimation.value
                    }
                )
            }

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(vertical = 12.dp)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                viewModel.connectivityStatus.collectAsState().apply {
                    Log.d("ConnectivityStatus", "SplashScreen: ${viewModel.connectivityStatus.value}")
                    when (value) {
                        true -> {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 1.dp,
                                modifier = Modifier.size(48.dp)
                            )
                            R.string.loading.Text(style = MaterialTheme.typography.bodyLarge)
                        }

                        false -> {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.size(48.dp)
                            )
                            R.string.no_internet.Text(style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }

        }

    }//box



    val context = LocalContext.current
    viewModel.versionUpdateDialog.value?.apply {
        if (currentState()) {
            AlertDialog(
                containerColor = AppBarYellow,
                shape = RoundedCornerShape(10.dp),
                onDismissRequest = {
                    // openDialog.value = false
                },
                title = {
                    // Text(text = context.getString(R.string.app_name), color = Color.DarkGray)
                    currentData?.title?.let {
                        Text(text =it,color = Color.DarkGray,fontSize = 20.sp)
                    }
                },
                text = {

                    currentData?.message?.let {
                        Text(text = it,color = Color.Gray,fontSize = 15.sp ) //version message from Api
                    }

                },
                dismissButton = {
                    //  if (!(currentData?.data as AppVersion).isSkipable) {
                    currentData?.negative?.let {
                        Button(
                            onClick = {
                                onDismiss?.invoke(null)
                            },
                            colors = ButtonDefaults.buttonColors(LogoutRed),
                            shape = RoundedCornerShape(7.dp),
                            modifier = Modifier.padding(end = 10.dp)

                        ) {
                            Text(text = it)
                        }
                    }
                    //  }

                },
                confirmButton = {
                    currentData?.positive?.let {
                        Button(
                            onClick = { onConfirm?.invoke(currentData?.data) },
                            colors = ButtonDefaults.buttonColors(SplashGreen),
                            shape = RoundedCornerShape(7.dp),
                        ) {
                            Text(text = it)
                        }
                    }


                }

            )
        }
    }
    EffectHandlers(viewModel)

//    Column(
//        modifier = Modifier
//            .wrapContentHeight()
//            .padding(vertical = 12.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        viewModel.connectivityStatus.collectAsState().apply {
//            when (value) {
//                true -> {
//                    CircularProgressIndicator(
//                        color = Color.White,
//                        strokeWidth = 1.dp,
//                        modifier = Modifier
//                    )
//                    R.string.loading.Text(style = MaterialTheme.typography.bodyMedium)
//                }
//
//                false -> {
//                    Icon(
//                        imageVector = Icons.Default.Close,
//                        contentDescription = null,
//                        tint = Color.Red,
//                        modifier = Modifier.size(48.dp)
//                    )
//                    R.string.no_internet.Text(style = MaterialTheme.typography.labelMedium)
//                }
//            }
//        }
//    }

}

@Composable
private fun EffectHandlers(viewModel: SplashViewModel) {
    val localContext = LocalContext.current

    viewModel.appPlayStoreLink.OnEffect(
        intentionalCode = {
            if (it.isNullOrEmpty()) {
                localContext.openPlayStore(it!!)
            }
        },
        clearance = { null }
    )
}