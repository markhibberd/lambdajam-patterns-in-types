package challenge0

object EqualSpec extends test.Spec {
  import Laws._

  "ThrowableEqual" should {
    "satisfy laws" ! equal.laws[Throwable]
  }

  "StringEqual" should {
    "satisfy laws" ! equal.laws[String]
  }

  "IntEqual" should {
    "satisfy laws" ! equal.laws[Int]
  }

  "OptionEqual" should {
    "satisfy laws with Int" ! equal.laws[Option[Int]]
    "satisfy laws with String" ! equal.laws[Option[String]]
  }

  "ListEqual" should {
    "satisfy laws with Int" ! equal.laws[List[Int]]
    "satisfy laws with String" ! equal.laws[List[String]]
  }

  "Tuple2Equal" should {
    "satisfy laws" ! equal.laws[(Int, String)]
  }

}
