
import scalaz._, Scalaz._, Tags.Conjunction

object Scratch extends App {

  val b: Boolean = List(true, false, true).map(Conjunction).suml

  println(b)

}
