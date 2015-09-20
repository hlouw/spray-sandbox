import Dependencies._
import ReleaseTransformations._

lazy val commonSettings = Seq(
  scalaVersion := "2.11.7"
)

lazy val dockerSettings = Seq(
  packageName in Docker := "spray-sandbox",
  dockerRepository := Some("hlouw"),
  dockerUpdateLatest := true,
  dockerExposedPorts := Seq(8080, 8081)
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
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= serviceDeps)
  .settings(Revolver.settings)
  .enablePlugins(JavaServerAppPackaging, DockerPlugin)
  .settings(dockerSettings: _*)

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
//  publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)