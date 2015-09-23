package hlouw.spraysandbox

import com.spotify.docker.client.messages.{ContainerConfig, HostConfig}
import com.spotify.docker.client.{DefaultDockerClient, DockerClient}

trait DockerSupport {

  private val docker: DockerClient = DefaultDockerClient.fromEnv().build()

  def runContainer(): String = {

    val containerId = {
      val containerConfig = ContainerConfig.builder().image("hlouw/spray-sandbox").build()
      val creation = docker.createContainer(containerConfig)
      creation.id()
    }

    val hostConfig: HostConfig = HostConfig.builder().publishAllPorts(true).build()

    docker.startContainer(containerId, hostConfig)

    containerId
  }

  def shutdownContainer(containerId: String): Unit = {
    docker.stopContainer(containerId, 2)
    docker.removeContainer(containerId)
  }

  def hostPort(containerId: String, internalPort: Int): String = {
    val info = docker.inspectContainer(containerId)
    info.networkSettings().ports().get(s"$internalPort/tcp").get(0).hostPort()
  }

  def hostIP(containerId: String): String = "192.168.99.100"

}
