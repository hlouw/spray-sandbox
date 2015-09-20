package hlouw.spraysandbox

import akka.actor.Actor
import hlouw.spraysandbox.resources.SandboxResource

class SandboxServiceActor extends Actor with SandboxResource {

  implicit def actorRefFactory = context

  def receive = runRoute(sandboxRoutes)

}
