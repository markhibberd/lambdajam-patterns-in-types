package challenge8b

import challenge0._
import challenge1._ // result
import challenge5._ // ReaderT
import challenge6._ // WriterT
import challenge7._ // StateT
import MonadTrans._
import EqualSyntax._

import challenge8b.HttpTransformerStack._

object Challenge8bSpec extends test.Spec {
  import Laws._
  import HttpArbitraries._

  "Http" should {
    "satisfy monad laws" ! monad.laws[Http]
  }

  "Echo service" should {
    val echo: Http[String] = HttpExample.echo
    "return body as string" ! prop{(r: HttpRead, s: HttpState) =>
      runHttp(echo)(r, s) must beLike {
        case Ok((_, (_, a))) => a must_== r.body
      }
    }

    "add 'content-type' header of 'text/plain'" ! prop((r: HttpRead, s: HttpState) =>
      runHttp(echo)(r, s) must beLike {
        case Ok((s1, _)) => s1.resheaders.headers must contain("content-type" -> "text/plain")
      }
    )

    "log a message with the length of the body in characters" ! prop((r: HttpRead, s: HttpState) =>
      runHttp(echo)(r, s) must beLike {
        case Ok((_, (w, _))) => w.log must contain(r.body.length.toString)
      }
    )
  }

  def runHttp[A](http: Http[A]): (HttpRead, HttpState) => HttpValue[(HttpState, (HttpWrite, A))] = { (r, s) =>
    val rt: ReaderT[WSV, HttpRead, A] = http.run
    val wt: WriterT[SV, HttpWrite, A] = rt.run(r)
    val st: StateT[V, HttpState, (HttpWrite, A)] = wt.run
    val v: HttpValue[(HttpState, (HttpWrite, A))] = st.run(s)
    v
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
      runHttp(a)(r, s) === runHttp(b)(r, s)
    })
}
