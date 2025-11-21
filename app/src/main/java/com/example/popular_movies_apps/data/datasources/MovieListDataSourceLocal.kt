package com.example.popular_movies_apps.data.datasources

import com.example.popular_movies_apps.data.local.MovieEntity.MovieDetailRealmObject
import com.example.popular_movies_apps.data.local.MovieEntity.MovieLocalDataSource
import com.example.popular_movies_apps.data.local.MovieEntity.MovieRealmObject
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.forEach

@Singleton
class MovieListDataSourceLocal @Inject constructor() : MovieLocalDataSource {

    private val realm: Realm by lazy {
        val config = RealmConfiguration.Builder(
            schema = setOf(MovieRealmObject::class, MovieDetailRealmObject::class)
        ).name("movies.realm").build()
        Realm.open(config)
    }
    override fun getAllMovies(): List<MovieRealmObject> {
        return realm.query<MovieRealmObject>().find().toList()
    }

    override fun getMovieById(id: Int): MovieRealmObject? {
        return realm.query<MovieRealmObject>("id == $0", id).first().find()
    }

    override fun saveMovies(movies: List<MovieRealmObject>) {
        realm.writeBlocking {
            delete(query<MovieRealmObject>())
            movies.forEach { movie ->
                copyToRealm(movie)
            }
        }
    }

    override fun getMovieDetailById(id: Int): MovieDetailRealmObject? =
        realm.query<MovieDetailRealmObject>("id == $0", id).first().find()

    override fun saveMovieDetail(detail: MovieDetailRealmObject) {
        realm.writeBlocking {
            delete(query<MovieDetailRealmObject>("id == $0", detail.id))
            copyToRealm(detail)
        }
    }
}