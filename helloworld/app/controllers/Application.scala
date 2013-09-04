package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form._
import play.api.data.Form
import models.Task
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  def index = Action {
    Redirect(routes.Application.tasks)
  }

  val taskForm = Form(
    "label" -> nonEmptyText)

  def tasks = Action {
    Ok(views.html.index(Task.all(), taskForm))
  }
  
   def edit(id: Long) = Action { 
      Ok(views.html.edit(Task.getTask(id),taskForm))
    }
  
  
    def list = Action { 
      Ok(views.html.list(Task.all()))
    }
   
  def save(id:Long) = Action { implicit request =>
  taskForm.bindFromRequest.fold(
    errors => BadRequest(views.html.index(Task.all(), errors)),
    label => {
      Task.update(id,label)
      Redirect(routes.Application.tasks)
    }
  )
}
  

  def newTask = Action { implicit request =>
  taskForm.bindFromRequest.fold(
    errors => BadRequest(views.html.index(Task.all(), errors)),
    label => {
      Task.create(label)
      Redirect(routes.Application.tasks)
    }
  )
}

def deleteTask(id: Long) = Action {
  Task.delete(id)
  Redirect(routes.Application.tasks)
}

}