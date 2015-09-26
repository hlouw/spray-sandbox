package hlouw.spraysandbox.resources

import hlouw.spraysandbox.Hello
import kamon.spray.KamonTraceDirectives
import spray.routing.HttpService

trait SandboxResource extends HttpService with JsonSupport with KamonTraceDirectives {

  val sandboxRoutes =
    path("hello") {
      get {
        traceName("HelloWorld") {
          complete(Hello("hi there"))
        }
      }
    }

}
