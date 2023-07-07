package com.jaya.app.jayamixing.presentation.ui.screen

import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Visibility
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.jayamixing.R
import com.jaya.app.jayamixing.extensions.BackPressHandler
import com.jaya.app.jayamixing.extensions.Text
import com.jaya.app.jayamixing.presentation.viewmodels.BaseViewModel
import com.jaya.app.jayamixing.presentation.viewmodels.LoginViewModel
import com.jaya.app.jayamixing.ui.theme.SplashGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun LoginScreen(
    baseViewModel: BaseViewModel,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val bringViewRequester = remember { BringIntoViewRequester() }
    val uiScope = rememberCoroutineScope()

//    BackPressHandler(onBackPressed = {viewModel.onBackDialog()})
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)


    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier
                .wrapContentSize()
                .navigationBarsPadding()
                .imePadding()
                ) {
                Image(
                    painter = painterResource(R.drawable.cropped_logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(180.dp)
                        .height(130.dp),
                )
                R.string.welcome_to_jaya_industries.Text(
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.DarkGray,
                        fontSize = 25.sp
                    ),
                    modifier = Modifier
                        .padding(bottom = 12.dp, start = 8.dp, end = 8.dp)
                        .align(Alignment.CenterHorizontally),
                )

                R.string.login_here.Text(
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .padding(bottom = 30.dp, start = 20.dp, end = 20.dp)
                        .align(Alignment.CenterHorizontally),
                )

                //----------------------------------------------------------------------------------------------------------------
                fun String.isValidEmail(): Boolean {
                    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this)
                        .matches()
                }

//----------------------------------------------------------------------------------------------------------------

                OutlinedTextField(
                    value = viewModel.emailText.value,
                    // onValueChange = { viewModel.emailText.value = it },
                    onValueChange = {
                        viewModel.emailText.value = it
                        viewModel.color.value= Color.Gray
                        if (viewModel.emailText.value.isValidEmail())
                            viewModel.color.value= SplashGreen
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = "email"
                        )
                    },
                    //label = { Text("your mobile number") },
                    placeholder = { Text("Enter your email Id", color = Color.Gray) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = viewModel.color.value,
                        unfocusedBorderColor = Color.Gray
                    ),
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .bringIntoViewRequester(bringViewRequester)
                        .onFocusEvent {
                            if (it.isFocused) {
                                uiScope.launch {
                                    bringViewRequester.bringIntoView()
                                }
                            }
                        },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email
                    ),
                )

                OutlinedTextField(
                    value = viewModel.password.value,
                    // onValueChange = { viewModel.emailText.value = it },
                    onValueChange = {
                        if(it.length < 10) viewModel.password.value = it
                        viewModel.passwordFieldcolor.value = Color.Gray
                        if (!viewModel.password.value.isEmpty())
                            viewModel.passwordFieldcolor.value = SplashGreen
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.password),
                            contentDescription = "password"
                        )
                    },
                    //label = { Text("your mobile number") },
                    placeholder = { Text("Password", color = Color.Gray) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = viewModel.color.value,
                        unfocusedBorderColor = Color.Gray
                    ),
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .bringIntoViewRequester(bringViewRequester)
                        .onFocusEvent {
                            if (it.isFocused) {
                                uiScope.launch {
                                    bringViewRequester.bringIntoView()
                                }
                            }
                        },
                    singleLine = true,
                    visualTransformation = if (viewModel.showHidepasswordText.value) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    trailingIcon = {
                        if (!viewModel.password.value.isNullOrEmpty())
                            if (viewModel.showHidepasswordText.value) {
                                IconButton(onClick = { viewModel.showHidepasswordText.value = false }) {
                                    Icon(
                                        imageVector = Icons.Filled.Visibility,
                                        contentDescription = "hide_password"
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = { viewModel.showHidepasswordText.value = true }) {
                                    Icon(
                                        imageVector = Icons.Filled.VisibilityOff,
                                        contentDescription = "show_password"
                                    )
                                }
                            }
                    }

                )
//LaunchedEffect(true ){viewModel.emailText.value}

                val context= LocalContext.current


                Button(
                    onClick = {
                        //   Toast.makeText(context, "continue", Toast.LENGTH_SHORT).show()
                        //viewModel.loader.value=true

                        if (!viewModel.emailText.value.isValidEmail() || viewModel.emailText.value.isNullOrEmpty() ){
                            //viewModel.color.value= Color.Red
                            focusRequester.requestFocus()
                            Toast.makeText(context, "Please enter a valid Email ", Toast.LENGTH_SHORT).show()
                        }else if(viewModel.password.value.isEmpty()){
                            focusRequester.requestFocus()
                            Toast.makeText(context, "Please enter a valid Password ", Toast.LENGTH_SHORT).show()
                        }else{
                            viewModel.loadingg.value=true
                            baseViewModel.storedLoginEmail.value=viewModel.emailText.value
                            baseViewModel.storedLoginPassword.value=viewModel.password.value
                            viewModel.loginIntoApp()

                            val timer = object : CountDownTimer(5000, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    // Toast.makeText(context, "${viewModel.timerX.value}", Toast.LENGTH_SHORT).show()
                                }
                                override fun onFinish() {
                                    //Toast.makeText(context, "${viewModel.successMessage.value}", Toast.LENGTH_SHORT).show()
                                }
                            }
                            timer.start()

                        }


                    },
                    enabled = viewModel.decideButtonState(),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 25.dp)
                        .fillMaxWidth()
                        .height(53.dp)
                        .bringIntoViewRequester(bringViewRequester)
                        .onFocusEvent {
                            if (it.isFocused) {
                                uiScope.launch {
                                    bringViewRequester.bringIntoView()
                                }
                            }
                        },
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) {
                    if (!viewModel.loadingg.value){
                        Text(
                            text = "Login Now",
                            color = Color.White,
                            fontSize = 18.sp,
                        )
                    }else{
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 1.dp,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(end = 10.dp)
                        )
                        R.string.loading.Text(style = MaterialTheme.typography.labelMedium)
                    }
                }


            }
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ){
                R.string.all_rights_reserved.Text(
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                        fontSize = 13.sp
                    ),
                    modifier = Modifier

                )
            }


        }


    }
}