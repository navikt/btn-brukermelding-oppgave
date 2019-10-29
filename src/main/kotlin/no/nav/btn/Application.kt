package no.nav.btn

fun main() {
    val consumer = BrukermeldingOppgaveRiver()

    Runtime.getRuntime().addShutdownHook(Thread {
        consumer.stop()
    })

    consumer.start()
}
