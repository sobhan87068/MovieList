package com.example.movielist.ui.home

import androidx.lifecycle.viewModelScope
import com.example.movielist.base.BaseViewModel
import com.example.movielist.domain.GetUpcomingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUpcomingUseCase: GetUpcomingUseCase
) : BaseViewModel<HomeAction, HomeState>(HomeState.Loading) {
    override fun handleAction(action: HomeAction) {
        viewModelScope.launch {
            getUpcomingUseCase(1).collect {
                updateState(HomeState.Success(it))
            }
        }
    }
}