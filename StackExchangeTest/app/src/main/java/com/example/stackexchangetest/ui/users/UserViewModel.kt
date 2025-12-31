package com.example.stackexchangetest.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stackexchangetest.data.model.APIResponse
import com.example.stackexchangetest.data.repository.UserRepository
import com.example.stackexchangetest.model.User
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val repository = UserRepository()

    private val _usersState = MutableLiveData<List<User>>()
    val usersState: LiveData<List<User>> = _usersState

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var page = 1
    private var loadMore = true

    fun searchUsers(query: String?, isNextPage: Boolean = false) {

        if (!isNextPage) {
            page = 1
            loadMore = true
            _usersState.value = emptyList()
        }

        if (loadMore) {
            viewModelScope.launch {
                when (val result = repository.searchUsers(query = query, page = page)) {
                    is APIResponse.Success -> {
                        updateUserList(result.data.items)
                        page++
                        loadMore = result.data.has_more

                    }

                    is APIResponse.Error -> {
                        _error.value = result.message
                    }
                }
            }
        }
    }

    private fun updateUserList(newList: List<User>){
        val userList = _usersState.value?.toMutableList()
        userList?.addAll(newList)
        _usersState.value = userList ?: mutableListOf()
    }
}