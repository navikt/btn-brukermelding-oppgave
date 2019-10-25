package no.nav.btn

fun main() {
    val consumer = RiverConsumer(
            consumerTopics = listOf(TOPIC_MELDING_FRA_BRUKER),
            producerTopic = TOPIC_MELDING_MED_OPPGAVE,
            retryTopic = TOPIC_RETRY_OPPGAVE_OPPRETTELSE)

    Runtime.getRuntime().addShutdownHook(Thread {
        consumer.stop()
    })

    consumer.start()
}
