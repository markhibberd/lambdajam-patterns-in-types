package challenge7

import challenge0._
import org.scalacheck.{Arbitrary, Gen}, Arbitrary._, Gen._

object StateTArbitraries {
  implicit def StateTArbitrary[M[_]: Monad, S: Arbitrary, A: Arbitrary]: Arbitrary[StateT[M, S, A]] =
    Arbitrary(oneOf(
      arbitrary[A] map (a => StateT.value[M, S, A](a)),
      arbitrary[(S, A)] map ({
        case (s, a) => for {
          _ <- StateT.put[M, S](s)
          r <- StateT.value[M, S, A](a)
        } yield r
      })
    ))
}
