package challenge0

trait Functor[F[_]] {
  def map[A, B](a: F[A])(f: A => B): F[B]
}

object Functor {
  def apply[F[_]: Functor]: Functor[F] =
    implicitly[Functor[F]]
}
