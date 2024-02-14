package com.example.movielist.ui.home

import androidx.lifecycle.viewModelScope
import com.example.movielist.base.BaseViewModel
import com.example.movielist.data.result.ApiResult
import com.example.movielist.domain.GetUpcomingUseCase
import com.example.movielist.domain.RetrieveMoviesPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUpcomingUseCase: GetUpcomingUseCase,
    private val retrieveMoviesPageUseCase: RetrieveMoviesPageUseCase
) : BaseViewModel<HomeAction, HomeState>(HomeState.Idle) {

    private var nextPage = 1
    private var totalPages = 1
    private var job: Job? = null

    override fun handleAction(action: HomeAction) {
        if (totalPages < nextPage) return
        if (state.value is HomeState.Loading) return

        job?.cancel()
        job = viewModelScope.launch {
            getUpcomingUseCase().combine(
                retrieveMoviesPageUseCase(nextPage).onEach {
                    if (it is ApiResult.ApiSuccess) nextPage++
                }
            ) { cachedMovies, apiResult ->
                when (apiResult) {
                    ApiResult.ApiLoading -> {
                        HomeState.Loading(cachedMovies)
                    }

                    is ApiResult.ApiSuccess -> {
                        totalPages = apiResult.pageCount
                        HomeState.Success(cachedMovies)
                    }

                    is ApiResult.ApiError -> {
                        HomeState.Error(
                            cachedMovies,
                            apiResult.message
                        )
                    }
                }
            }.collect {
                updateState(it)
            }
        }
    }
}