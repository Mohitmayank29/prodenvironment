package com.mohit.proddevenvironmet.Screens.submit

import com.google.gson.annotations.SerializedName

class SubmitModel(
    @SerializedName("response"               ) var response              : Boolean?           = null,
    @SerializedName("message"                ) var message               : String?            = null,
    @SerializedName("errormessage"           ) var errormessage          : String?            = null

)