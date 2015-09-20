package hlouw.spraysandbox.resources

import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sJacksonSupport

trait JsonSupport extends Json4sJacksonSupport {

  implicit def json4sJacksonFormats: Formats = DefaultFormats

}
