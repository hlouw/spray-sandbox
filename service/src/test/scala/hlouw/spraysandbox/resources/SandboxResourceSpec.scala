package hlouw.spraysandbox.resources

import hlouw.spraysandbox.Hello
import org.specs2.mutable.Specification
import spray.http.StatusCodes
import spray.testkit.Specs2RouteTest

class SandboxResourceSpec extends Specification with Specs2RouteTest with SandboxResource {

  def actorRefFactory = system

  "Sandbox Resource" in {

    "Hello endpoint" should {

      "return 'hi there' as response" in {
        Get("/hello") ~> sandboxRoutes ~> check {
          status === StatusCodes.OK
          responseAs[Hello] must beEqualTo(Hello("hi there"))
        }
      }
    }

  }
}
