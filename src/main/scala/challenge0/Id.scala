package challenge0

case class Id[A](run: A) {
  def map[B](f: A => B): Id[B] =
    Id(f(run))

  def flatMap[B](f: A => Id[B]): Id[B] =
    f(run)
}

object Id {
  implicit def IdMonad: Monad[Id] = new Monad[Id] {
    def point[A](a: => A) = Id(a)
    def bind[A, B](a: Id[A])(f: A => Id[B]): Id[B] = a flatMap f
  }

  implicit def IdEqual[A: Equal]: Equal[Id[A]] =
    Equal.from[Id[A]]((a, b) => Equal[A].equal(a.run, b.run))
}
