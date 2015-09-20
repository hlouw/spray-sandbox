package hlouw.spraysandbox

import org.specs2.mutable.Specification

class HelloFeatureSpec extends Specification with DockerSupport {

  sequential

  val containerId = runContainer()

  "Hello Feature" should {

    "return a greeting" in {
      // Use client to query running docker container
      success
    }
  }

  shutdownContainer(containerId)
}
