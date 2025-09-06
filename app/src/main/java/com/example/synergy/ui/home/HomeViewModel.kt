package com.example.synergy.ui.home

import androidx.lifecycle.ViewModel
import com.example.synergy.BuildConfig
import com.example.synergy.data.model.Mentor
import com.example.synergy.util.DummyData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _favoriteMentors = MutableStateFlow(emptyList<Mentor>())
    val favoriteMentors: StateFlow<List<Mentor>> = _favoriteMentors

    fun getFavoriteMentors() {
        if (BuildConfig.DEBUG)
            _favoriteMentors.value = DummyData.dummyMentors
        else{

        }
    }
}