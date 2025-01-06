package com.example.myapplication.network

import okhttp3.OkHttpClient

class WebSocketClient {
    private var client = OkHttpClient()
    private lateinit var webSocket: okhttp3.WebSocket

}