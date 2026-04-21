package com.mohit.proddevenvironmet.Screens.LoginScreen

import com.google.gson.annotations.SerializedName

class LoginResponseModel(
    @SerializedName("response"               ) var response              : Boolean?                         = null,
    @SerializedName("details"                ) var details               : Details?                         = Details(),
    @SerializedName("app_module"             ) var appModule             : ArrayList<AppModule>             = arrayListOf(),
    @SerializedName("sub_module"             ) var subModule             : ArrayList<String>                = arrayListOf(),
    @SerializedName("working_status_adata"   ) var workingStatusAdata    : ArrayList<WorkingStatusAdata>    = arrayListOf(),
    @SerializedName("product"                ) var product               : ArrayList<Product>               = arrayListOf(),
    @SerializedName("product_classification" ) var productClassification : ArrayList<ProductClassification> = arrayListOf(),
    @SerializedName("category"               ) var category              : ArrayList<Category>              = arrayListOf(),
    @SerializedName("payment_modes"          ) var paymentModes          : ArrayList<PaymentModes>          = arrayListOf(),
    @SerializedName("url_list"               ) var urlList               : ArrayList<UrlList>               = arrayListOf(),
    @SerializedName("shipping_arr_query"     ) var shippingArrQuery      : ArrayList<ShippingArrQuery>      = arrayListOf(),
    @SerializedName("message"                ) var message               : String?                          = null
) {
    data class Details (

        @SerializedName("dms_primary_id"    ) var dmsPrimaryId    : String? = null,
        @SerializedName("person_fullname"   ) var personFullname  : String? = null,
        @SerializedName("mobile"            ) var mobile          : String? = null,
        @SerializedName("email"             ) var email           : String? = null,
        @SerializedName("state_id"          ) var stateId         : String? = null,
        @SerializedName("person_role_id"    ) var personRoleId    : String? = null,
        @SerializedName("person_role_name"  ) var personRoleName  : String? = null,
        @SerializedName("user_id"           ) var userId          : String? = null,
        @SerializedName("dealer_id"         ) var dealerId        : String? = null,
        @SerializedName("dealer_code"       ) var dealerCode      : String? = null,
        @SerializedName("dealer_code_alias" ) var dealerCodeAlias : String? = null,
        @SerializedName("c_id"              ) var cId             : String? = null,
        @SerializedName("company_id"        ) var companyId       : String? = null,
        @SerializedName("company_image"     ) var companyImage    : String? = null,
        @SerializedName("state_name"        ) var stateName       : String? = null,
        @SerializedName("depo_name"         ) var depoName        : String? = null,
        @SerializedName("type_busy"         ) var typeBusy        : Int?    = null,
        @SerializedName("outstanding"       ) var outstanding     : Int?    = null,
        @SerializedName("user_type"         ) var userType        : String? = null

    )
    data class AppModule (

        @SerializedName("module_icon_image" ) var moduleIconImage : String? = null,
        @SerializedName("module_id"         ) var moduleId        : String? = null,
        @SerializedName("module_name"       ) var moduleName      : String? = null,
        @SerializedName("module_url"        ) var moduleUrl       : String? = null

    )
    data class WorkingStatusAdata (

        @SerializedName("id"             ) var id            : String? = null,
        @SerializedName("company_id"     ) var companyId     : String? = null,
        @SerializedName("name"           ) var name          : String? = null,
        @SerializedName("parent_id"      ) var parentId      : String? = null,
        @SerializedName("sequence"       ) var sequence      : String? = null,
        @SerializedName("color_status"   ) var colorStatus   : String? = null,
        @SerializedName("status"         ) var status        : String? = null,
        @SerializedName("language_json"  ) var languageJson  : String? = null,
        @SerializedName("formula_status" ) var formulaStatus : String? = null,
        @SerializedName("skip_distance"  ) var skipDistance  : String? = null

    )
    data class Product (

        @SerializedName("id"                      ) var id                    : String?           = null,
        @SerializedName("name"                    ) var name                  : String?           = null,
        @SerializedName("catalog_product_details" ) var catalogProductDetails : ArrayList<String> = arrayListOf()

    )
    data class ProductClassification (

        @SerializedName("id"   ) var id   : String? = null,
        @SerializedName("name" ) var name : String? = null

    )
    data class Category (

        @SerializedName("id"                  ) var id                 : String? = null,
        @SerializedName("classification_id"   ) var classificationId   : String? = null,
        @SerializedName("classification_name" ) var classificationName : String? = null,
        @SerializedName("name"                ) var name               : String? = null

    )
    data class PaymentModes (

        @SerializedName("id"         ) var id        : String? = null,
        @SerializedName("company_id" ) var companyId : String? = null,
        @SerializedName("mode"       ) var mode      : String? = null,
        @SerializedName("status"     ) var status    : String? = null,
        @SerializedName("created_at" ) var createdAt : String? = null,
        @SerializedName("updated_at" ) var updatedAt : String? = null

    )
    data class UrlList (

        @SerializedName("code"     ) var code    : String? = null,
        @SerializedName("url_list" ) var urlList : String? = null

    )
    data class ShippingArrQuery (

        @SerializedName("id"               ) var id             : String? = null,
        @SerializedName("address1"         ) var address1       : String? = null,
        @SerializedName("address2"         ) var address2       : String? = null,
        @SerializedName("address3"         ) var address3       : String? = null,
        @SerializedName("dealer_id"        ) var dealerId       : String? = null,
        @SerializedName("company_id"       ) var companyId      : String? = null,
        @SerializedName("server_date_time" ) var serverDateTime : String? = null

    )
}