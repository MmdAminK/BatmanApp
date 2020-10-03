package com.app.batman.models

import androidx.room.ColumnInfo
import com.fasterxml.jackson.annotation.JsonProperty

data class Rate(
    @JsonProperty("Source")
    val source: String,

    @JsonProperty("Value")
    val value: String
)
