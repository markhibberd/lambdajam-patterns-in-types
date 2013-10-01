package challenge3

import challenge0._, EqualSyntax._

object Challenge3Spec extends test.Spec {
  import Laws._
  import Writer._
  import WriterArbitraries._

  "Writer" should {
    "satisfy equal laws" ! equal.laws[Writer[List[Int], Int]]

    "satisfy monad laws" ! monad.laws[Writer_[List[Int]]#l]

    "return zero for value" ! prop((i: Int) =>
      Writer.value[List[Int], Int](i) == Writer(Monoid[List[Int]].zero, i))

    "tell appends" ! prop((i: List[Int], j: List[Int]) =>
      (for {
        _ <- tell(i)
        _ <- tell(j)
      } yield ()) === Writer(i ++ j, ()))
      
    "satisfy monoid laws" ! monoid.laws[Writer[List[Int], List[Int]]]
  }
}
