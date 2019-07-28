package com.example.lister.data

import com.example.lister.data.model.ServerRequest

class ServerRequestFactory (var account: MutableMap<String, Any> = mutableMapOf("type" to "anonymous")){
    fun generateRequest(`object`: MutableMap<String, Any>, action: String): ServerRequest = ServerRequest(
        account = account,
        `object` = `object`,
        action = action
    )
}