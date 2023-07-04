package com.jaya.app.jayamixing.presentation.ui.screen

import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.jayamixing.R
import com.jaya.app.jayamixing.extensions.Text
import com.jaya.app.jayamixing.presentation.viewmodels.BaseViewModel
import com.jaya.app.jayamixing.presentation.viewmodels.LoginViewModel
import com.jaya.app.jayamixing.ui.theme.SplashGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    baseViewModel: BaseViewModel,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp).imePadding(),
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.wrapContentSize().imePadding()) {
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
                    placeholder = { Text("Enter your email Id") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = viewModel.color.value,
                        unfocusedBorderColor = Color.Gray
                    ),
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp).imePadding(),
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
                        viewModel.password.value = it
                        viewModel.color.value= Color.Gray
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.password),
                            contentDescription = "password"
                        )
                    },
                    //label = { Text("your mobile number") },
                    placeholder = { Text("Password") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = viewModel.color.value,
                        unfocusedBorderColor = Color.Gray
                    ),
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp).imePadding(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email
                    ),
                )
//LaunchedEffect(true ){viewModel.emailText.value}

                val context= LocalContext.current


                Button(
                    onClick = {
                        //   Toast.makeText(context, "continue", Toast.LENGTH_SHORT).show()
                        //viewModel.loader.value=true

                        if (!viewModel.emailText.value.isValidEmail() || viewModel.emailText.value.isNullOrEmpty() || viewModel.password.value.isNullOrEmpty()){
                            //viewModel.color.value= Color.Red
                            focusRequester.requestFocus()
                            Toast.makeText(context, "Please enter a valid email or password", Toast.LENGTH_SHORT).show()
                        }else{
                            viewModel.loadingg.value=true
                            baseViewModel.storedLoginEmail.value=viewModel.emailText.value
                            baseViewModel.storedLoginPassword.value=viewModel.password.value
                            viewModel.getOtp()

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
                    enabled = viewModel.loadingButton.value,
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 25.dp)
                        .fillMaxWidth()
                        .height(53.dp).imePadding(),
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