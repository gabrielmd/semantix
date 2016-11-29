import sbt.Defaults.coreDefaultSettings
import sbt.Keys._
import sbt._

object RestAPIBuild extends Build {

  lazy val main = Project(id = "rest-api", base = file("."), settings = buildSettings)

  val buildSettings = coreDefaultSettings ++ Seq(
    organization := "br.com.semantix.restapi",
    version := "1.0-SNAPSHOT",

    scalaVersion := "2.11.7",

    libraryDependencies ++= {
      val akkaV = "2.4.0"
      val sprayV = "1.3.2"

      Seq(
        "com.typesafe.akka" %% "akka-actor" % akkaV,
        "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",

        "io.spray" %% "spray-can" % sprayV,
        "io.spray" %% "spray-client" % sprayV,
        "io.spray" %% "spray-http" % sprayV,
        "io.spray" %% "spray-json" % sprayV,
        "io.spray" %% "spray-routing" % sprayV,
        "io.spray" %% "spray-testkit" % sprayV % "test",

        "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
        "org.specs2" % "specs2_2.11" % "2.4.15" % "test"
      )
    },

    resolvers ++= Seq(
      "Typesafe repo" at "http://repo.typesafe.com/typesafe/releases/",
      "Spray repo" at "http://repo.spray.io",
      "Scalaz repo" at "http://dl.bintray.com/scalaz/releases")
  )
}