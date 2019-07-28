package com.example.lister.data.model

data class ServerResponse(
    var status: String,
    val objects: MutableList<MutableMap<String, Any?>>? = null,
    var error: String? = null
)
