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

data class MentorDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val name: String,
    @SerializedName("categories") val categories: List<Category>,
    @SerializedName("education") val education: String?,
    @SerializedName("career") val career: String?,
    @SerializedName("introduction") val introduction: String?
)

data class MentorApplicationRequest(
    @SerializedName("applicantName") val applicantName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("scheduledAt") val scheduledAt: String, // "yyyy-MM-dd HH:mm"
    @SerializedName("content") val content: String,
)

data class MentorApplicationResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("mentorId") val mentorId: Int,
    @SerializedName("mentorUsername") val mentorUsername: String?,
    @SerializedName("applicantName") val applicantName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("scheduledAt") val scheduledAt: String,
    @SerializedName("content") val content: String,
    @SerializedName("createdAt") val createdAt: String
)

data class Category(
    @SerializedName("code") val code: String,
    @SerializedName("name") val category: String,
)