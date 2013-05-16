package challenge0

object MonadSpec extends test.Spec {
  import Laws._

  "OptionMonad" should {
    "satisfy laws" ! monad.laws[Option]
  }

  "ListMonad" should {
    "satisfy laws" ! monad.laws[List]
  }
}
