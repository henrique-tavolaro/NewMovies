package com.example.newmovies.framework.datasource.cache.mappers

import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.business.domain.util.EntityMapper
import com.example.newmovies.framework.datasource.cache.model.CachedMovieDetail
import com.example.newmovies.framework.datasource.cache.model.SavedMovie

class SavedMovieMapper: EntityMapper<SavedMovie, MovieDetailResponse> {
    override fun mapFromEntity(entity: SavedMovie): MovieDetailResponse {
        return MovieDetailResponse(
            actors = entity.actors,
            awards = "",
            boxOffice = "",
            country = "",
            dVD = "",
            director = entity.director,
            genre = entity.genre,
            imdbID = entity.imdbID,
            imdbRating = entity.imdbRating,
            imdbVotes = "",
            language = "",
            metascore = "",
            plot = entity.plot,
            poster = entity.poster,
            production = "",
            rated = "",
            released = "",
            response = "",
            runtime = "",
            title = entity.title,
            type = "",
            website = "",
            writer = "",
            year = entity.year
        )
    }

    override fun mapToEntity(domainModel: MovieDetailResponse): SavedMovie {
        return SavedMovie(
            actors = domainModel.actors,
            director = domainModel.director,
            genre = domainModel.genre,
            imdbID = domainModel.imdbID,
            imdbRating = domainModel.imdbRating,
            plot = domainModel.plot,
            poster = domainModel.poster,
            title = domainModel.title,
            year = domainModel.year,
            myRating = 0f,
            onToWatchList = false,
            watched = false
        )
    }

    fun entityListToResponseList(entities: List<SavedMovie>): List<MovieDetailResponse>{
        val list: ArrayList<MovieDetailResponse> = ArrayList()
        for(entity in entities){
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun responseListToEntityList(response: List<MovieDetailResponse>): List<SavedMovie>{
        val list: ArrayList<SavedMovie> = ArrayList()
        for(movie in response){
            list.add(mapToEntity(movie))
        }
        return list
    }
}