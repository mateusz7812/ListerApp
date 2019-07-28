package com.example.lister

import com.example.lister.data.LoginDataSource
import com.example.lister.data.ServerResponseFactory
import com.example.lister.data.model.ServerRequest
import com.example.lister.data.model.ServerResponse
import org.junit.Assert
import org.junit.Test

class UnitTest {
    @Test
    fun serverRequestToJson() {
        val request = ServerRequest(
            mutableMapOf("login" to "test1","password" to "test2"),
            mutableMapOf("type" to "test3"),
            "test4"
            )
        val jsonRequest = request.toJson()
        Assert.assertEquals("{\"account\": {\"login\": \"test1\", \"password\": \"test2\"}, \"object\": {\"type\": \"test3\"}, \"action\": \"test4\"}", jsonRequest)
    }

    @Test
    fun serverResponseFactoryFromJson(){
        val receivedResponse = ServerResponseFactory().fromJsonString("{\"status\": \"handled\", \"objects\": [{\"id\": 1, \"name\": \"cat\"}, {\"id\": 2, \"name\": \"dog\"}]}")
        val objects = mutableListOf<MutableMap<String, Any?>>()
        val first = mutableMapOf<String, Any?>()
        first["id"] = 1
        first["name"] = "cat"
        objects.add(first)
        val second = mutableMapOf<String, Any?>()
        second["id"] = 2
        second["name"] = "dog"
        objects.add(second)
        val requiredResponse = ServerResponse("handled", objects, null)
        Assert.assertEquals(requiredResponse.error, receivedResponse.error)
        Assert.assertEquals(requiredResponse.status, receivedResponse.status)
        Assert.assertEquals(requiredResponse.objects, receivedResponse.objects)
    }

    @Test
    fun serverResponseFactoryFromJsonWithErrorResponse(){
        val receivedResponse = ServerResponseFactory().fromJsonString("{\"status\": \"failed\", \"error\": \"dog\"}")
        val requiredResponse = ServerResponse("failed", null, "dog")
        Assert.assertEquals(requiredResponse.error, receivedResponse.error)
        Assert.assertEquals(requiredResponse.status, receivedResponse.status)
        Assert.assertEquals(requiredResponse.objects.toString(), receivedResponse.objects.toString())
    }

    @Test
    fun serverResponseFactoryFromBadJson(){
        val receivedResponse = ServerResponseFactory().fromJsonString("")
        val requiredResponse = ServerResponse("failed", mutableListOf(), "bad response")
        Assert.assertEquals(requiredResponse.error, receivedResponse.error)
        Assert.assertEquals(requiredResponse.status, receivedResponse.status)
        Assert.assertEquals(requiredResponse.objects.toString(), receivedResponse.objects.toString())
    }

    @Test
    fun loginDataSourceGetUserFromDB(){
        val user = LoginDataSource().getUserFromDB("asd", "asd")
        Assert.assertEquals(user!!.displayName, "asd")
    }

}