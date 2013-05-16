package challenge6

import challenge0._, EqualSyntax._

object Challenge6Spec extends test.Spec {
  import Laws._
  import WriterT._
  import WriterTArbitraries._

  "WriterT" should {
    "satisfy equal laws" ! equal.laws[WriterT[Id, List[Int], Int]]

    "satisfy monad laws" ! monad.laws[WriterT_[Id, List[Int]]#l]

    "return zero for value" ! prop((i: Int) =>
      WriterT.value[Id, List[Int], Int](i).run === Id((Monoid[List[Int]].zero, i)))

    "tell appends" ! prop((i: List[Int], j: List[Int]) =>
      (for {
        _ <- tell[Id, List[Int]](i)
        _ <- tell[Id, List[Int]](j)
      } yield ()).run === Id((i ++ j, ())))
  }
}
