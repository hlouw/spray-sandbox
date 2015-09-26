import sbt._
import TestConfigs._

object Dependencies {

  // Versions
  val logbackV = "1.1.3"
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  val specs2V = "2.4.17"
  val json4sV = "3.2.11"
  val kamonV = "0.5.1"
  val aspectJweaverV = "1.8.2"
  val dockerClientV = "2.7.7"

  // Libraries
  val logback = "ch.qos.logback" % "logback-classic" % logbackV
  val sprayCan = "io.spray" %% "spray-can" % sprayV
  val sprayRouting = "io.spray" %%  "spray-routing" % sprayV
  val sprayHttpx = "io.spray" %%  "spray-httpx" % sprayV
  val sprayClient = "io.spray" %%  "spray-client" % sprayV
  val sprayTestkit = "io.spray" %%  "spray-testkit" % sprayV
  val json4sJackson = "org.json4s" %% "json4s-jackson" % json4sV
  val json4sNative = "org.json4s" %% "json4s-native" % json4sV
  val akkaActor = "com.typesafe.akka" %%  "akka-actor"    % akkaV
  val akkaSlf4j = "com.typesafe.akka" %%  "akka-slf4j"    % akkaV
  val akkaTestkit = "com.typesafe.akka" %%  "akka-testkit"  % akkaV
  val specs2core = "org.specs2" %%  "specs2-core"   % specs2V
  val dockerClient = "com.spotify" % "docker-client" % dockerClientV classifier "shaded"
  val kamonCore = "io.kamon" %% "kamon-core" % kamonV
  val kamonSpray = "io.kamon" %% "kamon-spray" % kamonV
  val kamonStatsD = "io.kamon" %% "kamon-statsd" % kamonV
  val kamonLogReporter = "io.kamon" %% "kamon-log-reporter" % kamonV
  val aspectJweaver = "org.aspectj" % "aspectjweaver" % aspectJweaverV

  // Projects
  val coreDeps = Seq(
    sprayCan, sprayRouting, sprayHttpx, akkaActor, akkaSlf4j, json4sJackson, logback
  )

  val kamonDeps = Seq(kamonCore, kamonSpray, kamonLogReporter, aspectJweaver)

  val serviceTestDeps = Seq(
    akkaTestkit % Test, sprayTestkit % Test, specs2core % Test,
    sprayClient % ServiceTest, specs2core % ServiceTest, dockerClient % ServiceTest
  )

  val serviceDeps = coreDeps ++ kamonDeps ++ serviceTestDeps

  val clientDeps = coreDeps ++ Seq(
    sprayClient
  )

}