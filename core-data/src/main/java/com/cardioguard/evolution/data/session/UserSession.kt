package com.cardioguard.evolution.data.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserSession {
    private val _currentUserName = MutableStateFlow<String?>(null)
    val currentUserName: StateFlow<String?> = _currentUserName

    fun setUser(name: String?) {
        _currentUserName.value = name
    }

    fun clear() {
        _currentUserName.value = null
    }
}
