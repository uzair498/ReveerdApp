package com.example.reveerdapp.views

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.example.reveerdapp.R
import com.example.reveerdapp.api.RetrofitInstance
import com.example.reveerdapp.api.simpleLogin.LoginResponse
import com.example.reveerdapp.api.simpleLogin.SimpleLoginDataModel
import com.example.reveerdapp.models.OTPAction
import com.example.reveerdapp.models.OTPState
import com.example.reveerdapp.ui.MyColors
import com.example.reveerdapp.ui.MyFonts
import com.example.reveerdapp.utils.CheckRequiredPermissions
import com.example.reveerdapp.utils.OTPInputField
import com.example.reveerdapp.utils.ShowSettingDialog
import com.example.reveerdapp.utils.openAppSettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "GeneralTag"

@Composable
fun OnboardingScreen1(navController: NavController) {

    val context = LocalContext.current
    var permissionChecked by remember { mutableStateOf(false) }
    var permissionGranted by remember { mutableStateOf(false) }
    var permanentlyDenied by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    if (!permissionChecked) {
        CheckRequiredPermissions { granted, deniedPermanently ->
            permissionGranted = granted
            permissionChecked = true
            permanentlyDenied = deniedPermanently
            if (permissionGranted) {
                navController.navigate("onboarding2")
            } else if (deniedPermanently) {
                showDialog = true
            }
        }
    }

    if (showDialog) {
        ShowSettingDialog(
            onDismiss = { showDialog = false },
        ) {
            showDialog = false
            openAppSettings(context)
        }
    }

    // TODO: Location permission Screen
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.img_background_location),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MyColors.white
            ),
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .height(480.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(R.drawable.ic_location_pin),
                    contentDescription = "Location Pin",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 136.dp, height = 136.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "Enable your location",
                    style = TextStyle(
                        fontFamily = MyFonts.CustomFonts,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "This app needs your location to work properly and help you find requests near your location",
                    style = TextStyle(
                        fontFamily = MyFonts.CustomFonts,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

                ElevatedButton(
                    onClick = {
                        // TODO: Move on further
//                        if (permissionGranted)
//                        {
//                            navController.navigate("onboarding2")
//                        }
                        permissionChecked = false

                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MyColors.locationButtonColor,
                        contentColor = MyColors.white
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 24.dp)


                ) {
//                    Text(text = if (currentPage.value < pages.size - 1) "Next" else "Finish")
                    Text(
                        text = "Use my Location",
                        style = TextStyle(
                            fontFamily = MyFonts.CustomFonts,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    )
                }

//                Spacer(modifier = Modifier.height(14.dp))
//
//                TextButton(
//                    onClick = {},
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = MyColors.white,
//                        contentColor = MyColors.black
//                    )
//                ) {
//                    Text(
//                        text = "Skip for now",
//                        style = TextStyle(
//                            fontFamily = MyFonts.CustomFonts,
//                            fontWeight = FontWeight.Light,
//                            fontSize = 14.sp,
//                            color = MyColors.customOffWhite
//                        )
//                    )
//                }
            }
        }

    }
}

