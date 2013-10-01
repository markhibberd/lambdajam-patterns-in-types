package challenge0

trait Monad[F[_]] extends Functor[F] {
  def point[A](a: => A): F[A]
  def bind[A, B](a: F[A])(f: A => F[B]): F[B]
  def map[A, B](a: F[A])(f: A => B): F[B] = bind(a)(b => point(f(b)))
}

object Monad {
  def apply[F[_]: Monad]: Monad[F] =
    implicitly[Monad[F]]

  implicit def OptionMonad: Monad[Option] = new Monad[Option] {
    def point[A](a: => A) = Some(a)
    def bind[A, B](a: Option[A])(f: A => Option[B]): Option[B] = a flatMap f
  }

  implicit def ListMonad: Monad[List] = new Monad[List] {
    def point[A](a: => A) = List(a)
    def bind[A, B](a: List[A])(f: A => List[B]): List[B] = a flatMap f
  }

  implicit class MonadOps[M[_]: Monad, A](a: M[A]) {
    def map[B](f: A => B) =
      Monad[M].map(a)(f)

    def flatMap[B](f: A => M[B]) =
      Monad[M].bind(a)(f)
      
    def filter(f: A => Boolean) = a
  }
}

object MonadLaws {
  def associative[F[_], A, B, C](fa: F[A], f: A => F[B], g: B => F[C])(implicit FC: Equal[F[C]], F: Monad[F]): Boolean =
    FC.equal(F.bind(F.bind(fa)(f))(g), F.bind(fa)((a: A) => F.bind(f(a))(g)))

  def rightIdentity[F[_], A](fa: F[A])(implicit FA: Equal[F[A]], F: Monad[F]): Boolean =
    FA.equal(F.bind(fa)(F.point[A](_)), fa)

  def leftIdentity[F[_], A, B](fa: A, f: A => F[B])(implicit FB: Equal[F[B]], F: Monad[F]): Boolean =
    FB.equal(F.bind(F.point(fa))(f), f(fa))
}
