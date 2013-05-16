package challenge1

import org.scalacheck.{Arbitrary, Gen}, Arbitrary._, Gen._

object ResultArbitraries {
  import Result._

  implicit def ResultArbitrary[A: Arbitrary]: Arbitrary[Result[A]] =
    Arbitrary(frequency(
      (1, arbitrary[Throwable] map explosion),
      (1, arbitrary[String] map fail),
      (8, arbitrary[A] map ok)
    ))
}
