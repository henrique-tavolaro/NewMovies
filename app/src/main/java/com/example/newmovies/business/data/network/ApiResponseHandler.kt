package com.example.newmovies.business.data.network

//
//
//import com.example.newmovies.business.data.network.NetworkErrors.NETWORK_DATA_NULL
//import com.example.newmovies.business.data.network.NetworkErrors.NETWORK_ERROR
//import com.example.newmovies.business.domain.state.*
//import com.example.newmovies.framework.datasource.network.model.Response
//import com.example.newmovies.framework.presentation.state.MovieViewState
//
//
//abstract class ApiResponseHandler <ViewState, Data>(
//    private val response: ApiResult<Data?>,
//    private val stateEvent: StateEvent
//){
//
//    suspend fun getResult(): DataState<MovieViewState>? {
//
//        return when(response){
//
//            is ApiResult.GenericError -> {
//                DataState.error(
//                    response = StateResponse(
//                        message = "${stateEvent?.errorInfo()}\n\nReason: ${response.errorMessage.toString()}",
//                        uiComponentType = UIComponentType.Dialog(),
//                        messageType = MessageType.Error()
//                    ),
//                    stateEvent = stateEvent
//                )
//            }
//
//            is ApiResult.NetworkError -> {
//                DataState.error(
//                    response = StateResponse(
//                        message = "${stateEvent?.errorInfo()}\n\nReason: $NETWORK_ERROR",
//                        uiComponentType = UIComponentType.Dialog(),
//                        messageType = MessageType.Error()
//                    ),
//                    stateEvent = stateEvent
//                )
//            }
//
//            is ApiResult.Success -> {
//                if(response.value == null){
//                    DataState.error(
//                        response = StateResponse(
//                            message = "${stateEvent?.errorInfo()}\n\nReason: ${NETWORK_DATA_NULL}.",
//                            uiComponentType = UIComponentType.Dialog(),
//                            messageType = MessageType.Error()
//                        ),
//                        stateEvent = stateEvent
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