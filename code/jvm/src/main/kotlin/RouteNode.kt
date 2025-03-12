package isel.openapi.mock

class RouteNode(val part: String) {
    val children = mutableMapOf<String, RouteNode>()
    var handler: ((Map<String, String>) -> String)? = null
    val isParameter = part.startsWith("{")
}

class Router {
    private val root = RouteNode("")

    fun register(path: String, handler: (Map<String, String>) -> String) {
        val parts = path.split("/").filter { it.isNotEmpty() }
        var current = root

        for (part in parts) {
            val newP = part.removePrefix("{").removeSuffix("}")
            current = current.children.computeIfAbsent(newP) { RouteNode(newP) }
        }
        current.handler = handler
    }

    fun match(path: String): Pair<((Map<String, String>) -> String)?, Map<String, String>>?{
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
                    params[wildcardChild.part] = part
                } else {
                    return null
                }
            }
        }
        return current.handler to params
    }
}
