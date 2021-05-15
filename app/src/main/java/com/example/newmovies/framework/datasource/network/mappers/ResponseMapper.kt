package com.example.newmovies.framework.datasource.network.mappers

import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.business.domain.util.EntityMapper
import com.example.newmovies.framework.datasource.cache.model.CachedMovie
import com.example.newmovies.framework.datasource.network.model.Response

class ResponseMapper: EntityMapper<MovieResponse, Response>{

    override fun mapFromEntity(entity: MovieResponse): Response {
        return Response(
            imdbID = entity.imdbID,
            poster = entity.poster,
            title = entity.title,
            type = entity.type,
            year = entity.year
        )
    }

    override fun mapToEntity(domainModel: Response): MovieResponse {
        return MovieResponse(
            imdbID = domainModel.imdbID,
            poster = domainModel.poster,
            title = domainModel.title,
            type = domainModel.type,
            year = domainModel.year
        )
    }

    fun entityListToResponseList(entities: List<MovieResponse>): List<Response>{
        val list: ArrayList<Response> = ArrayList()
        for(entity in entities){
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun responseListToEntityList(response: List<Response>): List<MovieResponse>{
        val list: ArrayList<MovieResponse> = ArrayList()
        for(movie in response){
            list.add(mapToEntity(movie))
        }
        return list
    }
}