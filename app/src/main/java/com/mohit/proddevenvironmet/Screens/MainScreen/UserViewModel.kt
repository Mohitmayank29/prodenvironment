package com.mohit.proddevenvironmet.Screens.MainScreen
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohit.proddevenvironmet.ApiResponseHandler.ApiResult
import com.mohit.proddevenvironmet.CommonConstants.Constants
import com.mohit.proddevenvironmet.RoomDataBase.Dao.ModuleDao
import com.mohit.proddevenvironmet.RoomDataBase.Dao.UserDao
import com.mohit.proddevenvironmet.RoomDataBase.Table.ModuleEntity
import com.mohit.proddevenvironmet.RoomDataBase.Table.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userDao: UserDao,
    private val moduleDao : ModuleDao

): ViewModel() {
    val users : LiveData<List<UserEntity>> = userDao.getAllUsers()
        private  val _loading = MutableLiveData<Boolean>()
        val loading : LiveData<Boolean> = _loading
    val modules : LiveData<List<ModuleEntity>> = moduleDao.getallmodule()
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun fetchUsers(body: Map<String, Any>, context: Context){
        viewModelScope.launch {
            _loading.value  = true
            when (val result = repository.fetchUsers(body = body)){
                is ApiResult.Success ->{
                    val body =
                      result.data
                    Log.d("TAG", "fetchUsers: $body")
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
//                    Constants.CustomToast("Success",1)
                    _loading.value = false
                }
                is ApiResult.Error ->{
                    _loading.value = false
                }
            }
            _loading.value = false
        }
    }
}