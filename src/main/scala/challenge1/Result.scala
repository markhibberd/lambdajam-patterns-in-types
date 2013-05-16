package challenge1

import challenge0._, EqualSyntax._


/*
 * A result type that represents either an exception or an error message or a value.
 */
sealed trait Result[A] {

  /*
   * Exercise 1.1:
   *
   * Implement the catamorphism for Result[A].
   *
   * Hint: Try using pattern matching.
   */
  def fold[X](
    explosion: Throwable => X,
    fail: String => X,
    ok: A => X
  ): X = ???

  /*
   * Exercise 1.2:
   *
   * Implement map for Result[A].
   *
   * The following laws must hold:
   *  1) r.map(z => z) == r
   *  2) r.map(z => f(g(z))) == r.map(g).map(f)
   *
   * Hint: Try using flatMap.
   */
  def map[B](f: A => B): Result[B] =
    ???


  /*
   * Exercise 1.3:
   *
   * Implement flatMap (a.k.a. bind, a.k.a. >>=).
   *
   * The following law must hold:
   *   r.flatMap(f).flatMap(g) == r.flatMap(z => f(z).flatMap(g))
   *
   * Hint: Try using fold.
   */
  def flatMap[B](f: A => Result[B]): Result[B] =
    ???
}

case class Explosion[A](exception: Throwable) extends Result[A]
case class Fail[A](message: String) extends Result[A]
case class Ok[A](value: A) extends Result[A]

object Result {
  def explosion[A](exception: Throwable): Result[A] =
    Explosion(exception)

  def fail[A](message: String): Result[A] =
    Fail(message)

  def ok[A](value: A): Result[A] =
    Ok(value)

  implicit def ResultMonad: Monad[Result] = new Monad[Result] {
    def point[A](a: => A) = ok(a)
    def bind[A, B](a: Result[A])(f: A => Result[B]) = a flatMap f
  }

  implicit def ResultEqual[A: Equal] =
    Equal.from[Result[A]]((a, b) => a.fold(
      t => b.fold(_ === t, _ => false, _ => false),
      m => b.fold(_ => false, _ === m, _ => false),
      a => b.fold(_ => false, _ => false, _ === a)
    ))
}
