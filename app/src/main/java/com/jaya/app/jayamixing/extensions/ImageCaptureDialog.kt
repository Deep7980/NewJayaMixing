package com.jaya.app.jayamixing.extensions

import android.R
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.jaya.app.jayamixing.presentation.viewmodels.AddProductViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCaptureDialog(
    setShowDialog: (Boolean) -> Unit,
    onImageCaptured: (Bitmap?) -> Unit,
    onGalleryImageCapturd: (Uri?) -> Unit
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(), onResult = onImageCaptured
    )
    val galleryLauncher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.PickVisualMedia(), onResult = onGalleryImageCapturd)
    Dialog(onDismissRequest = { setShowDialog(true) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.Yellow
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(110.dp)) {
                            Text(
                                text = "Choose Image",
                                modifier = Modifier.padding(start = 10.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "",
                                tint = colorResource(R.color.darker_gray),
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                                    .clickable {
                                        setShowDialog(false)
                                    }
                            )
                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 55.dp),
                            horizontalArrangement = Arrangement.spacedBy(30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_menu_gallery),
                                contentDescription = "gallery",
                                modifier = Modifier
                                    .fillMaxWidth(0.40f)
                                    .fillMaxSize(0.20f)
                                    .clickable{
//                                        galleryLauncher.launch("image/*")
                                        galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                                    }
                            )

                            Image(
                                painter = painterResource(id = R.drawable.ic_menu_camera),
                                contentDescription = "camera",
                                modifier = Modifier
                                    .fillMaxWidth(0.40f)
                                    .fillMaxSize(0.30f)
                                    .clickable(onClick = launcher::launch)
                            )
                        }

                    }
//------------------------------------------
                    Spacer(modifier = Modifier.height(10.dp))

                    val ctx = LocalContext.current


                    //viewModel.dialogexpenseValue.value=""


                }
            }
        }
    }
}