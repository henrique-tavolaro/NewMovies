package com.codingwithmitch.cleannotes.business.domain.state


data class DataState<T>(
    var stateMessage: StateMessage? = null,
    var data: T? = null
) {

    companion object {

        fun <T> error(
            stateResponse: StateResponse
        ): DataState<T> {
            return DataState(
                stateMessage = StateMessage(
                    stateResponse
                ),
                data = null
            )
        }

        fun <T> data(
            stateResponse: StateResponse?,
            data: T? = null
        ): DataState<T> {
            return DataState(
                stateMessage = stateResponse?.let {
                    StateMessage(
                        it
                    )
                },
                data = data
            )
        }
    }
}