@Composable
fun OnboardingScreen2(navController: NavController) {
    // TODO: LOGIN Screen
    val receivedEmail = remember { mutableStateOf("") }
    val receivedPassword = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MyColors.white)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "Log In",
            style = TextStyle(
                fontFamily = MyFonts.CustomFonts,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            ),
            modifier = Modifier
        )

        // Email Text field
        OutlinedTextField(
            value = receivedEmail.value,
            onValueChange = { newText: String -> receivedEmail.value = newText },
            label = { Text("Email") },
//            placeholder = { Text("Enter your email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )

//        Log.i(TAG, receivedEmail.toString())

        // for showing and hiding the password
        val passwordState = remember { mutableStateOf("") }
        val passwordVisibility = remember { mutableStateOf(false) }

        val icon = if (passwordVisibility.value) {
            ImageVector.vectorResource(id = R.drawable.ic_visibility_on)
        } else {
            ImageVector.vectorResource(id = R.drawable.ic_visibility_off)
        }

        // password text field
        OutlinedTextField(
            value = receivedPassword.value,
            onValueChange = { newPassword: String -> receivedPassword.value = newPassword },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisibility.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(imageVector = icon, contentDescription = "Visibility Icon")
                }
            },

            )

        // forgot password button
        TextButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 20.dp)
        ) {
            Text(
                text = "Forgot Password",
                modifier = Modifier,
                style = TextStyle(
                    color = MyColors.locationButtonColor,
                    fontFamily = MyFonts.CustomFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                )
            )
        }

        // TODO: login button
        ElevatedButton(
            onClick = {

                navController.navigate("onboarding3")

                // TODO: make api call here
//                val loginRequest = SimpleLoginDataModel(
//                    deviceToken = "59482094tlkregslkdfjglkasdjdflg",
//                    email = receivedEmail.toString(),
//                    password = receivedPassword.toString()
//                )
//
//                RetrofitInstance.simpleApiLoginService.login(loginRequest)
//                    .enqueue(object : Callback<LoginResponse> {
//                        override fun onResponse(
//                            call: Call<LoginResponse>,
//                            response: Response<LoginResponse>
//                        ) {
//                            if (response.isSuccessful) {
//                                Log.i(TAG, "Successful reponse = " + response.body().toString())
//                                // navigate to next screen
//                                navController.navigate("onboarding3")
//                            } else {
//                                Log.i(TAG, "UnSuccessful = " + response.code().toString())
//                            }
//                        }
//
//                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                            Log.i(TAG, t.message.toString())
//                        }
//                    })

            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MyColors.locationButtonColor,
                contentColor = MyColors.white
            )

        ) {
            Text(
                text = "Log in",
                style = TextStyle(
                    fontFamily = MyFonts.CustomFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            )
        }

        Text(
            text = "OR",
            style = TextStyle(
                fontFamily = MyFonts.CustomFonts,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        // google login button
        OutlinedButton(
            onClick = {},
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
                .height(60.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MyColors.white
            ),
            border = BorderStroke(1.dp, MyColors.black)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                // google icon
                Image(
                    painterResource(R.drawable.ic_google),
                    contentDescription = "google icon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .sizeIn(minWidth = Dp.Unspecified, minHeight = Dp.Unspecified),

                    )

                Text(
                    text = "Log In with Google",
                    style = TextStyle(
                        fontFamily = MyFonts.CustomFonts,
                        fontSize = 16.sp,
                        color = MyColors.black,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

        }

        // apple login button
        ElevatedButton(
            onClick = {},
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
                .height(60.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MyColors.black
            ),
        ) {

            // apple icon
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Image(
                    painterResource(R.drawable.ic_apple),
                    contentDescription = "google icon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .sizeIn(minWidth = Dp.Unspecified, minHeight = Dp.Unspecified),
                )

                Text(
                    text = "Log In with Apple",
                    style = TextStyle(
                        fontFamily = MyFonts.CustomFonts,
                        fontSize = 16.sp,
                        color = MyColors.white,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

        }

        // facebook login button
        ElevatedButton(
            onClick = {},
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
                .height(60.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MyColors.customSkyBlue
            ),
        ) {

            // facebook icon
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {

                Image(
                    painterResource(R.drawable.ic_fb),
                    contentDescription = "google icon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .sizeIn(minWidth = Dp.Unspecified, minHeight = Dp.Unspecified),
                )

                Text(
                    text = "Log In with Facebook",
                    style = TextStyle(
                        fontFamily = MyFonts.CustomFonts,
                        fontSize = 16.sp,
                        color = MyColors.white,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )


            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account?",
                style = TextStyle(
                    fontFamily = MyFonts.CustomFonts,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    color = MyColors.customOffWhite,
                    fontSize = 14.sp
                )
            )

            // Sign up button
            TextButton(
                onClick = {},
                modifier = Modifier

            ) {
                Text(
                    text = " Sign up",
                    style = TextStyle(
                        fontFamily = MyFonts.CustomFonts,
                        fontWeight = FontWeight.SemiBold,
                        color = MyColors.locationButtonColor,
                        fontSize = 14.sp
                    ),

                    )
            }

        }

    }
}

@Composable
fun OnboardingScreen3(navController: NavController) {

    // TODO: Email Verification Screen = Verification step 1
    val focusManager = LocalFocusManager.current
    val textState = remember { mutableStateOf("") }
    val view = LocalView.current
    val insets = ViewCompat.getRootWindowInsets(view)
    val navigationBarHeightPx =
        insets?.getInsets(WindowInsetsCompat.Type.navigationBars())?.bottom ?: 0
    val navigationBarHeightDp = with(LocalDensity.current) { navigationBarHeightPx.toDp() }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(color = MyColors.white)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            }
            .padding(WindowInsets.ime.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

//        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .padding(top = 56.dp, start = 4.dp)
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    navController.navigate("onboarding2")
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painterResource(id = R.drawable.ic_back),
                contentDescription = "back button",
                modifier = Modifier
                    .sizeIn(minWidth = Dp.Unspecified, minHeight = Dp.Unspecified)
            )

            Text(
                text = "Back",
                style = TextStyle(
                    fontFamily = MyFonts.CustomFonts,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp,
                    color = MyColors.black
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Enter your Email",
            style = TextStyle(
                fontFamily = MyFonts.CustomFonts,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = MyColors.black,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "We will verify if your email exists and send you an OTP code",
            style = TextStyle(
                fontFamily = MyFonts.CustomFonts,
                fontSize = 16.sp,
                color = MyColors.customMsgColor,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val recivedText = remember { mutableStateOf("") }
        OutlinedTextField(
            value = recivedText.value,
            onValueChange = { newText: String -> recivedText.value = newText },
            label = { Text("Email") },
//            placeholder = { Text("Enter your email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )

        // for placing the button at the bottom
        Spacer(modifier = Modifier.weight(1f))

        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = navigationBarHeightDp + 4.dp)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                navController.navigate("onboarding4")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MyColors.locationButtonColor,
                contentColor = MyColors.white
            )
        )
        {
            Text(
                "Send OTP",
                fontSize = 16.sp,
                fontFamily = MyFonts.CustomFonts,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun OnboardingScreen4(
    navController: NavController,
    state: OTPState,
    focusRequesters: List<FocusRequester>,
    onAction: (OTPAction) -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: OTP Verification Screen = Verification step 2

    val focusManager = LocalFocusManager.current
    val textState = remember { mutableStateOf("") }
    val view = LocalView.current
    val insets = ViewCompat.getRootWindowInsets(view)
    val navigationBarHeightPx =
        insets?.getInsets(WindowInsetsCompat.Type.navigationBars())?.bottom ?: 0
    val navigationBarHeightDp = with(LocalDensity.current) { navigationBarHeightPx.toDp() }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(color = MyColors.white)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            }
            .padding(WindowInsets.ime.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Row(
            modifier = Modifier
                .padding(top = 56.dp, start = 4.dp)
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    navController.navigate("onboarding3")
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painterResource(id = R.drawable.ic_back),
                contentDescription = "back button",
                modifier = Modifier
                    .sizeIn(minWidth = Dp.Unspecified, minHeight = Dp.Unspecified)
            )

            Text(
                text = "Back",
                style = TextStyle(
                    fontFamily = MyFonts.CustomFonts,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp,
                    color = MyColors.black
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Enter OTP",
            style = TextStyle(
                fontFamily = MyFonts.CustomFonts,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = MyColors.black,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Please Enter 6 digit OTP sent to your email address",
            style = TextStyle(
                fontFamily = MyFonts.CustomFonts,
                fontSize = 16.sp,
                color = MyColors.customMsgColor,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        //TODO: OTP Fields will go here
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            state.code.forEachIndexed { index, number ->
                OTPInputField(
                    number = number,
                    focusRequester = focusRequesters[index],
                    onfocusChanged = { isFocused ->
                        if (isFocused) {
                            onAction(OTPAction.OnChangeFieldFocused(index))
                        }
                    },
                    onNumberChanged = { newNumber ->
                        onAction(OTPAction.OnEnterNumber(newNumber, index))
                    },
                    onKeyboardBack = {
                        onAction(OTPAction.OnKeyboardBack)
                    },
                    modifier = Modifier
                        .weight(1f)
//                        .aspectRatio(1f) for making them square
                )
            }
        }

        state.isValid?.let { isValid ->
            Text(
                text = if (isValid) "OTP is valid" else "OTP is not valid",
                color = if (isValid) MyColors.locationButtonColor else Color.Red,
                fontSize = 16.sp


            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Didn't receive code?",
                fontSize = 16.sp,
                fontFamily = MyFonts.CustomFonts,
                fontWeight = FontWeight.SemiBold
            )
            TextButton(
                onClick = {}

            ) {
                Text(
                    text = "Rsend again",
                    fontSize = 16.sp,
                    fontFamily = MyFonts.CustomFonts,
                    fontWeight = FontWeight.SemiBold,
                    color = MyColors.locationButtonColor
                )
            }
        }


        // for placing the button at the bottom
        Spacer(modifier = Modifier.weight(1f))

        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = navigationBarHeightDp + 4.dp)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                navController.navigate("onboarding5")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MyColors.locationButtonColor,
                contentColor = MyColors.white
            )
        )
        {
            Text(
                "Verify",
                fontSize = 16.sp,
                fontFamily = MyFonts.CustomFonts,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun OnboardingScreen5(navController: NavController) {

    val focusManager = LocalFocusManager.current
    val view = LocalView.current
    val insets = ViewCompat.getRootWindowInsets(view)
    val navigationBarHeightPx =
        insets?.getInsets(WindowInsetsCompat.Type.navigationBars())?.bottom ?: 0
    val navigationBarHeightDp = with(LocalDensity.current) { navigationBarHeightPx.toDp() }
    val receivedPassword = remember { mutableStateOf("") }
    val confirmNewPassowrd = remember { mutableStateOf("") }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(color = MyColors.white)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            }
            .padding(WindowInsets.ime.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

//        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .padding(top = 56.dp, start = 4.dp)
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    navController.navigate("onboarding4")
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painterResource(id = R.drawable.ic_back),
                contentDescription = "back button",
                modifier = Modifier
                    .sizeIn(minWidth = Dp.Unspecified, minHeight = Dp.Unspecified)
            )

            Text(
                text = "Back",
                style = TextStyle(
                    fontFamily = MyFonts.CustomFonts,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp,
                    color = MyColors.black
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Set New Password",
            style = TextStyle(
                fontFamily = MyFonts.CustomFonts,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = MyColors.black,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Set your new password",
            style = TextStyle(
                fontFamily = MyFonts.CustomFonts,
                fontSize = 16.sp,
                color = MyColors.customMsgColor,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // New password field
        val passwordVisibility = remember { mutableStateOf(false) }

        val icon = if (passwordVisibility.value) {
            ImageVector.vectorResource(id = R.drawable.ic_visibility_on)
        } else {
            ImageVector.vectorResource(id = R.drawable.ic_visibility_off)
        }
        // password text field
        OutlinedTextField(
            value = receivedPassword.value,
            onValueChange = { newPassword: String -> receivedPassword.value = newPassword },
//            label = { Text("Password") },
            placeholder = { Text("Enter Your new password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisibility.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(imageVector = icon, contentDescription = "Visibility Icon")
                }
            },
        )

        // Confirm new passowrd
        OutlinedTextField(
            value = confirmNewPassowrd.value,
            onValueChange = { newPassword: String -> confirmNewPassowrd.value = newPassword },
//            label = { Text("Password") },
            placeholder = { Text("Confirm password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisibility.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(imageVector = icon, contentDescription = "Visibility Icon")
                }
            },
        )

        // for placing the button at the bottom
        Spacer(modifier = Modifier.weight(1f))

        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = navigationBarHeightDp + 4.dp)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                navController.navigate("main")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MyColors.locationButtonColor,
                contentColor = MyColors.white
            )
        )
        {
            Text(
                "Save",
                fontSize = 16.sp,
                fontFamily = MyFonts.CustomFonts,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}