package isel.openapi.mock

import io.ktor.http.ContentType
import io.ktor.server.application.*
import io.ktor.server.request.uri
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.toMap

val paths = arrayOf(
    "/login" to { _: Map<String, String> -> "Login!" },
    "/channel" to { params: Map<String, String> -> "Channel" },
    "/channel/{channelId}" to { params: Map<String, String> -> "Channel ${params["channelId"]}" },
    "/channel/{channelId}/messages" to { params: Map<String, String> -> "Channel ${params["channelId"]} messages" },
    "/channel/{channelId}/members" to { params: Map<String, String> -> "Channel ${params["channelId"]} members" },
    "/channel/{channelId}/members/{memberId}" to { params: Map<String, String> -> "Channel ${params["channelId"]} member ${params["memberId"]}" },
)

fun Application.configureRouting() {

    val router = Router()

    paths.forEach { (path, handler) ->
        router.register(path) { params ->
            handler(params.toMap())
        }
    }

    routing {
        get("/{...}") {
            val path = call.request.uri
            val (handler, params) = router.match(path) ?: return@get call.respondText("Not Found")

            if (handler != null) {
                call.respondText(handler(params), ContentType.Text.Plain)
            } else {
                call.respondText("Not Found")
            }
        }
    }
}
