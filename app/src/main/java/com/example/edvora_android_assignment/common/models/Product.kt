package com.example.edvora_android_assignment.common.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "product")
@JsonClass(generateAdapter = true)
data class Product (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @field:Json(name = "product_name" ) var productName : String,
    @field:Json(name = "brand_name"   ) var brandName   : String?  = null,
    @field:Json(name = "price"        ) var price       : Int?     = null,
    @field:Json(name = "address"      ) @Embedded var address: Address?,
    @field:Json(name = "discription"  ) var description : String?  = null,
    @field:Json(name = "date"         ) var date        : String?  = null,
    @field:Json(name = "time"         ) var time        : String?  = null,
    @field:Json(name = "image"        ) var image       : String?  = null
)

@JsonClass(generateAdapter = true)
data class Address (
    @field:Json(name = "state" ) var state : String? = null,
    @field:Json(name = "city"  ) var city  : String? = null
)


