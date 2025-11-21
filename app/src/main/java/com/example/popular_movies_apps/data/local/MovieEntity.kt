package com.example.popular_movies_apps.data.local.MovieEntity

import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class MovieRealmObject : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var title: String = ""
    var overview: String = ""
    var posterPath: String? = null
    var releaseDate: String? = null
    var voteAverage: Double = 0.0
}

class MovieDetailRealmObject : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var title: String = ""
    var overview: String = ""
    var posterPath: String? = null
    var backdropPath: String? = null
    var releaseDate: String? = null
    var voteAverage: Double = 0.0
    var runtime: Int? = null
    var tagline: String? = null

}


