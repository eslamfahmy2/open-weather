package com.testapp.domain.repository



import com.testapp.data.networking.AppServices
import com.testapp.domain.mapToDomainModel
import com.testapp.domain.models.UniversalError
import com.testapp.domain.models.Weather
import com.testapp.domain.state.StateData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepository @Inject constructor(
    private val appServices: AppServices
) {
    fun getWeatherByQuery(location: String): Flow<StateData<Weather>> {
        return flow {
            try {
                val out = appServices.getWeatherByQuery(location).mapToDomainModel()
                emit(StateData<Weather>().success(out))
            } catch (e: Exception) {
                emit(StateData<Weather>().error(UniversalError(e.message)))
            }
        }
    }
}