import sbt._

object TestConfigs {

  lazy val ServiceTest = config("svctest") extend(Test)

  lazy val ServiceTestSettings = inConfig(ServiceTest)(Defaults.testSettings)

}