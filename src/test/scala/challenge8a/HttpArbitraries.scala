package challenge8a

import challenge0._
import org.scalacheck.{Arbitrary, Gen}, Arbitrary._, Gen._

object HttpArbitraries {

  implicit val methodArbitrary: Arbitrary[Method] = Arbitrary(oneOf(Options, Get, Head, Post, Put, Delete, Trace, Connect))

  implicit val headerGen: Gen[(String, String)] =
    for {
      h <- Gen.alphaStr
      v <- Gen.alphaStr
    } yield h -> v

  implicit val headersGen: Gen[Headers] =
    for {
      v <- Gen.containerOf[List, (String, String)](headerGen)
    } yield Headers(v.toVector)

  implicit val httpReadGen: Gen[HttpRead] =
    for {
      method <- methodArbitrary.arbitrary
      headers <- headersGen
      body <- Gen.alphaStr
    } yield HttpRead(method, body, headers)

  implicit val httpStateGen: Gen[HttpState] =
    for {
      headers <- headersGen
    } yield HttpState(headers)

  implicit val httpReadArbitrary: Arbitrary[HttpRead] = Arbitrary(httpReadGen)

  implicit val httpStateArbitrary: Arbitrary[HttpState] = Arbitrary(httpStateGen)

  implicit def httpArbitrary[A: Arbitrary]: Arbitrary[Http[A]] =
    Arbitrary(oneOf(
      arbitrary[A] map (a => Http.value(a)),
      for {
        log <- arbitrary[String]
        s <- httpStateGen
        a <- arbitrary[A]
      } yield {
        for {
          _ <- Http.log(log)
          _ <- Http.httpModify(_ => s)
          r <- Http.value(a)
        } yield r
      }
    ))
}
