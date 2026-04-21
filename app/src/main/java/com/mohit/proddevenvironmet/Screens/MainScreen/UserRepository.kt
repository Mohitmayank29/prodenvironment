package com.mohit.proddevenvironmet.Screens.MainScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.mohit.proddevenvironmet.ApiCalls.ApiInterface
import com.mohit.proddevenvironmet.ApiResponseHandler.ApiResult
import com.mohit.proddevenvironmet.CommonConstants.Constants
import com.mohit.proddevenvironmet.RoomDataBase.Dao.ModuleDao
import com.mohit.proddevenvironmet.RoomDataBase.Dao.UserDao
import com.mohit.proddevenvironmet.RoomDataBase.Table.ModuleEntity
import com.mohit.proddevenvironmet.RoomDataBase.Table.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor (
    private val api: ApiInterface,
    private val userDao: UserDao,
    private val moduleDao : ModuleDao
) {


    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    suspend fun fetchUsers(body: Map<String,Any>): ApiResult<Unit> {
        return try {
             when (val result = Constants.safeapicall {
                api.getLogin(body)
            }) {
                is ApiResult.Success -> {
                    val json = result.data
                    Log.d("RAW_JSON", Gson().toJson(body))
                    Log.d("TAG", "fetchUsers1: $json")
                    val details = json?.getAsJsonObject("details")
                    val module = json?.getAsJsonArray("app_module")

//                    Log.d("API_FULL", json.toString())
//                    Log.d("API_DETAILS", json?.details.toString())
//                    Log.d("API_DETAILS", json?.appModule.toString())
//                    Log.d("CHECK_1", json?.details.toString())
//                    Log.d("CHECK_2", json?.details.toString())
                    val userList = listOf(
                        UserEntity(
                            id = details?.get("user_id")?.asString.toString(),
                            name = details?.get("person_fullname")?.asString.toString(),
                            email = details?.get("email")?.asString.toString(),
                            statename = details?.get("state_name")?.asString.toString()
                        )
                    )
                    val modulelist = module?.map { element ->
                        val obj = element.asJsonObject

                        ModuleEntity(
                            id = obj.get("module_id")?.asString.toString(),
                            icon = obj.get("module_icon_image")?.asString.toString(),
                            name = obj.get("module_name")?.asString.toString(),
                            moduleurl = obj.get("module_url")?.asString.toString(),
                        )
                    }  ?: emptyList()
                    Log.d("STEP_1", "Before delete")

                    userDao.deleteAllUsers()
                    Log.d("STEP_2", "After delete")

                    userDao.insertUser(userList)
                    Log.d("STEP_3", "After insert")

                    val savedData = userDao.getAllUsersOnce()
                    Log.d("DB_CHECK", savedData.toString())

                    moduleDao.deleteallmodule()
                    moduleDao.insertmodule(modulelist)
                    val savedModules = moduleDao.getallmoduleonce()
                    Log.d("MODULE_DB", savedModules.toString())
                    ApiResult.Success(Unit)

                }
                is ApiResult.Error ->{
                    ApiResult.Error(result.message)
                }
            }

       } catch (e: Exception) {
            ApiResult.Error(e.message.toString())
       }
    }
}