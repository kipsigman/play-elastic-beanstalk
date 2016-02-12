import com.typesafe.sbt.packager.docker._

name := """play-elastic-beanstalk"""

version := "0.1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala,ElasticBeanstalkPlugin,BuildInfoPlugin)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  specs2 % Test
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

// Docker/Elastic Beanstalk
maintainer in Docker := "Johnny Utah <johnny.utah@fbi.gov>"
dockerExposedPorts := Seq(9000)
dockerBaseImage := "java:latest"

// BuildInfoPlugin
buildInfoPackage := "build"