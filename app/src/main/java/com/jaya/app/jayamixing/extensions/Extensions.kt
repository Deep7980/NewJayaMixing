package com.jaya.app.jayamixing.extensions

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.net.Uri
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jaya.app.core.common.DataEntry
import com.jaya.app.core.common.EmitType
import com.jaya.app.jayamixing.helpers_impl.SavableMutableState

@Composable
fun Int.resourceString(): String {
    return stringResource(id = this)
}

//fun Int.string(): String {
//    return FarmologyDeliveryApp.appInstance.baseContext.resources.getString(this)
//}

@Composable
fun Int.dimen(): Dp {
    return dimensionResource(id = this)
}

@OptIn(ExperimentalUnitApi::class, ExperimentalUnitApi::class)
@Composable
fun Int.toSp(): TextUnit {
    return TextUnit(dimensionResource(id = this@toSp).value, TextUnitType.Sp)
}


fun String.shortToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}


fun String.longToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}




fun Float.dp(): Float = this * density + 0.5f

val density: Float
    get() = Resources.getSystem().displayMetrics.density

//
//fun <T> handleExceptions(: HttpClient, response: T): Resource<T> {
//    return try {
//        Resource.Success(data = response)
//    } catch (ex: RedirectResponseException) {
//        Resource.Error(ex.message ?: "")
//    } catch (ex: ClientRequestException) {
//        Resource.Error(ex.message ?: "")
//    } catch (ex: ServerResponseException) {
//        Resource.Error(ex.message ?: "")
//    } catch (ex: Exception) {
//        Resource.Error(ex.message ?: "")
//    }
//}


fun String.asColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}

@Composable
fun <T> MutableState<T>.OnEffect(
    intentionalCode: suspend (T) -> Unit,
    clearance: () -> T,
) {
    LaunchedEffect(key1 = value) {
        value?.let {
            intentionalCode(it)
            value = clearance()
        }
    }
}

fun Context.openPlayStore(link: String) {
    val intent = Intent("market://details?id=$packageName")
    var isFound = false

    val otherApps: List<ResolveInfo> = packageManager.queryIntentActivities(intent, 0)
    for (otherApp in otherApps) {
        // look for Google Play application
        if (otherApp.activityInfo.applicationInfo.packageName
                .equals("com.android.vending")
        ) {
            val otherAppActivity: ActivityInfo = otherApp.activityInfo
            val componentName = ComponentName(
                otherAppActivity.applicationInfo.packageName,
                otherAppActivity.name
            )
            // make sure it does NOT open in the stack of your activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            // task reparenting if needed
            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            // if the Google Play was already open in a search result
            //  this make sure it still go to the app page you requested
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            // this make sure only the Google Play app is allowed to
            // intercept the intent
            intent.component = componentName
            startActivity(intent)
            isFound = true
            break
        }
    }

    if (!isFound) {
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(link)
        )
        startActivity(webIntent)
    }
}


fun <T> Any.castListToRequiredTypes(): List<T>? {
    val items = mutableListOf<T>()

    if (this !is List<*>) return null

    forEach { item -> item?.let { items.add(it as T) } }

    return items.toList()
}

inline fun <reified T1, reified T2> Any.castMapToRequiredTypes(): Map<T1, T2>? {

    val data = mutableMapOf<T1, T2>()

    if (this !is Map<*, *>) return null

    forEach { (k, v) -> data[k as T1] = v as T2 }

    return data.toMap()
}


inline fun <reified T> Any.castValueToRequiredTypes(): T? {
    return this as? T
}

inline fun <reified K, reified V> Any.castPairToRequiredType(): Pair<K, V>? {

    if (this !is Pair<*, *>) return null

    return (this.first as K) to (this.second as V)
}


@Composable
fun <T> MutableState<T>.ComposeLaunchEffect(
    intentionalCode: suspend (T) -> Unit,
    clearance: () -> T,
) {
    LaunchedEffect(key1 = value) {
        value?.let {
            intentionalCode(it)
            value = clearance()
        }
    }
}
@Composable
fun Int.Text(style: TextStyle, modifier: Modifier = Modifier) =
    androidx.compose.material3.Text(text = stringResource(id = this), style = style,modifier = modifier)
//    androidx.compose.material.Text(
//        text = stringResource(id = this),
//        style = style,
//        modifier = modifier
//    )

@Composable
fun Int.text(style: TextStyle, modifier: Modifier = Modifier) =
    androidx.compose.material3.Text(text = stringResource(id = this), style = style, modifier = modifier)
//    Text(text = stringResource(id = this), style = style, modifier = modifier)


@UiComposable
@Composable
fun <T> SavableMutableState<T>.OnEffect(
    intentionalCode: suspend (T) -> Unit,
    clearance: (() -> T)? = null,
) {
    LaunchedEffect(key1 = value) {
        value?.let {
            intentionalCode(it)
            if(clearance != null) {
                reset(clearance())
            }
        }
    }
}

val screenHeight: Dp
    @Composable
    @ReadOnlyComposable
    get() = LocalConfiguration.current.screenHeightDp.dp

val screenWidth: Dp
    @Composable
    @ReadOnlyComposable
    get() = LocalConfiguration.current.screenWidthDp.dp


inline fun <reified T> T.encodeJson(): String = Gson().toJson(this, object : TypeToken<T>() {}.type)

inline fun <reified T> String.decodeJson(): T =
    Gson().fromJson(this, object : TypeToken<T>() {}.type)
fun <T> animationSpec() = tween<T>(
    durationMillis = 400,
    easing = LinearOutSlowInEasing
)

@OptIn(ExperimentalAnimationApi::class)
fun upToBottom() = slideInVertically(
    initialOffsetY = {-it},
    animationSpec = animationSpec()
) + fadeIn(animationSpec = animationSpec()) with slideOutVertically(
    targetOffsetY = {it},
    animationSpec = animationSpec()
) + fadeOut(animationSpec())

@Composable
fun Int.Image(modifier: Modifier = Modifier, scale: ContentScale = ContentScale.Fit) =
    androidx.compose.foundation.Image(
        painter = painterResource(id = this),
        contentDescription = toString(),
        modifier = modifier,
        contentScale = scale,
    )


@OptIn(ExperimentalAnimationApi::class)
fun bottomToUp() = slideInVertically(
    initialOffsetY = {it},
    animationSpec = animationSpec()
) + fadeIn(animationSpec()) with slideOutVertically(
    targetOffsetY = {-it},
    animationSpec = animationSpec()
) + fadeOut(animationSpec = animationSpec())

//fun DataEntry.handleErrors(): String? = when (type) {
//    EmitType.NETWORK_ERROR -> data.castValueToRequiredTypes<String>()
//    EmitType.BACKEND_ERROR -> data.castValueToRequiredTypes<String>()
//    else -> null
//}


fun Activity.changeStatusBarColor(color: Long) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = color.toInt()
}