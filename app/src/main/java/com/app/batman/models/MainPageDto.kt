package com.app.batman.models

import com.fasterxml.jackson.annotation.JsonProperty

data class MainPageDto(
    @JsonProperty("Search")
    var films: ArrayList<Film>? = ArrayList(),

    @JsonProperty("totalResults")
    var totalResults: String?,

    @JsonProperty("Response")
    var response: String?
)