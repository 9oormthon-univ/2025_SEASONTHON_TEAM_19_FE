package com.example.synergy.data.model

import com.google.gson.annotations.SerializedName

data class MentorPageResponse(
    val content: List<Mentor>,
    val last: Boolean,
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
    val first: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
)

data class Mentor(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("mentor") val isMentor: Boolean,
    @SerializedName("categories") val categories: List<Category>,
    @SerializedName("introduction") val introduction: String?,
)

data class Category(
    @SerializedName("code") val code: String,
    @SerializedName("name") val category: String,
)