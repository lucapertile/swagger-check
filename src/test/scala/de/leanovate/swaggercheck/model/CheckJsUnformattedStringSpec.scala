package de.leanovate.swaggercheck.model

import org.scalacheck.Shrink
import org.scalatest.{MustMatchers, WordSpec}

class CheckJsUnformattedStringSpec extends WordSpec with MustMatchers {
  "JsUnformattedString" should {
    "shrink without min length" in {
      val original = CheckJsString.unformatted( "0123456789abcdefghijklmnopqrstuvwxyz")

      val shrink = Shrink.shrink(original)

      shrink must not be empty
      shrink.foreach {
        value =>
          value.minLength mustBe empty
          value.value.length must be < 36
          value.value.forall(_.isLetterOrDigit) mustBe true
      }
    }

    "shrink with min length" in {
      val original = CheckJsString(formatted = false, Some(30), "0123456789abcdefghijklmnopqrstuvwxyz")

      val shrink = Shrink.shrink(original)

      shrink must not be empty
      shrink.foreach {
        value =>
          value.minLength mustBe Some(30)
          value.value.forall(_.isLetterOrDigit) mustBe true
          value.value.length must be < 36
          value.value.length must be >= 30
      }
    }

    "not shrink beneath min length" in {
      val original = CheckJsString(formatted = false, Some(36), "0123456789abcdefghijklmnopqrstuvwxyz")

      val shrink = Shrink.shrink(original)

      shrink mustBe empty
    }
  }
}
