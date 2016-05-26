import com.typesafe.sbt.packager.docker._

name := """play-elastic-beanstalk"""

version := "0.2.0"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala,ElasticBeanstalkPlugin,BuildInfoPlugin)

libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

// Docker/Elastic Beanstalk
maintainer in Docker := "Johnny Utah <johnny.utah@fbi.gov>"
dockerExposedPorts := Seq(9000)
dockerBaseImage := "java:latest"

// BuildInfoPlugin
buildInfoPackage := "build"