package com.project.mobile_university.data.gson

import com.google.gson.annotations.SerializedName

data class BaseServerResponse<T>(@SerializedName("meta") val meta: Meta,
                                 @SerializedName("objects") val objectList: List<T>?)

data class Meta(@SerializedName("limit") val limit: Int,
                @SerializedName("next") val nextPage: String?,
                @SerializedName("offset") val currentPage: Int,
                @SerializedName("previous") val previousPage: String?,
                @SerializedName("total_count") val size: Int)