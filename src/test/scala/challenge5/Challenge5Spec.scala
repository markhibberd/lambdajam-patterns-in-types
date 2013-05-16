package challenge5

import challenge0._, EqualSyntax._

object Challenge5Spec extends test.Spec {
  import Laws._
  import ReaderT._
  import ReaderTArbitraries._

  "ReaderT" should {
    "satisfy monad laws" ! monad.laws[ReaderT_[Id, Int]#l]

    "return context for ask" ! prop((i: Int) =>
      ask[Id, Int].run(i) === Id(i))

    "modify environment for local" ! prop((i: Int) =>
      local[Id, Int, Int](_ * 2)(ask[Id, Int]).run(i) === Id(i * 2))
  }

  implicit def ReaderTEqual[A: Equal]: Equal[ReaderT[Id, Int, A]] =
    Equal.from[ReaderT[Id, Int, A]]((a, b) =>
      a.run(0) === b.run(0))
}
