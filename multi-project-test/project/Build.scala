import sbt._
import Keys._

object MultiProjectTestBuild extends Build {
  lazy val root = Project(id = "multi-project-test", base = file(".")) settings (ScctPlugin.mergeReportSettings: _*) aggregate(first, second, third)

  lazy val first = Project(id = "first", base = file("first")) settings (externalDeps :_*) settings (ScctPlugin.instrumentSettings: _*)
  lazy val second = Project(id = "second", base = file("second")) settings (ScctPlugin.instrumentSettings: _*) dependsOn(first)
  lazy val third = Project(id = "third", base = file("third")) aggregate(grandchild)
  lazy val grandchild = Project(id = "grandchild", base = file("third/grand-child")) settings (ScctPlugin.instrumentSettings: _*) dependsOn(second)

  override lazy val settings = super.settings ++ Seq(
    organization := "reaktor.scct",
    scalaVersion := "2.10.0-RC3",
    resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.10" % "test",
      "org.specs2" %% "specs2" % "1.12.3" % "test"
    )
  )

  lazy val externalDeps = Seq(
    libraryDependencies += "reaktor.scct" %% "simple-test" % "1.0"
  )
}