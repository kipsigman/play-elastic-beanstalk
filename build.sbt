import com.typesafe.sbt.packager.docker._

name := """play-elastic-beanstalk"""

version := "0.1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

// Docker
maintainer in Docker := "Johnny Utah <johnny.utah@fbi.gov>"

dockerExposedPorts := Seq(9000)

dockerBaseImage := "java:latest"

// Elastic Beanstalk tasks
lazy val elasticBeanstalkStage = taskKey[Unit]("Create a local directory with all the files for an AWS Elastic Beanstalk Docker distribution.")

elasticBeanstalkStage := {
  // Depends on docker:stage
  val dockerStageValue = (stage in Docker).value
  
  // Copy Elastic Beanstalk Dockerrun.aws.json configuration file to Docker target directory
  val elasticBeanstalkSource = baseDirectory.value / "elastic-beanstalk"
  IO.copyDirectory(elasticBeanstalkSource, dockerStageValue, true)
}

lazy val elasticBeanstalkDist = taskKey[File]("Creates a zip for an AWS Elastic Beanstalk Docker distribution")

elasticBeanstalkDist := {
  val log = streams.value.log
  
  // Depends on elasticBeanstalkStage
  val stageValue = elasticBeanstalkStage.value
  
  // Zip Docker target
  val dockerStagingDirectory: File = (stagingDirectory in Docker).value
  
  val zipFile: File = (target.value) / s"${name.value}-${version.value}-elastic-beanstalk.zip"
  log.info(s"Zipping $dockerStagingDirectory to $zipFile")
  Process(s"zip -r $zipFile .", dockerStagingDirectory) !!
  
  zipFile
}
