import sbt._
import TestConfigs._

object Dependencies {

  // Versions
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  val specs2V = "2.4.17"
  val json4sV = "3.2.11"

  // Libraries
  val sprayCan = "io.spray" %% "spray-can" % sprayV
  val sprayRouting = "io.spray" %%  "spray-routing" % sprayV
  val sprayHttpx = "io.spray" %%  "spray-httpx" % sprayV
  val sprayTestkit = "io.spray" %%  "spray-testkit" % sprayV
  val json4sJackson = "org.json4s" %% "json4s-jackson" % json4sV
  val json4sNative = "org.json4s" %% "json4s-native" % json4sV
  val akkaActor = "com.typesafe.akka" %%  "akka-actor"    % akkaV
  val akkaTestkit = "com.typesafe.akka" %%  "akka-testkit"  % akkaV
  val specs2core = "org.specs2" %%  "specs2-core"   % specs2V

  // Projects
  val serviceDeps = Seq(
    sprayCan, sprayRouting, sprayHttpx, akkaActor, json4sJackson,
    akkaTestkit % Test, sprayTestkit % Test, specs2core % Test,
    specs2core % ServiceTest
  )

}