package hlouw.spraysandbox

import akka.actor.ActorSystem
import hlouw.spraysandbox.client.SandboxClient
import org.specs2.mutable.Specification

class HelloFeatureSpec extends Specification with DockerSupport {

  var containerId: Option[String] = None

  lazy val client = {
    implicit val system: ActorSystem = ActorSystem("service-test")
    val baseUrl = s"http://${hostIP(containerId.get)}:${hostPort(containerId.get, 8080)}"
    println(baseUrl)
    new SandboxClient(baseUrl)
  }

  sequential

  "Docker startup" should {
    "create a container" in {
      containerId = Some(runContainer())
      success
    }
  }

  "Hello Feature" should {

    "return a greeting" in {
      client.hello() must beEqualTo(Hello("hi")).await
    }
  }

  "Docker cleanup" should {
    "stop and remove container" in {
      containerId.foreach(shutdownContainer)
      success
    }
  }
}
