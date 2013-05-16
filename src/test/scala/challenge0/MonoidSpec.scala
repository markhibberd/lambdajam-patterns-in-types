package challenge0

object MonoidSpec extends test.Spec {
  import Laws._

  "StringMonoid" should {
    "satisfy laws" ! monoid.laws[String]
  }

  "ListMonoid" should {
    "satisfy laws" ! monoid.laws[List[Int]]
  }
}
