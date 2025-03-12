package isel.openapi.mock

import io.ktor.http.ContentType
import io.ktor.server.application.*
import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.toMap

val paths = arrayOf(
    "GET" to "/login" to { params: Map<String, String> -> "Get login!" },
    "GET" to "/channel" to { params: Map<String, String> -> "Get channel" },
    "GET" to "/channel/{channelId}" to { params: Map<String, String> -> "Get channel ${params["channelId"]}" },
    "GET" to "/channel/{channelId}/messages" to { params: Map<String, String> -> "Get channel ${params["channelId"]} messages" },
    "GET" to "/channel/{channelId}/members" to { params: Map<String, String> -> "Get channel ${params["channelId"]} members" },
    "GET" to "/channel/{channelId}/members/{memberId}" to { params: Map<String, String> -> "Get channel ${params["channelId"]} member ${params["memberId"]}" },
    "POST" to "/login" to { params: Map<String, String> -> "Post login!" },
    "POST" to "/channel" to { params: Map<String, String> -> "Post channel" },
    "POST" to "/channel/{channelId}" to { params: Map<String, String> -> "Post channel ${params["channelId"]}" },
    "POST" to "/channel/{channelId}/messages" to { params: Map<String, String> -> "Post channel ${params["channelId"]} messages" },
    "POST" to "/channel/{channelId}/members" to { params: Map<String, String> -> "Post channel ${params["channelId"]} members" },
    "POST" to "/channel/{channelId}/members/{memberId}" to { params: Map<String, String> -> "Post channel ${params["channelId"]} member ${params["memberId"]}" },
)

fun Application.configureRouting() {

    val router = Router()

    paths.forEach { (req, handler) ->
        router.register(req.first, req.second) { params ->
            handler(params.toMap())
        }
    }

    routing {
        get("/{...}") {
            val path = call.request.uri
            val (handler, params) = router.match("GET", path) ?: return@get call.respondText("Not Found")

            if (handler != null) {
                call.respondText(handler(params), ContentType.Text.Plain)
            } else {
                call.respondText("Not Found")
            }
        }
        post("/{...}") {
            val path = call.request.uri
            val (handler, params) = router.match("POST", path) ?: return@post call.respondText("Not Found")

            if (handler != null) {
                call.respondText(handler(params), ContentType.Text.Plain)
            } else {
                call.respondText("Not Found")
            }
        }
    }
}
