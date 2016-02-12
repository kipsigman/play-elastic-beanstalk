package controllers

import javax.inject.Inject
import javax.inject.Singleton

import play.api._
import play.api.mvc._

@Singleton
class Application @Inject() (environment: Environment, configuration: Configuration) extends Controller {

  def index = Action {
    val title = build.BuildInfo.name
    val buildInfo: Seq[String] = build.BuildInfo.toString.split(",").map(_.trim()).sorted
    Ok(views.html.index(title, configuration, buildInfo))
  }
}
