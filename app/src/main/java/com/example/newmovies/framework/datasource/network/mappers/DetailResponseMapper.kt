package com.example.newmovies.framework.datasource.network.mappers

import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.business.domain.model.MovieResponse
import com.example.newmovies.business.domain.util.EntityMapper
import com.example.newmovies.framework.datasource.network.model.DetailResponse
import com.example.newmovies.framework.datasource.network.model.Response

class DetailResponseMapper: EntityMapper<MovieDetailResponse ,DetailResponse > {
    override fun mapFromEntity(entity: MovieDetailResponse): DetailResponse {
        return DetailResponse(
            actors = entity.actors,
            awards = entity.awards,
            boxOffice = entity.boxOffice,
            country = entity.country,
            dVD = entity.dVD,
            director = entity.director,
            genre = entity.genre,
            imdbID = entity.imdbID,
            imdbRating = entity.imdbRating,
            imdbVotes = entity.imdbVotes,
            language = entity.language,
            metascore = entity.metascore,
            plot = entity.plot,
            poster = entity.poster,
            production = entity.production,
            rated = entity.rated,
            ratings = listOf(),
            released = entity.released,
            response = entity.response,
            runtime = entity.runtime,
            title = entity.title,
            type = entity.type,
            website = entity.website,
            writer = entity.writer,
            year = entity.year
        )
    }

    override fun mapToEntity(domainModel: DetailResponse): MovieDetailResponse {
        return MovieDetailResponse(
            actors = domainModel.actors,
            awards = domainModel.awards,
            boxOffice = domainModel.boxOffice,
            country = domainModel.country,
            dVD = domainModel.dVD,
            director = domainModel.director,
            genre = domainModel.genre,
            imdbID = domainModel.imdbID,
            imdbRating = domainModel.imdbRating,
            imdbVotes = domainModel.imdbVotes,
            language = domainModel.language,
            metascore = domainModel.metascore,
            plot = domainModel.plot,
            poster = domainModel.poster,
            production = domainModel.production,
            rated = domainModel.rated,
            released = domainModel.released,
            response = domainModel.response,
            runtime = domainModel.runtime,
            title = domainModel.title,
            type = domainModel.type,
            website = domainModel.website,
            writer = domainModel.writer,
            year = domainModel.year
        )
    }

    fun entityListToResponseList(entities: List<MovieDetailResponse>): List<DetailResponse>{
        val list: ArrayList<DetailResponse> = ArrayList()
        for(entity in entities){
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun responseListToEntityList(response: List<DetailResponse>): List<MovieDetailResponse>{
        val list: ArrayList<MovieDetailResponse> = ArrayList()
        for(movie in response){
            list.add(mapToEntity(movie))
        }
        return list
    }

}