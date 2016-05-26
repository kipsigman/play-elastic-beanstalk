Play Elastic Beanstalk
=================================

Play 2.5 application with build configuration and instructions for deploying to AWS (Amazon Web Services) Elastic Beanstalk.

## Features

- Seed for Play/Scala app
- Usage of [SBT Elastic Beanstalk plugin](https://github.com/kipsigman/sbt-elastic-beanstalk)
- Usage of [SBT Native Packager Docker plugin](http://www.scala-sbt.org/sbt-native-packager/formats/docker.html)
- Usage of [sbt-buildinfo plugin](https://github.com/sbt/sbt-buildinfo)
- Configuration for connecting to RDS 

## Deployment to AWS Elastic Beanstalk

### Prepare Elastic Beanstalk application/environment

1. Create an AWS account: https://aws.amazon.com
2. Select "Elastic Beanstalk" from services: https://console.aws.amazon.com/elasticbeanstalk/home
3. Click "Create New Application"
  1. Application Info
    - name/description: Choose whatever you want, i.e. "play-elastic-beanstalk"
  2. New Environment: Web Server Environment
  3. Environment Type
    - Predefined Configuration: Generic -> Docker
    - Environment type: Single instance
  3. Application Version: Sample application
  4. Environment Information
    - Environment name: Choose a unique name, i.e. "play-eb-<myname>-env"
    - Environment URL: Will be auto created
  5. Additional Resources
    - Create an RDS DB instance: check
  6. Configuration Details: Use defaults
  7. Environment Tags: none
  8. Permissions: Use defaults
  9. Review Information: Click "Launch"
   
### Build distribution

```sh
sbt elastic-beanstalk:dist
```
Note output which indicates package location

### Deploy with Elastic Beanstalk console
1. Select application/environment from AWS Elastic Beanstalk console
2. Click "Upload and Deploy"
  - Choose file (see Build distribtion), i.e. <projectdir>/target/elastic-beanstalk/play-elastic-beanstalk-0.2.0.zip
  - Version label: Use default or trim to app version number (0.2.0)
3. Configure for production
  1. Select "Configuration" from your environment in the Elastic Beanstalk console
  2. Select "Software Configuration" and in Environment Properties add this property:
    - Property Name: JAVA_OPTS
    - Property Value: -Dconfig.resource=production.conf

### View your app
View your app with the environment URL provided in the Elastic Beanstalk console. The homepage will show the following:

- Environment (should be production)
- Build info
- If you set up an RDS database, the environment variables for connecting to it

## Continuous Deployment

How to set up continuous deployment to Elastic Beanstalk using Github and Jenkins.

NOTE: Coming soon. In the meantime follow this [blog post](https://www.cloudbees.com/blog/migrating-play2-and-other-apps-aws-beanstalk-docker), note that it may be out of date.

## Typesafe Activator Template

https://www.typesafe.com/activator/template/play-elastic-beanstalk