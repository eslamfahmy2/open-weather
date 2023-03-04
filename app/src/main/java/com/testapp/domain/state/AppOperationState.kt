package com.testapp.domain.state

import com.testapp.R
import com.testapp.domain.models.UniversalError
import com.testapp.utils.ApplicationContextSingleton

data class AppOperationState(
    val error: UniversalError? = null,
    val status: DataStatus = DataStatus.CREATED
) {
    enum class DataStatus {
        CREATED, SUCCESS, ERROR, LOADING, COMPLETE
    }
}

fun unknownError(): AppOperationState {
    return AppOperationState(
        UniversalError(
            ApplicationContextSingleton.getString(
                R.string.unknown_error
            )
        )
    )
}

fun universalError(error: UniversalError): AppOperationState {
    return AppOperationState(error, AppOperationState.DataStatus.ERROR)
}