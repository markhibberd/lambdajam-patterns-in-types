package challenge1

import challenge0._, EqualSyntax._

object Challenge1Spec extends test.Spec {
  import Laws._
  import ResultArbitraries._

  "Result" should {
    "satisfy equal laws with Int" ! equal.laws[Result[Int]]

    "satisfy equal laws with String" ! equal.laws[Result[String]]

    "satisfy monad laws" ! monad.laws[Result]

    "fold with explosion" ! prop((t: Throwable) =>
      Result.explosion[Int](t).fold(_ == t, _ => false, _ => false))

    "fold with fail" ! prop((s: String) =>
      Result.fail[Int](s).fold(_ => false, _ === s, _ => false))

    "fold with ok" ! prop((i: Int) =>
      Result.ok[Int](i).fold(_ => false, _ => false, _ === i))
  }
}
