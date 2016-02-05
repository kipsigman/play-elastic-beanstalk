package controllers

import javax.inject.Inject
import javax.inject.Singleton

import play.api._
import play.api.mvc._

@Singleton
class Application @Inject() (environment: Environment, configuration: Configuration) extends Controller {

  def index = Action {
    val title = configuration.getString("application.name").get
    Ok(views.html.index(title, configuration))
  }
}
