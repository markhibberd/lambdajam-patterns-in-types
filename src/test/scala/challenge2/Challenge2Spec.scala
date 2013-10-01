package challenge2

import challenge0._, EqualSyntax._

object Challenge2Spec extends test.Spec {
  import Laws._
  import Reader._
  import ReaderArbitraries._

  "Reader" should {
    "satisfy monad laws" ! monad.laws[Reader_[Int]#l]

    "return context for ask (guaranteed by types)" ! prop((i: Int) =>
      ask[Int].run(i) == i)

    "modify environment for local" ! prop((i: Int) =>
      local[Int, Int](_ * 2)(ask[Int]).run(i) == i * 2)

    "satisfy monoid laws" ! monoid.laws[Reader[Int, List[Int]]]
  }

  implicit def ReaderEqual[A: Equal]: Equal[Reader[Int, A]] =
    Equal.from[Reader[Int, A]]((a, b) =>
      a.run(0) === b.run(0))
}
