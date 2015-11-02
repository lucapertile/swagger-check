package de.leanovate.swaggercheck.fixtures.model

import java.time.temporal.ChronoUnit
import java.time.{Instant, LocalDate}

import de.leanovate.swaggercheck.Generators
import org.scalacheck.{Arbitrary, Gen}
import play.api.libs.json.Json

case class AnyThing(
                     anUUID: String,
                     anURL: String,
                     anURI: String,
                     anEmail: String,
                     aDate: LocalDate,
                     aDateTime: Instant,
                     anInt32: Int,
                     anInt64: Long,
                     aFloat: Float,
                     aDouble: Double
                     )

object AnyThing {
  implicit val jsonFormat = Json.format[AnyThing]

  implicit val arbitrary = Arbitrary(for {
    anUUID <- Gen.uuid.map(_.toString)
    anURL <- Generators.url
    anURI <- Generators.uri
    anEmail <- Generators.email
    aDate <- Arbitrary.arbitrary[Int].map(diff => LocalDate.now().plus(diff, ChronoUnit.DAYS))
    aDateTime <- Arbitrary.arbitrary[Long].map(diff => Instant.now().plus(diff, ChronoUnit.MILLIS))
    anInt32 <- Arbitrary.arbitrary[Int]
    anInt64 <- Arbitrary.arbitrary[Long]
    aFloat <- Arbitrary.arbitrary[Float]
    aDouble <- Arbitrary.arbitrary[Double]
  } yield AnyThing(anUUID, anURL, anURI, anEmail, aDate, aDateTime, anInt32, anInt64, aFloat, aDouble))
}