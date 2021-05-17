package com.example.newmovies.framework.datasource.cache.mappers

import com.example.newmovies.business.domain.model.MovieDetailResponse
import com.example.newmovies.business.domain.util.EntityMapper
import com.example.newmovies.framework.datasource.cache.model.CachedMovieDetail

class CachedMovieDetailsMapper: EntityMapper<CachedMovieDetail, MovieDetailResponse> {

    override fun mapFromEntity(entity: CachedMovieDetail): MovieDetailResponse {
        return MovieDetailResponse(
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

    override fun mapToEntity(domainModel: MovieDetailResponse): CachedMovieDetail {
        return CachedMovieDetail(
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

    fun entityListToResponseList(entity: CachedMovieDetail): MovieDetailResponse{
        return mapFromEntity(entity)
    }

    fun responseListToEntityList(response: MovieDetailResponse): CachedMovieDetail{

        return mapToEntity(response)
    }
}