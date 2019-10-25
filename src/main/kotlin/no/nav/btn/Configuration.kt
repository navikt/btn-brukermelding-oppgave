package no.nav.btn

import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

val defaultConsumerConfig = Properties().apply {
        put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1")
        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
        put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PacketDeserializer::class.java.name)
}

fun consumerConfig(
        groupId: String,
        bootstrapServerUrl: String,
        properties: Properties = defaultConsumerConfig
): Properties {
        return Properties().apply {
                putAll(properties)
                putAll(commonConfig(bootstrapServerUrl))
                put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
        }
}

val defaultProducerConfig = Properties().apply {
        put(ProducerConfig.ACKS_CONFIG, "1")
        put(ProducerConfig.BATCH_SIZE_CONFIG, "1")
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PacketSerializer::class.java.name)
}

fun producerConfig(
        clientId: String,
        bootstrapServers: String,
        properties: Properties = defaultProducerConfig
): Properties {
        return Properties().apply {
                putAll(properties)
                putAll(commonConfig(bootstrapServers))
                put(ProducerConfig.CLIENT_ID_CONFIG, clientId)
        }
}

fun commonConfig(bootstrapServers: String): Properties {
        return Properties().apply {
                put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        }
}