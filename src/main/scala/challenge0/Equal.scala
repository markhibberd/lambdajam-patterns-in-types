package challenge0

trait Equal[A] {
  def equal(a1: A, a2: A): Boolean
}

object Equal {
  def apply[A: Equal]: Equal[A] =
    implicitly[Equal[A]]

  def from[A](f: (A, A) => Boolean): Equal[A] =
    new Equal[A] { def equal(a1: A, a2: A) = f(a1, a2) }

  def derived[A]: Equal[A] =
    from[A](_.equals(_))

  implicit def UnitEqual =
    derived[Unit]

  implicit def StringEqual =
    derived[String]

  implicit def IntEqual =
    derived[Int]

  implicit def OptionEqual[A: Equal]: Equal[Option[A]] =
    from[Option[A]]({
      case (Some(a1), Some(a2)) => Equal[A].equal(a1, a2)
      case (None, None)         => true
      case (None, Some(_))      => false
      case (Some(_), None)      => false
    })

  implicit def ListEqual[A: Equal] =
    from[List[A]](_.corresponds(_)(Equal[A].equal))

  implicit def Tuple2Equal[A: Equal, B: Equal] =
    from[(A, B)]({
      case ((a1, b1), (a2, b2)) =>
        Equal[A].equal(a1, a2) && Equal[B].equal(b1, b2)
    })

  implicit def ThrowableEqual =
    derived[Throwable]
}

case class EqualSyntax[A](value: A) {
  def ===(other: A)(implicit A: Equal[A]) =
    Equal[A].equal(value, other)
}

object EqualSyntax {
  implicit def ToEqualSyntax[A](value: A) =
    EqualSyntax(value)
}

object EqualLaws {
  def commutative[A: Equal](a1: A, a2: A): Boolean =
    Equal[A].equal(a1, a2) == Equal[A].equal(a2, a1)

  def reflexive[A: Equal](f: A): Boolean =
    Equal[A].equal(f, f)

  def transitive[A: Equal](a1: A, a2: A, a3: A): Boolean =
    !(Equal[A].equal(a1, a2) && Equal[A].equal(a2, a3)) || Equal[A].equal(a1, a3)
}
