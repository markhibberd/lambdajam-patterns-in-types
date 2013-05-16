package challenge4

import org.scalacheck.{Arbitrary, Gen}, Arbitrary._, Gen._

object StateArbitraries {
  implicit def StateArbitrary[S: Arbitrary, A: Arbitrary]: Arbitrary[State[S, A]] =
    Arbitrary(oneOf(
      arbitrary[A] map (a => State.value[S, A](a)),
      arbitrary[(S, A)] map ({
        case (s, a) => for {
          _ <- State.put(s)
          r <- State.value[S, A](a)
        } yield r
      })
    ))
}
