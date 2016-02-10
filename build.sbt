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
lazy val elasticBeanstalkStage = taskKey[File]("Create a local directory with all the files for an AWS Elastic Beanstalk Docker distribution.")

elasticBeanstalkStage := {
  val log = streams.value.log
  
  // Depends on docker:stage. Re-use docker:stagingDirectory for this task
  val elasticBeanstalkStagingDirectory = (stage in Docker).value
  log.info(s"elasticBeanstalkStagingDirectory=${elasticBeanstalkStagingDirectory}")
  
  // Copy Elastic Beanstalk Dockerrun.aws.json configuration file to Docker target directory
  val elasticBeanstalkSource = baseDirectory.value / "elastic-beanstalk"
  IO.copyDirectory(elasticBeanstalkSource, elasticBeanstalkStagingDirectory, true)
  elasticBeanstalkStagingDirectory
}

lazy val elasticBeanstalkDist = taskKey[File]("Creates a zip for an AWS Elastic Beanstalk Docker distribution")

elasticBeanstalkDist := {
  val log = streams.value.log
  
  // Depends on elasticBeanstalkStage
  val elasticBeanstalkStagingDirectory = elasticBeanstalkStage.value
  log.info(s"elasticBeanstalkStage.value=${elasticBeanstalkStagingDirectory}")
  
  // Create target
  val elasticBeanstalkTarget = target.value / "elastic-beanstalk"
  IO.createDirectory(elasticBeanstalkTarget)
  log.info(s"elasticBeanstalkTarget=${elasticBeanstalkTarget}")
  
  // Zip staging contents
  val zipFile: File = elasticBeanstalkTarget / s"${name.value}-${version.value}.zip"
  log.info(s"Zipping $elasticBeanstalkStagingDirectory to $zipFile")
  Process(s"zip -r $zipFile .", elasticBeanstalkStagingDirectory) !!
  
  zipFile
}