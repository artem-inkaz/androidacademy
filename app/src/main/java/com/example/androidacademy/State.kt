package com.example.androidacademy

sealed class State {
    object Init : State()
    object Loading : State()
    object Error : State()
    object EmptyDataSet : State()
    object Success : State()
}