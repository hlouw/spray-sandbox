package hlouw.spraysandbox

import com.spotify.docker.client.messages.{ContainerConfig, ContainerCreation, ContainerInfo, HostConfig}
import com.spotify.docker.client.{DefaultDockerClient, DockerClient}

trait DockerSupport {

  private val docker: DockerClient = DefaultDockerClient.fromEnv().build()

  def runContainer(): String = {
    val containerId = {
      val containerConfig: ContainerConfig = ContainerConfig.builder().image("hlouw/spray-sandbox").build()
      val creation: ContainerCreation = docker.createContainer(containerConfig)
      creation.id()
    }

    val hostConfig: HostConfig = HostConfig.builder().publishAllPorts(true).build()

    docker.startContainer(containerId, hostConfig)
    println("Started on port: " + hostPort(containerId, 8080))

    containerId
  }

  def shutdownContainer(containerId: String) = {
    docker.stopContainer(containerId, 2)
    docker.removeContainer(containerId)
  }

  def hostPort(containerId: String, internalPort: Int) = {
    val info: ContainerInfo = docker.inspectContainer(containerId)
    info.networkSettings().ports().get(s"$internalPort/tcp").get(0).hostPort()
  }

}
