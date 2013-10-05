package challenge3

import challenge0._, EqualSyntax._

/*
 * A writer data type that represents the pair of some
 * writer content with the production of a value.
 */
case class Writer[W, A](log: W, value: A) {

  /*
   * Exercise 3.1:
   *
   * Implement map for Writer[W, A].
   *
   * The following laws must hold:
   *  1) r.map(z => z) == r
   *  2) r.map(z => f(g(z))) == r.map(g).map(f)
   *
   */
  def map[B](f: A => B): Writer[W, B] =
    ???

  /*
   * Exercise 3.2:
   *
   * Implement flatMap (a.k.a. bind, a.k.a. >>=).
   *
   * The following law must hold:
   *   r.flatMap(f).flatMap(g) == r.flatMap(z => f(z).flatMap(g))
   *
   */
  def flatMap[B](f: A => Writer[W, B])(implicit M: Monoid[W]): Writer[W, B] =
    ???
}

object Writer {
  /*
   * Exercise 3.3:
   *
   * Implement value  (a.k.a. return, point, pure) given a
   * Monoid for W.
   *
   * Hint: Try using Writer constructor.
   */
  def value[W: Monoid, A](a: A): Writer[W, A] =
    ???

  /*
   * Exercise 3.4:
   *
   * Implement tell.
   *
   * Tell appends the writer content w and produces no value.
   *
   * Hint: Try using Writer constructor.
   */
  def tell[W](w: W): Writer[W, Unit] =
    ???

  def writer[W, A](a: A)(w: W): Writer[W, A] =
    Writer(w, a)

  class Writer_[W] {
    type l[a] = Writer[W, a]
  }

  implicit def WriterMonad[W: Monoid]: Monad[Writer_[W]#l] =
    new Monad[Writer_[W]#l] {
      def point[A](a: => A) = value[W, A](a)
      def bind[A, B](a: Writer[W, A])(f: A => Writer[W, B]) = a flatMap f
    }

  implicit def WriterEqual[W: Equal, A: Equal] =
    Equal.from[Writer[W, A]]((a, b) => (a.log, a.value)  === (b.log, b.value))

  implicit def WriterMoniod[W: Monoid, A: Monoid]: Monoid[Writer[W, A]] =
    new Monoid[Writer[W, A]] {
      def zero = ???
      def append(l: Writer[W, A], r: => Writer[W, A]) = ???
    }
}
