package com.example.stackexchangetest.data.model

import com.example.stackexchangetest.model.User

data class StackExchangeResponse(
    val items: List<User>,
    val has_more: Boolean
)
