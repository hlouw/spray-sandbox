import Dependencies._

lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := "2.11.7"
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(name := "spray-sandbox")
  .aggregate(entities, client, service)

lazy val entities = project.settings(commonSettings: _*)

lazy val client = project.dependsOn(entities).settings(commonSettings: _*)

lazy val service = project.dependsOn(entities)
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= serviceDeps)
  .settings(Revolver.settings)