package com.project.mobile_university.domain.repository

import com.project.mobile_university.data.gson.BaseServerResponse
import com.project.mobile_university.data.gson.Meta
import com.project.mobile_university.data.gson.Student
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.data.presentation.ServerConfig
import com.project.mobile_university.domain.shared.LoginRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import io.reactivex.Observable

class LoginRepositoryMock(private val sharedPreferenceService: SharedPreferenceService) : LoginRepository {
    override fun login(login: String, password: String): Observable<BaseServerResponse<User>> {
        if (login != "test_student" && password != "test_student") {
            return Observable.error(Throwable("Authorization error"))
        }

        val fakeResponse = BaseServerResponse(
            Meta(
                limit = 0,
                currentPage = 0,
                nextPage = 0,
                previousPage = 0,
                size = 0
            ), listOf<User>(
                Student(
                    email = "",
                    firstName = "test_student",
                    groupId = 1,
                    isStudent = true,
                    lastName = "test_student",
                    subgroupId = 1
                )
            )
        )

        return Observable.fromCallable { sharedPreferenceService.saveUserInfo(fakeResponse.objectList!![0]) }
            .map { fakeResponse }
    }

    override fun saveServerConfig(serverConfig: ServerConfig): Observable<ServerConfig> {
        return Observable.just(serverConfig)
    }

    override fun getServerConfig(): Observable<ServerConfig> {
        return Observable.just(ServerConfig())
    }

    override fun saveLoginPass(login: String, pass: String): Observable<Unit> {
        return Observable.just(Unit)
    }

    override fun setServiceUrl(serviceUrl: String): Observable<Unit> {
        return Observable.just(Unit)
    }
}