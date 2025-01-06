package com.example.reveerdapp

import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.webkit.PermissionRequest
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reveerdapp.models.OTPAction
import com.example.reveerdapp.models.OTPViewModel
import com.example.reveerdapp.utils.setStatusBarColor
import com.example.reveerdapp.views.OnboardingScreen1
import com.example.reveerdapp.views.OnboardingScreen2
import com.example.reveerdapp.views.OnboardingScreen3
import com.example.reveerdapp.views.OnboardingScreen4
import com.example.reveerdapp.views.OnboardingScreen5
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // TODO: Splash screen Implementation using SplashScreenApi
//        installSplashScreen().apply {
//
//            Handler(Looper.getMainLooper()).postDelayed({
//                keepSplashOnScreen = false
//            }, 6000)
//
//            setKeepOnScreenCondition {
//                keepSplashOnScreen
//            }
//        }

        setContent {
            setStatusBarColor(true, window)
            MyUI()
        }

    }
}

@Composable
@Preview
fun MyUI() {


    AppNavigator()
}

@Composable
fun SplashScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.img_splash2),
            contentDescription = "Splash Logo"
        )
    }
    LaunchedEffect(key1 = true) {
        //splash screen delay time
        delay(2000)
        navController.navigate("onboarding1") {
            popUpTo("splash")
            { inclusive = true }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val viewModel = viewModel<OTPViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusRequesters = remember {
        List(6) { FocusRequester() }
    }
    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.focusedIndex) {
        state.focusedIndex?.let { index ->
            focusRequesters.getOrNull(index)?.requestFocus()
        }
    }

    LaunchedEffect(state.code, keyboardManager) {
        val allNumbersEntered = state.code.none { it == null }
        if (allNumbersEntered) {
            focusRequesters.forEach {
                it.freeFocus()
            }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("onboarding1") { OnboardingScreen1(navController) }
        composable("onboarding2") { OnboardingScreen2(navController) }
        composable("onboarding3") { OnboardingScreen3(navController) }
        composable("onboarding4") {
            OnboardingScreen4(
                navController,
                state = state,
                focusRequesters = focusRequesters,
                onAction = { action ->
                    when (action) {
                        is OTPAction.OnEnterNumber -> {
                            if (action.number != null) {
                                focusRequesters[action.index].freeFocus()
                            }
                        }

                        else -> {}
                    }
                    viewModel.onAction(action)
                })
        }
        composable("onboarding5") { OnboardingScreen5(navController) }
        composable("main") { MainScreen() }
    }
}

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Welcome to Main Screen")
    }
}




