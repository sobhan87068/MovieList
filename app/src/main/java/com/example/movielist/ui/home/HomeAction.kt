package com.example.movielist.ui.home

import com.example.movielist.base.ViewAction

sealed class HomeAction : ViewAction {
    data object LoadMore : HomeAction()
}