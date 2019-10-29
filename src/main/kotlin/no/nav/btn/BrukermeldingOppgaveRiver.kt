package no.nav.btn

import no.nav.btn.kafkaservices.RiverWithServiceCall
import no.nav.btn.packet.Packet

class BrukermeldingOppgaveRiver : RiverWithServiceCall(
        consumerTopics = listOf(TOPIC_MELDING_FRA_BRUKER),
        onSuccessfullCallTopic = TOPIC_MELDING_MED_OPPGAVE,
        retryOnFailedCallTopic = TOPIC_RETRY_OPPGAVE_OPPRETTELSE
) {
    override val SERVICE_APP_ID: String
        get() = "btn-brukermelding-oppgave"

    override fun makeServiceCall(packet: Packet): Packet {
        makeMockServerCall()
        return packet
    }
}