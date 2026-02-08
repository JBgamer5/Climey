package com.alejandro.climey.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.climey.domain.model.Location
import com.alejandro.climey.domain.usecases.SearchLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val searchLocation: SearchLocation
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val state = _searchQuery
        .debounce(300L)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            flow {
                if (query.isNotBlank()) {
                    emit(SearchState(isLoading = true))

                    searchLocation(query).fold(
                        onSuccess = {
                            emit(SearchState(results = it))
                        },
                        onFailure = {
                            emit(SearchState(error = it))
                        }
                    )
                } else {
                    emit(SearchState())
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchState()
        )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }


}

data class SearchState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val results: List<Location> = emptyList()
)