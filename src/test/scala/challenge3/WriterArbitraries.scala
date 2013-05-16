package challenge3

import org.scalacheck.{Arbitrary, Gen}, Arbitrary._, Gen._

object WriterArbitraries {
  implicit def WriterArbitrary[W: Arbitrary, A: Arbitrary]: Arbitrary[Writer[W, A]] =
    Arbitrary(for {
      log <- arbitrary[W]
      value <- arbitrary[A]
    } yield Writer(log, value))
}
