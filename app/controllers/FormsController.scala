package controllers

import models.{QuizData, UserData}
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import javax.inject.Inject
import play.api.data.Form
import play.api.data.Forms._

class FormsController @Inject()(ws: WSClient, cc: ControllerComponents) extends AbstractController(cc)
                                with play.api.i18n.I18nSupport{

  val quizSetupForm = Form(
    mapping(
      "numberOfQuestions" -> number,
      "category" -> text,
      "difficulty" -> text,
      "qType" -> text,
      "encoding" -> text
    )(QuizData.apply)(QuizData.unapply)
  )

  //remove this if you dont use it
  def quizSetup() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.quizSetup(quizSetupForm))
  }

  val quizSetupPost = Action(parse.form(quizSetupForm)) { implicit request =>
    println("THIS WORKED")
    val quizSetupData = request.body
    println(quizSetupData)
    Ok(views.html.quizSetup(quizSetupForm))
  }


  val userForm = Form(
    mapping(
      "name" -> text,
      "age"  -> number
    )(UserData.apply)(UserData.unapply)
  )

  def index = Action { implicit request =>
    Ok(views.html.user(userForm))
  }

  val userPost = Action(parse.form(userForm)) { implicit request =>
    println("THIS WORKED")
    val userData = request.body
    println(userData)
    val newUser  = models.User(userData.name, userData.age)
    Ok(views.html.user(userForm))
  }
}
