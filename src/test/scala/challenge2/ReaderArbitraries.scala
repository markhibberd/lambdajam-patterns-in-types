package challenge2

import org.scalacheck.{Arbitrary, Gen}, Arbitrary._, Gen._

object ReaderArbitraries {
  implicit def ReaderArbitrary[R, A: Arbitrary]: Arbitrary[Reader[R, A]] =
    Arbitrary(arbitrary[A] map (a => Reader.value[R, A](a)))
}
