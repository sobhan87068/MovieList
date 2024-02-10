package com.example.movielist.ui.home

import androidx.lifecycle.viewModelScope
import com.example.movielist.base.BaseViewModel
import com.example.movielist.data.result.ApiResult
import com.example.movielist.domain.GetUpcomingUseCase
import com.example.movielist.domain.RetrieveMoviesPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUpcomingUseCase: GetUpcomingUseCase,
    private val retrieveMoviesPageUseCase: RetrieveMoviesPageUseCase
) : BaseViewModel<HomeAction, HomeState>(HomeState.Idle) {

    private var nextPage = 1
    private var totalPages = 1

    override fun handleAction(action: HomeAction) {
        if (totalPages < nextPage) return
        if (state.value is HomeState.Loading
            || state.value is HomeState.NewPage.PaginationLoading
        ) return

        viewModelScope.launch {
            getUpcomingUseCase().combine(retrieveMoviesPageUseCase(nextPage)) { cachedMovies, apiResult ->
                when (apiResult) {
                    ApiResult.ApiLoading -> {
                        if (cachedMovies.isEmpty()) {
                            HomeState.Loading
                        } else {
                            HomeState.NewPage.PaginationLoading(cachedMovies)
                        }
                    }

                    is ApiResult.ApiSuccess -> {
                        totalPages = apiResult.pageCount
                        HomeState.NewPage.Success(cachedMovies)
                    }

                    is ApiResult.ApiError -> {
                        if (cachedMovies.isEmpty()) {
                            HomeState.Error(apiResult.code, apiResult.message)
                        } else {
                            HomeState.NewPage.PaginationError(
                                cachedMovies,
                                apiResult.code,
                                apiResult.message
                            )
                        }
                    }
                }
            }
                /*.catch {
                    updateState(
                    if (currentItems.isEmpty()) {
                        HomeState.Error(code = 500, it.message)
                    } else HomeState.NewPage.PaginationError(currentItems, code = 500, it.message)
                    )
                }*/.collect {
                    updateState(it)
                    nextPage++
                }
        }
    }
}