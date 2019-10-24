package no.nav.btn

import com.natpryce.konfig.*

private val defaultProperties = ConfigurationMap(
        mapOf(
                "BOOTSTRAP_SERVER_CONFIG" to "localhost:9092",
                "CLIENT_ID_CONFIG" to "btn-brukermelding-oppgave"
        )
)

data class Configuration(
        val serverUrl: String = config()[Key("BOOTSTRAP_SERVER_CONFIG", stringType)],
        val clientId: String = config()[Key("CLIENT_ID_CONFIG", stringType)]
)

private fun config() = ConfigurationProperties.systemProperties() overriding
        EnvironmentVariables overriding
        defaultProperties