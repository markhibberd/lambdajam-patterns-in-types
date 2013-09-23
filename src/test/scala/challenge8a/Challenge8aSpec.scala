package challenge8a

import challenge0._, EqualSyntax._

class Challenge8aSpec extends test.Spec {
  import Laws._
  import HttpArbitraries._

  "Http" should {
    "satisfy monad laws" ! monad.laws[Http]
  }

  implicit def HttpWriteEqual =
    Equal.derived[HttpWrite]

  implicit def HttpStateEqual =
    Equal.derived[HttpState]

  implicit def HttpValueEqual[A: Equal]: Equal[HttpValue[A]] =
    Equal.from[HttpValue[A]]((a, b) => a.fold(
      t => b.fold(_ === t, _ => false, _ => false),
      m => b.fold(_ => false, _ === m, _ => false),
      a => b.fold(_ => false, _ => false, _ === a)
    ))

  implicit def HttpEqual[A: Equal]: Equal[Http[A]] =
    Equal.from[Http[A]]((a, b) => {
      val r = HttpRead(Get, "abc", Headers(Vector("accept" -> "application/json")))
      val s = HttpState(Headers(Vector("loggedin" -> "fred")))
      a.run(r, s) === b.run(r, s)
    })
}
