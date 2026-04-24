package com.mohit.proddevenvironmet.Screens.MainScreen
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohit.proddevenvironmet.ApiResponseHandler.ApiResult
import com.mohit.proddevenvironmet.BaseViewModel
import com.mohit.proddevenvironmet.CommonConstants.Constants
import com.mohit.proddevenvironmet.NoInternet.NetworkObserver
import com.mohit.proddevenvironmet.RoomDataBase.Dao.ModuleDao
import com.mohit.proddevenvironmet.RoomDataBase.Dao.UserDao
import com.mohit.proddevenvironmet.RoomDataBase.Table.ModuleEntity
import com.mohit.proddevenvironmet.RoomDataBase.Table.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userDao: UserDao,
    private val moduleDao : ModuleDao,
    private val context : Application

): BaseViewModel(context) {
    val users : LiveData<List<UserEntity>> = userDao.getAllUsers()
    val modules : LiveData<List<ModuleEntity>> = moduleDao.getallmodule()
    private val _state = MutableStateFlow<ApiResult<Unit>?>(null)
    val  state : StateFlow<ApiResult<Unit>?> = _state
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun fetchUsers(body: Map<String, Any>, url:String){
        viewModelScope.launch {
                _state.value = ApiResult.Loading()
                val result = repository.fetchUsers(body = body, url = url)
                _state.value = result

//                is ApiResult.Success ->{
//                    val body =
//                      result.data
//                    Log.d("TAG", "fetchUsers: $body")
//                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
////                    Constants.CustomToast("Success",1)
//
//                }
//                is ApiResult.Error ->{
//                    _loading.value = false
//                }

        }
    }
}