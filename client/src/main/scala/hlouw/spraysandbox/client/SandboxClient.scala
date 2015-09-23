package hlouw.spraysandbox.client

import akka.actor.ActorSystem
import hlouw.spraysandbox.Hello
import org.json4s.{DefaultFormats, Formats}
import spray.client.pipelining._
import spray.httpx.Json4sJacksonSupport

import scala.concurrent.Future

class SandboxClient(baseUrl: String)(implicit system: ActorSystem) extends Json4sJacksonSupport {

  import system.dispatcher

  implicit def json4sJacksonFormats: Formats = DefaultFormats

  def hello(): Future[Hello] = {
    val pipeline = sendReceive ~> unmarshal[Hello]

    pipeline {
      Get(s"$baseUrl/hello")
    }
  }

}
