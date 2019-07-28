package com.example.lister.data

import com.beust.klaxon.JsonArray
import com.beust.klaxon.Klaxon
import com.example.lister.Requester
import com.example.lister.data.model.ServerRequest
import com.example.lister.data.model.ServerResponse
import java.io.StringReader

class ServerResponseFactory {
    fun fromJsonString(string: String): ServerResponse =
        try {
            val parsed = Klaxon().parseJsonObject(StringReader(string))
            val status = parsed["status"] as String
            if (status == "handled") {
                if (parsed["objects"]!!.javaClass.name == "com.beust.klaxon.JsonArray") {
                    ServerResponse(
                        status = status,
                        objects = Klaxon().parseFromJsonArray<MutableMap<String, Any?>>(parsed["objects"] as JsonArray<*>) as MutableList<MutableMap<String, Any?>>
                    )
                } else
                    ServerResponse(
                        status = status
                    )
            } else
                ServerResponse(status = status, error = parsed["error"] as String)
        } catch (e: Exception) {
            println(e)
            ServerResponse("failed", mutableListOf(), "bad response")
        }

    fun send_request(request: ServerRequest): ServerResponse{
        val req = Requester("192.168.30.10", 7000)
        val jsonResponse = req.makeRequest(request.toJson())
        return fromJsonString(jsonResponse)
    }
}