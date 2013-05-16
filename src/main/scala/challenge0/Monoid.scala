package challenge0

trait Monoid[A] {
  def zero: A
  def append(l: A, r: => A): A
}

object Monoid {
  def apply[A: Monoid]: Monoid[A] =
    implicitly[Monoid[A]]

  def from[A](f: (A, => A) => A, z: A): Monoid[A] =
    new Monoid[A] {
      def zero = z
      def append(l: A, r: => A) = f(l, r)
    }

  implicit def ListMonoid[A] =
    from[List[A]](_ ++ _, Nil)

  implicit def StringMonoid =
    from[String](_ + _, "")
}

object MonoidLaws {
  def associative[A: Equal: Monoid](a1: A, a2: A, a3: A): Boolean =
    Equal[A].equal(
      Monoid[A].append(a1, Monoid[A].append(a2, a3)),
      Monoid[A].append(Monoid[A].append(a1, a2), a3)
    )

  def rightIdentity[A: Equal: Monoid](a: A) =
    Equal[A].equal(a, Monoid[A].append(a, Monoid[A].zero))

  def leftIdentity[A: Equal: Monoid](a: A) =
    Equal[A].equal(a, Monoid[A].append(Monoid[A].zero, a))
}
