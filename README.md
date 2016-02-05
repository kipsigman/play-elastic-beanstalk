Amazon Web Services Elastic Beanstalk sample app
=================================

Play 2.4 seed application with build configuration and instructions for deploying to Amazon Web Services Elastic Beanstalk.

## Prepare Elastic Beanstalk application/environment

1. Create an AWS account: https://aws.amazon.com
2. Select "Elastic Beanstalk" from services: https://console.aws.amazon.com/elasticbeanstalk/home
3. Click "Create New Application"
  1. Application Info
    1. name/description: Choose whatever you want, i.e. "play-aws-elastic-beanstalk"
  2. New Environment: Web Server Environment
  3. Environment Type
    - Predefined Configuration: Generic -> Docker
    - Environment type: Single instance
  3. Application Version: Sample application
  4. Environment Information
    - Environment name: Choose a unique name, i.e. "play-aws-eb-<myname>-env"
    - Environment URL: Will be auto created
  5. Additional Resources
    - Create an RDS DB instance: check
  6. Configuration Details: Use defaults
  7. Environment Tags: none
  8. Permissions: Use defaults
  9. Review Information: Click "Launch"
   
## Deploying

1. Build dist
  1. sbt elasticBeanstalkDist
2. Select application/environment from AWS Elastic Beanstalk console
3. Click "Upload and Deploy"
  - Choose file: <yourprojectdir>/target/play-aws-elastic-beanstalk-<version>-elastic-beanstalk.zip
  - Version label: Use default or trim to app version number (0.1.0)
4. Configure for production
  1. Select "Configuration" from your environment in the Elastic Beanstalk console
  2. Select "Software Configuration" and in Environment Properties add this property:
    - Property Name: JAVA_OPTS
    - Property Value: -Dconfig.resource=production.conf
5. View your app with the environment URL provided in the Elastic Beanstalk console


## Continuous Deployment
TODO: Define steps for setting up continuous deployment with Github and AWS

Temp workaround: follow this blog post (though it is a bit out of date): https://www.cloudbees.com/blog/migrating-play2-and-other-apps-aws-beanstalk-docker