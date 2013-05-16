package challenge0

import org.scalacheck.{Arbitrary, Gen, Prop, Properties}
import Prop.forAll

object Laws {
  object monad {
    def associativity[F[_], A, B, C](implicit F: Monad[F], A: Arbitrary[F[A]], B: Arbitrary[A => F[B]], C: Arbitrary[B => F[C]], E: Equal[F[C]]) =
      forAll(MonadLaws.associative[F, A, B, C] _)

    def rightIdentity[F[_], A](implicit F: Monad[F], A: Arbitrary[F[A]], E: Equal[F[A]]) =
      forAll(MonadLaws.rightIdentity[F, A] _)

    def leftIdentity[F[_], A, B](implicit F: Monad[F], A: Arbitrary[A], B: Arbitrary[A => F[B]], E: Equal[F[B]]) =
      forAll(MonadLaws.leftIdentity[F, A, B] _)

    def laws[F[_]](implicit F: Monad[F], A: Arbitrary[F[Int]], B: Arbitrary[Int => F[Int]], C: Arbitrary[F[Int => Int]], E: Equal[F[Int]]) =
      new Properties("monad") {
        property("associativity") = associativity[F, Int, Int, Int]
        property("right identity") = rightIdentity[F, Int]
        property("left identity") = leftIdentity[F, Int, Int]
      }
  }

  object equal {
    def commutativity[A: Equal: Arbitrary] =
      forAll(EqualLaws.commutative[A] _)

    def reflexive[A: Equal: Arbitrary] =
      forAll(EqualLaws.reflexive[A] _)

    def transitive[A: Equal: Arbitrary] =
      forAll(EqualLaws.transitive[A] _)

    def laws[A: Equal: Arbitrary] =
      new Properties("equal") {
        property("commutativity") = commutativity[A]
        property("reflexive") = reflexive[A]
        property("transitive") = transitive[A]
      }
  }

  object monoid {
    def associative[A: Equal: Arbitrary: Monoid] =
      forAll(MonoidLaws.associative[A] _)

    def rightIdentity[A: Equal: Arbitrary: Monoid] =
      forAll(MonoidLaws.rightIdentity[A] _)

    def leftIdentity[A: Equal: Arbitrary: Monoid] =
      forAll(MonoidLaws.leftIdentity[A] _)

    def laws[A: Equal: Arbitrary: Monoid] =
      new Properties("monoid") {
        property("associative") = associative[A]
        property("left identity") = leftIdentity[A]
        property("right identity") = rightIdentity[A]
      }
  }
}
