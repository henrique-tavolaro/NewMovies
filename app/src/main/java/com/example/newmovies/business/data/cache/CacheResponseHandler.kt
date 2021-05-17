package com.example.newmovies.business.data.cache

//import com.example.newmovies.business.data.cache.CacheErrors.CACHE_DATA_NULL
//import com.example.newmovies.business.data.network.DataState
//import com.example.newmovies.business.data.network.MessageType
//import com.example.newmovies.business.data.network.StateResponse
//import com.example.newmovies.business.data.network.UIComponentType
//import com.example.newmovies.business.domain.state.DataState
//
//
//abstract class CacheResponseHandler <MovieViewState, Data>(
//    private val response: CacheResult<Data?>
//){
//    suspend fun getResult(): DataState<MovieViewState>? {
//
//        return when(response){
//
//            is CacheResult.GenericError -> {
//                DataState.error(
//                    response = StateResponse(
//                        message = response.errorMessage,
//                        uiComponentType = UIComponentType.Dialog(),
//                        messageType = MessageType.Error()
//                    )
//                )
//            }
//
//            is CacheResult.Success -> {
//                if(response.value == null){
//                    DataState.error(
//                        response = StateResponse(
//                            message = CACHE_DATA_NULL,
//                            uiComponentType = UIComponentType.Dialog(),
//                            messageType = MessageType.Error()
//                        )
//                    )
//                }
//                else{
//                    handleSuccess(resultObj = response.value)
//                }
//            }
//
//        }
//    }
//
//    abstract suspend fun handleSuccess(resultObj: Data): DataState<MovieViewState>?
//
//}