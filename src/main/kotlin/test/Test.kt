package test

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

import kotlinx.datetime.Instant
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char

object InstantIso8601Serializer : KSerializer<Instant> {
    private val dateTimeFormat = DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET
    private val outputFormat = DateTimeComponents.Format {
        year()
        char('-')
        monthNumber()
        char('-')
        dayOfMonth()
        char('T')
        hour()
        char(':')
        minute()
        char(':')
        second()
        char('.')
        secondFraction(3)
        offset(UtcOffset.Formats.ISO)
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("kotlinx.datetime.Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant) = encoder.encodeString(value.format(outputFormat))

    override fun deserialize(decoder: Decoder): Instant = dateTimeFormat.parse(decoder.decodeString()).toInstantUsingOffset()
}


val dateString = "2025-02-11T16:34:20.000Z"

fun main() {
    println(dateString)
    val tokenExpiry: Instant = Json.decodeFromString(InstantIso8601Serializer, "\"$dateString\"")
    println(tokenExpiry)
}