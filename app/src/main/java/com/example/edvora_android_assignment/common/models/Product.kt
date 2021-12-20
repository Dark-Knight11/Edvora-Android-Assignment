package com.example.edvora_android_assignment.common.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product")
data class Product (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("product_name" ) var productName : String,
    @SerializedName("brand_name"   ) var brandName   : String?  = null,
    @SerializedName("price"        ) var price       : Int?     = null,
    @SerializedName("address"      ) @Embedded var address: Address?,
    @SerializedName("discription"  ) var description : String?  = null,
    @SerializedName("date"         ) var date        : String?  = null,
    @SerializedName("time"         ) var time        : String?  = null,
    @SerializedName("image"        ) var image       : String?  = null
)


data class Address (
    @SerializedName("state" ) var state : String? = null,
    @SerializedName("city"  ) var city  : String? = null
)


