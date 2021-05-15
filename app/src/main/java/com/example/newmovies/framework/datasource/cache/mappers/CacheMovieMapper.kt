package com.example.newmovies.framework.datasource.cache.mappers

import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.business.domain.util.EntityMapper
import com.example.newmovies.framework.datasource.cache.model.CachedMovie

class CacheMovieMapper: EntityMapper<CachedMovie, MovieResponse>{

    override fun mapFromEntity(entity: CachedMovie): MovieResponse {
        return MovieResponse(
            imdbID = entity.imdbID,
            poster = entity.poster,
            title = entity.title,
            type = entity.type,
            year = entity.year
        )
    }

    override fun mapToEntity(domainModel: MovieResponse): CachedMovie {
        return CachedMovie(
            imdbID = domainModel.imdbID,
            poster = domainModel.poster,
            title = domainModel.title,
            type = domainModel.type,
            year = domainModel.year
        )
    }

    fun entityListToResponseList(entities: List<CachedMovie>): List<MovieResponse>{
        val list: ArrayList<MovieResponse> = ArrayList()
        for(entity in entities){
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun responseListToEntityList(response: List<MovieResponse>): List<CachedMovie>{
        val list: ArrayList<CachedMovie> = ArrayList()
        for(movie in response){
            list.add(mapToEntity(movie))
        }
        return list
    }


}