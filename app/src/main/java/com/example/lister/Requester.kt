package com.example.lister
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class Requester(host: String = "localhost", port: Int = 8080){
    private val url = URL("http://$host:$port")
    private lateinit var conn: HttpURLConnection

    private fun open(){
         conn = url.openConnection() as HttpURLConnection
    }

    private fun close(){
        conn.disconnect()
    }

    private fun prepareRequest(data: String){
        conn.requestMethod = "POST"
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        conn.setRequestProperty("Content-Length", data.toByteArray().size.toString())
        conn.setRequestProperty("Content-Language", "en-US")
        conn.useCaches = false
        conn.doOutput = true
    }

    private fun sendRequest(data: String) {
        val wr = DataOutputStream(
            conn.outputStream
        )
        wr.writeBytes(data)
        wr.close()
        return
    }

    private fun getResponse(): String {
        val instream = conn.inputStream
        val rd = BufferedReader(InputStreamReader(instream))
        val response = rd.readLines().toString()
        rd.close()
        return response.substring(1, response.count()-1)
    }

    fun makeRequest(data: String): String {
        open()
        prepareRequest(data)
        sendRequest(data)
        val response = getResponse()
        close()
        return response
    }
}

fun main(){
    val req = Requester(port=7000)
    val response = req.makeRequest("{\"account\": {\"type\": \"account\", \"login\": \"test1\", \"password\": \"test2\"}, \"object\": {\"type\": \"test3\"}, \"action\": \"test4\"}")
    print(response)
}

