package com.mohit.proddevenvironmet.Screens.MainScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohit.proddevenvironmet.ApiResponseHandler.ApiResult
import com.mohit.proddevenvironmet.BuildConfig
import com.mohit.proddevenvironmet.CommonConstants.Constants
import com.mohit.proddevenvironmet.RoomDataBase.Table.ModuleEntity

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun MainScreen(viewModel: UserViewModel = hiltViewModel()) {
    val baseurl = BuildConfig.BASE_URL
    val userlist by viewModel.users.observeAsState(emptyList())
    val state = viewModel.state.collectAsState()
      val result = state.value
    val modulelist by viewModel.modules.observeAsState(emptyList())
    val url = "dms_login"
    val username = "121213@bhaskar"
    val pass = "121213"
    val vname = "2.0.1"
    val vcode = 31
    val token = "ciAK6sscSRGyeqsOCsv-1c:APA91bHy4DlV1sQEhFqrtXm0tNSHAlZM2CZ4u7_KC_bc89j1cCprThHONExIlIpHitH4s8IeSkOxkAQ5zQHPRPqyzpA3MYJQ9OWqGXwNC3UXZAj-76EhBoI"
    LaunchedEffect(Unit) {
        val body = mutableMapOf<String,Any>()
        body["v_name"] = vname
        body["v_code"] = vcode
        body["uname"] = username
        body["pass"] = pass
        body["token"] = token
        viewModel.fetchUsers(body,url)
    }
    when(result){
        is ApiResult.Loading -> {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        }
        is ApiResult.Error ->{
            val message = result.message
            Constants.CustomToast(message,1)
        }
        else -> {}


    }
    val user = userlist.firstOrNull()
    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = baseurl)
        Text(text = "Name: ${user?.name ?: ""}")
        Text(text = "Email: ${user?.email ?: ""}")
        Text(text = "State: ${user?.statename ?: ""}")
        Log.d("data", user?.email.toString())
        Log.d("data", modulelist.toString())
        LazyColumn(Modifier.fillMaxWidth()) {
            items(modulelist){ item ->
                Data(item)
            }
        }
    }
}

@Composable
fun Data(item: ModuleEntity){
    val imageurl = BuildConfig.IMAGE_URL
    val fullurl = imageurl + item.icon

            Column(
        Modifier.fillMaxWidth()
    ) {
        Text("name : ${item.name}")
        Text("id : ${item.id}")
        Text("icon url : $fullurl")
    }
}