package isel.openapi.mock.tests

import isel.openapi.mock.openApiParsing.validateOpenApi

const val openAPIDef = "openapi: 3.0.4\n" +
        "info:\n" +
        "  title: Sample API\n" +
        "  description: Optional multiline or single-line description in [CommonMark](http://commonmark.org/help/) or HTML.\n" +
        "  version: 0.1.9\n" +
        "\n" +
        "servers:\n" +
        "  - url: http://api.example.com/v1\n" +
        "    description: Optional server description, e.g. Main (production) server\n" +
        "  - url: http://staging-api.example.com\n" +
        "    description: Optional server description, e.g. Internal staging server for testing\n" +
        "\n" +
        "paths:\n" +
        "  /users:\n" +
        "    get:\n" +
        "      summary: Returns a list of users.\n" +
        "      description: Optional extended description in CommonMark or HTML.\n" +
        "      parameters:\n" +
        "        - name: x\n" +
        "          in: query\n" +
        "          description: asd\n" +
        "          required: true\n" +
        "          allowEmptyValue: true\n" +
        "          schema:\n" +
        "            type: string\n" +
        "      responses:\n" +
        "        \"200\": # status code\n" +
        "          description: A JSON array of user names\n" +
        "          content:\n" +
        "            application/json:\n" +
        "              schema:\n" +
        "                type: array\n" +
        "                items:\n" +
        "                  type: string"

class OpenAPIParseTest {

    @Test
    fun test() {
        assert(validateOpenApi(openAPIDef))
    }

}