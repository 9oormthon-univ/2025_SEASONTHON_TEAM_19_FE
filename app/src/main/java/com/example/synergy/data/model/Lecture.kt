package com.example.synergy.data.model

import com.google.gson.annotations.SerializedName

data class PageResponse(
    val content: List<Lecture> = emptyList(),
    val pageNumber: Int = 0,
    val pageSize: Int = 0,
    val totalElements: Int = 0,
    val totalPages: Int = 0,
    val last: Boolean = true,
)

data class Lecture(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("category") val category: String,
    @SerializedName("date") val date: String,
    @SerializedName("location") val location: String,
    @SerializedName("capacity") val capacity: Int,
    @SerializedName("reserved") val reserved: Int,
    @SerializedName("remain") val remain: Int,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String?,
    @SerializedName("content") val content: String?
)
