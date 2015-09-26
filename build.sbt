import Dependencies._
import TestConfigs._
import ReleaseTransformations._
import sbt.Keys._
import spray.revolver.RevolverPlugin.Revolver

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
  .settings(name := "spray-sandbox-client")
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= clientDeps)

lazy val service = project
  .dependsOn(entities, client % ServiceTest)
  .configs(ServiceTest)
  .settings(commonSettings: _*)
  .settings(ServiceTestSettings: _*)
  .settings(libraryDependencies ++= serviceDeps)
  .settings(Revolver.settings)
  .settings(kamonSettings: _*)
  .enablePlugins(JavaServerAppPackaging, DockerPlugin)
  .settings(dockerSettings: _*)

lazy val dockerSettings = Seq(
  packageName in Docker := "spray-sandbox",
  dockerRepository := Some("hlouw"),
  dockerUpdateLatest := true,
  dockerExposedPorts := Seq(8080, 8081),
  bashScriptExtraDefines += """addJava "-javaagent:${lib_dir}/org.aspectj.aspectjweaver-""" + aspectJweaverV + """.jar"""",
  bashScriptExtraDefines += """addJava "-Dkamon.auto-start=true""""
)

lazy val kamonSettings = aspectjSettings ++ Seq(
  javaOptions in run <++= AspectjKeys.weaverOptions in Aspectj,
  javaOptions in run += "-Dkamon.auto-start=true",
  javaOptions in Revolver.reStart <++= AspectjKeys.weaverOptions in Aspectj,
  javaOptions in Revolver.reStart += "-Dkamon.auto-start=true",
  fork in run := true
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