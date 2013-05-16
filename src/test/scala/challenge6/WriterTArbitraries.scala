package challenge6

import challenge0._
import org.scalacheck.{Arbitrary, Gen}, Arbitrary._, Gen._

object WriterTArbitraries {
  implicit def WriterTArbitrary[M[_]: Monad, W: Arbitrary, A: Arbitrary]: Arbitrary[WriterT[M, W, A]] =
    Arbitrary(for {
      log <- arbitrary[W]
      value <- arbitrary[A]
    } yield WriterT.writer[M, W, A](value)(log))
}
