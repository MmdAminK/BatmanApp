package com.app.batman.models

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty
import com.flaviofaria.kenburnsview.KenBurnsView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception

@Entity(tableName = "Film")
data class Film(
    @ColumnInfo(name = "title")
    @JsonProperty("Title")
    val title: String? = "",

    @ColumnInfo(name = "year")
    @JsonProperty("Year")
    val year: String? = "",

    @ColumnInfo(name = "rated")
    @JsonProperty("Rated")
    val rated: String? = "",

    @ColumnInfo(name = "released")
    @JsonProperty("Released")
    val released: String? = "",

    @ColumnInfo(name = "runtime")
    @JsonProperty("Runtime")
    val runtime: String? = "",

    @ColumnInfo(name = "genre")
    @JsonProperty("Genre")
    val genre: String? = "",

    @ColumnInfo(name = "director")
    @JsonProperty("Director")
    val director: String? = "",

    @ColumnInfo(name = "writer")
    @JsonProperty("Writer")
    val writer: String? = "",

    @ColumnInfo(name = "actors")
    @JsonProperty("Actors")
    val actors: String? = "",

    @ColumnInfo(name = "plot")
    @JsonProperty("Plot")
    val plot: String? = "",

    @ColumnInfo(name = "language")
    @JsonProperty("Language")
    val language: String? = "",

    @ColumnInfo(name = "country")
    @JsonProperty("Country")
    val country: String? = "",

    @ColumnInfo(name = "awards")
    @JsonProperty("Awards")
    val awards: String? = "",

    @ColumnInfo(name = "poster")
    @JsonProperty("Poster")
    val poster: String? = "",

    @ColumnInfo(name = "ratings")
    @JsonProperty("Ratings")
    val ratings: ArrayList<Rate>? = ArrayList(),

    @ColumnInfo(name = "metaScore")
    @JsonProperty("Metascore")
    val metaScore: String? = "",

    @ColumnInfo(name = "imdbRating")
    @JsonProperty("imdbRating")
    val imdbRating: String? = "",

    @ColumnInfo(name = "imdbVotes")
    @JsonProperty("imdbVotes")
    val imdbVotes: String? = "",

    @PrimaryKey
    @ColumnInfo(name = "imdbID")
    @JsonProperty("imdbID")
    val imdbId: String = "",

    @ColumnInfo(name = "type")
    @JsonProperty("Type")
    val type: String? = "",

    @ColumnInfo(name = "dvd")
    @JsonProperty("DVD")
    val dvd: String? = "",

    @ColumnInfo(name = "boxOffice")
    @JsonProperty("BoxOffice")
    val boxOffice: String? = "",

    @ColumnInfo(name = "production")
    @JsonProperty("Production")
    val production: String? = "",

    @ColumnInfo(name = "Website")
    @JsonProperty("Website")
    val website: String? = "",

    @ColumnInfo(name = "totalSeason")
    @JsonProperty("totalSeasons")
    val totalSeason: String? = "",

    @ColumnInfo(name = "response")
    @JsonProperty("Response")
    val response: String? = ""
) {
    companion object {
        @BindingAdapter("loadUrl")
        @JvmStatic
        fun loadImage(imageView: KenBurnsView, url: String) {
            Picasso
                .get()
                .load(url)
                .into(imageView)
        }

        @BindingAdapter("loadUrlImageView")
        @JvmStatic
        fun loadImage2(imageView: ImageView, url: String?) {
                try {
                    Picasso
                        .get()
                        .load(url)
                        .into(imageView)
                }
                catch (exception: Exception) {
                }
        }

        @SuppressLint("SetTextI18n")
        @BindingAdapter("totalSeason")
        @JvmStatic
        fun loadText(textView: TextView, str: String?) {
            if (!str.isNullOrEmpty()) {
                textView.text = "TotalSeason : $str"
                textView.visibility = View.VISIBLE
            }
            else {
                textView.visibility = View.GONE
            }
        }

    }
}
