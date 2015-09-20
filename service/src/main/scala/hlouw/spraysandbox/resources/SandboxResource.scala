package hlouw.spraysandbox.resources

import hlouw.spraysandbox.Hello
import spray.routing.HttpService

trait SandboxResource extends HttpService with JsonSupport {

  val sandboxRoutes =
    path("hello") {
      get {
        complete(Hello("hi there"))
      }
    }

}
