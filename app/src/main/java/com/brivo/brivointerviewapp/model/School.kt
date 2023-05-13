package com.brivo.brivointerviewapp.model

import com.google.gson.annotations.SerializedName

data class School(
    @SerializedName("dbn") val code: String?,
    @SerializedName("school_name") val schoolName: String?,
    @SerializedName("overview_paragraph") val overview: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("primary_address_line_1") val address: String?,
    @SerializedName("zip") val zip: String?,
    @SerializedName("state_code") val state: String?,
    @SerializedName("requirement1_1") val requirement1_1: String?,
    @SerializedName("requirement2_1") val requirement2_1: String?,
    @SerializedName("school_email") val email: String?,
    @SerializedName("phone_number") val phone_number: String?,
    @SerializedName("website") val website: String?,
    @SerializedName("extracurricular_activities") val extracurricularActivities: String?,
    @SerializedName("neighborhood") val neighborhood: String?,
    @SerializedName("borough") val borough: String?,
    val dict: Map<String, String>?,
)