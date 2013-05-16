package challenge4

import challenge0._, EqualSyntax._

object Challenge4Spec extends test.Spec {
  import Laws._
  import State._
  import StateArbitraries._

  "State" should {
    "satisfy monad laws" ! monad.laws[State_[Int]#l]

    "value should not modify state (guaranteed by types)" ! prop((s: String, i: Int) =>
      State.value[Int, String](s).run(i) === (i, s))

    "return state for get (guaranteed by types)" ! prop((i: Int) =>
      get[Int].run(i) === (i, i))

    "return view state for gets (guaranteed by types)" ! prop((i: Int) =>
      gets[Int, String](_.toString).run(i) === (i, i.toString))

    "update state with modify" ! prop((i: Int, f: Int => Int) =>
      modify(f).run(i) == (f(i), ()))

    "clobber state with put"  ! prop((i: Int, j: Int) =>
      put(j).run(i) == (j, ()))
  }

  implicit def StateEqual[A: Equal]: Equal[State[Int, A]] =
    Equal.from[State[Int, A]]((a, b) =>
      a.run(0) === b.run(0))
}
