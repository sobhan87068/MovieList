package com.example.movielist.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<A : ViewAction, S : ViewState>(initialState: S) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _actions = Channel<A>(Channel.UNLIMITED)
    val actions = _actions.receiveAsFlow()
    protected abstract fun handleAction(action: A)

    init {
        viewModelScope.launch {
            actions.collect {
                handleAction(it)
            }
        }
    }

    protected suspend fun updateState(state: S) {
        _state.emit(state)
    }

    fun submitAction(action: A) {
        viewModelScope.launch {
            _actions.send(action)
        }
    }
}