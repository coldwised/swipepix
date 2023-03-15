package com.coldwised.swipepix.data.remote

import com.coldwised.swipepix.R
import com.coldwised.swipepix.data.remote.dto.ImageDto
import com.coldwised.swipepix.domain.model.ImageModel
import com.coldwised.swipepix.util.Resource
import com.coldwised.swipepix.util.UiText
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class GallerySocketServiceImpl(
    private val client: HttpClient
): GallerySocketService {

    private var socket: WebSocketSession? = null
    override suspend fun initSession(username: String): Resource<Unit> {
        return try {
            val socketLocal = client.webSocketSession {
                url(ImagesService.Endpoints.GetAllImages.url)
            }
            socket = socketLocal
            if(socketLocal.isActive) {
                Resource.Success(Unit)
            } else {
                Resource.Error(UiText.StringResource(R.string.socket_connection_error))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(UiText.StringResource(R.string.unknown_error))
        }
    }

    override suspend fun sendImage(imageUrl: String) {
        try {
            socket?.send(Frame.Text(imageUrl))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeImages(): Flow<ImageModel> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as Frame.Text).readText()
                    val imageDto = Json.decodeFromString<ImageDto>(json)
                    imageDto.toImageModel()
                } ?: flow {}
        } catch (e: Exception) {
            e.printStackTrace()
            flow {}
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}