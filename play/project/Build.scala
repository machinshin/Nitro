import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "Nitro"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    jdbc,
    "mysql" % "mysql-connector-java" % "5.1.25"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += "Typesafe outside repo" at "http://repo.typesafe.com/typesafe/repo/"
    // Add your own project settings here
  )

}
