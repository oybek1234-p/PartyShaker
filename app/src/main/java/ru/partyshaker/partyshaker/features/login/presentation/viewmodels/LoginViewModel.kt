package ru.partyshaker.partyshaker.features.login.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.partyshaker.partyshaker.feature_utils.entity.Result
import ru.partyshaker.partyshaker.features.login.domain.AuthorizationRepository
import ru.partyshaker.partyshaker.features.login.domain.LoginRequest
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthorizationRepository) :
    ViewModel() {

    fun getAuthToken(loginRequest: LoginRequest) {
        viewModelScope.launch {
            try {
                val tokenResult = repository.login(loginRequest)
                when (tokenResult) {
                    is Result.Success -> {
                        println("YOUR TOKEN IS: ${tokenResult.data}")
                    }
                    is Result.Error -> {
                        Log.e(TOKEN_EXCEPTION_TAG, tokenResult.exception)
                    }
                }
            } catch (e: Exception) {
                Log.e(LOGIN_EXCEPTION_TAG, e.toString())
            }
        }
    }

    companion object {
        private const val TOKEN_EXCEPTION_TAG = "Token Exception: "
        private const val LOGIN_EXCEPTION_TAG = "Login Exception: "
    }
}