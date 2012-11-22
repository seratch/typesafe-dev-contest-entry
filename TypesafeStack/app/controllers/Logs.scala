package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import play.api.Play.current

import akka.actor._
import akka.actor.Actor._
import play.api.libs.concurrent.{Akka => PlayAkka}

import models._

object Logs extends Controller {
  
  def index = Action {
    val logs = Log.findRecent()
    Ok(views.html.logs.index(logs))
  }

  val createLogForm = Form(mapping(
    "name" -> text.verifying(nonEmpty),
    "line" -> text.verifying(nonEmpty)
  )(CreateLogForm.apply)(CreateLogForm.unapply))

  def create = Action { implicit request => 
    createLogForm.bindFromRequest.fold(
      formWithErrors => BadRequest,
      log => { 
        Log.create(name = log.name, line = log.line)
        Ok
      }
    )
  }

  lazy val logActor = PlayAkka.system.actorOf(Props[LogActor])

  def asyncCreate = Action { implicit request =>
    createLogForm.bindFromRequest.fold(
      formWithErrors => BadRequest,
      log => {
        logActor ! log
        Accepted
      }
    )
  }
 
}

case class CreateLogForm(name: String, line: String)

class LogActor extends Actor {
  def receive = {
    case CreateLogForm(name, line) => Log.create(name, line)
  }
}

