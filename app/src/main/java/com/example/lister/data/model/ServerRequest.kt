package com.example.lister.data.model

import com.beust.klaxon.Klaxon
import com.example.lister.data.ServerResponseFactory

class ServerRequest(
    val account: MutableMap<String, Any> = mutableMapOf(),
    val `object`: MutableMap<String, Any> = mutableMapOf(),
    val action: String = ""){
    fun toJson(): String {
        val classMap = mutableMapOf<String, Any>()
        classMap["account"] = account
        classMap["object"] = `object`
        classMap["action"] = action
        return Klaxon().toJsonString(classMap)
    }

    fun send(): ServerResponse {
        return ServerResponseFactory().send_request(this)
    }
}
