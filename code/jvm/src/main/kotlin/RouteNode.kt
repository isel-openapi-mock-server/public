package isel.openapi.mock

class RouteNode(val part: String) {
    val children = mutableMapOf<String, RouteNode>()
    val info = mutableMapOf<String, ((Map<String, String>) -> String)?>()
    val isParameter = part.startsWith("{")
}

class Router {
    private val root = RouteNode("")

    fun register(method: String, path: String, handler: (Map<String, String>) -> String) {
        val parts = path.split("/").filter { it.isNotEmpty() }
        var current = root

        for (part in parts) {
            current = current.children.computeIfAbsent(part) { RouteNode(part) }
        }
        current.info[method] = handler
    }

    fun match(method: String, path: String): Pair<((Map<String, String>) -> String)?, Map<String, String>>?{
        val parts = path.split("/").filter { it.isNotEmpty() }
        var current = root
        val params = mutableMapOf<String, String>()

        for (part in parts) {
            if (current.children.containsKey(part)) {
                current = current.children[part]!!
            }

            else {
                val wildcardChild = current.children.values.find { it.isParameter }
                if (wildcardChild != null) {
                    current = wildcardChild
                    params[wildcardChild.part.removePrefix("{").removeSuffix("}")] = part
                } else {
                    return null
                }
            }
        }
        return current.info[method] to params
    }
}
