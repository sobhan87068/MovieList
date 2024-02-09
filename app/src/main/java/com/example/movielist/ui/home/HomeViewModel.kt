package com.example.movielist.ui.home

import androidx.lifecycle.viewModelScope
import com.example.movielist.base.BaseViewModel
import com.example.movielist.data.model.Movie
import com.example.movielist.domain.GetUpcomingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUpcomingUseCase: GetUpcomingUseCase
) : BaseViewModel<HomeAction, HomeState>(HomeState.Loading) {

    private var nextPage = 1

    private var currentItems = listOf<Movie>()
    override fun handleAction(action: HomeAction) {
        viewModelScope.launch {
            updateState(
                if (currentItems.isEmpty()) {
                    HomeState.Loading
                } else HomeState.NewPage.PaginationLoading(currentItems)
            )
            getUpcomingUseCase(nextPage).collect {
                currentItems = (currentItems + it).distinctBy(Movie::id)
                updateState(HomeState.NewPage.Success(currentItems))
                nextPage++
            }
        }
    }
}