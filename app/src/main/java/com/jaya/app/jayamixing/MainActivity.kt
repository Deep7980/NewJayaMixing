package com.jaya.app.jayamixing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jaya.app.jayamixing.navigation.MainNavGraph
import com.jaya.app.jayamixing.presentation.viewmodels.BaseViewModel
import com.jaya.app.jayamixing.ui.theme.JayaMixingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val baseViewModel by viewModels<BaseViewModel>()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JayaMixingTheme(baseViewModel) {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                ) { paddingValues ->
                    CompositionLocalProvider() {
                        MainNavGraph(
                            navHostController = rememberNavController(),
                            navigationChannel = baseViewModel.appNavigator.navigationChannel,
                            paddingValues = paddingValues,
                            baseViewModel = baseViewModel,
                        )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        baseViewModel.connectivity.listeningNetworkState()
    }

    override fun onDestroy() {
        super.onDestroy()
        baseViewModel.connectivity.stopListenNetworkState()
    }
}