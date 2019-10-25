package no.nav.btn

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.*

private val logger = LoggerFactory.getLogger(RiverConsumer::class.java)

class RiverConsumer(
        val consumerTopics: List<String>,
        val producerTopic: String,
        val retryTopic: String
) : ConsumerService() {
    lateinit var reproducer: KafkaProducer<String, Packet>
    override val SERVICE_APP_ID: String
        get() = "btn-brukermelding-oppgave"

    private fun initializeReproducer() {
        reproducer = KafkaProducer(getProducerConfig())
    }

    override fun run() {
        if (!::reproducer.isInitialized) {
            initializeReproducer()
        }

        val consumer = KafkaConsumer<String, Packet>(getConsumerConfig())
        consumer.subscribe(consumerTopics)
        while(job.isActive) {
            val records = consumer.poll(Duration.ofMillis(100))
            records.forEach {
                logger.info("Recieved ${it.value().message}")
                try {
                    makeMockServerCall()
                    reproducer.send(ProducerRecord(producerTopic, UUID.randomUUID().toString(), addBreadcrumbs(it.value())))
                } catch (e: Exception) {
                    logger.warn("Caught error")
                    reproducer.send(ProducerRecord(retryTopic, 0, it.key(), it.value(), listOf(RecordHeader("X-Failed-Attempts", "1".toByteArray()))))
                }
            }
        }
    }

    override fun shutdown() {
        if (::reproducer.isInitialized) {
            reproducer.flush()
            reproducer.close(Duration.ofSeconds(5))
        }
    }

    private fun addBreadcrumbs(packet: Packet): Packet = Packet(
            breadcrumbs = packet.breadcrumbs + Breadcrumb("btn-brukermelding-oppgave"),
            timestamp = packet.timestamp,
            message = packet.message
    )
}