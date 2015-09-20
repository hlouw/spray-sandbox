import Dependencies._
import TestConfigs._
import ReleaseTransformations._

lazy val commonSettings = Seq(
  scalaVersion := "2.11.7"
)

lazy val root = (project in file("."))
  .settings(name := "spray-sandbox")
  .settings(commonSettings: _*)
  .aggregate(entities, client, service)

lazy val entities = project
  .settings(commonSettings: _*)

lazy val client = project
  .dependsOn(entities)
  .settings(commonSettings: _*)

lazy val service = project
  .dependsOn(entities)
  .configs(ServiceTest)
  .settings(commonSettings: _*)
  .settings(ServiceTestSettings: _*)
  .settings(libraryDependencies ++= serviceDeps)
  .settings(Revolver.settings)
  .enablePlugins(JavaServerAppPackaging, DockerPlugin)
  .settings(dockerSettings: _*)

lazy val dockerSettings = Seq(
  packageName in Docker := "spray-sandbox",
  dockerRepository := Some("hlouw"),
  dockerUpdateLatest := true,
  dockerExposedPorts := Seq(8080, 8081)
)

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepTask(publishLocal in Docker in service),
  setNextVersion,
  commitNextVersion,
  pushChanges
)