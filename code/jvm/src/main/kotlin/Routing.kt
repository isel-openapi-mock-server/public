package isel.openapi.mock

import io.ktor.http.ContentType
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

data class ApiRoute(
    val method: String,
    val path: String,
    val handler: (Map<String, String>) -> String,
)

val paths = arrayOf(
    ApiRoute("GET", "/login") { _: Map<String, String> -> "Get login!\n" },
    ApiRoute("GET", "/channel") { _: Map<String, String> -> "Get channel\n" },
    ApiRoute("GET", "/channel/{channelId}") { params: Map<String, String> -> "Get channel ${params["channelId"]}\n" },
    ApiRoute("GET", "/channel/{channelId}/messages") { params: Map<String, String> -> "Get channel ${params["channelId"]} messages\n" },
    ApiRoute("GET", "/channel/{channelId}/members") { params: Map<String, String> -> "Get channel ${params["channelId"]} members\n" },
    ApiRoute("GET", "/channel/{channelId}/members/{memberId}") { params: Map<String, String> -> "Get channel ${params["channelId"]} member ${params["memberId"]}\n" },
    ApiRoute("POST", "/login") { _: Map<String, String> -> "Post login!\n" },
    ApiRoute("POST", "/channel") { _: Map<String, String> -> "Post channel\n" },
    ApiRoute("POST", "/channel/{channelId}") { params: Map<String, String> -> "Post channel ${params["channelId"]}\n" },
    ApiRoute("POST", "/channel/{channelId}/messages") { params: Map<String, String> -> "Post channel ${params["channelId"]} messages\n" },
    ApiRoute("POST", "/channel/{channelId}/members") { params: Map<String, String> -> "Post channel ${params["channelId"]} members\n" },
    ApiRoute("POST", "/channel/{channelId}/members/{memberId}") { params: Map<String, String> -> "Post channel ${params["channelId"]} member ${params["memberId"]}\n" },
)

const val NOT_FOUND = "Not Found\n"

fun Application.configureRouting() {

    val router = Router()

    paths.forEach { apiRoute ->
        router.register(apiRoute.method, apiRoute.path) { params ->
            apiRoute.handler(params)
        }
    }

    routing {
        get("/{...}") {
            val path = call.request.uri
            val (handler, params) = router.match("GET", path) ?: return@get call.respondText(NOT_FOUND)

            if (handler != null) {
                call.respondText(handler(params), ContentType.Text.Plain)
            } else {
                call.respondText(NOT_FOUND)
            }
        }
        post("/{...}") {
            val path = call.request.uri
            val (handler, params) = router.match("POST", path) ?: return@post call.respondText(NOT_FOUND)

            if (handler != null) {
                call.respondText(handler(params), ContentType.Text.Plain)
            } else {
                call.respondText(NOT_FOUND)
            }
        }
    }
}
