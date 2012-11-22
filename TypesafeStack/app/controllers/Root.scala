package controllers

import play.api._
import play.api.mvc._

object Root extends Controller {
  
  def index = Action {
    Redirect("/logs")
  }
  
}
