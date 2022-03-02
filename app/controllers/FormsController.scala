package controllers

import models.QuizData
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

  def quizSetup() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.quizSetup(quizSetupForm))
  }

  val quizSetupPost = Action(parse.form(quizSetupForm)) { implicit request =>
    println("THIS WORKED")
    val quizSetupData = request.body
    println(quizSetupData)
    Ok(views.html.quizSetup(quizSetupForm))
  }

}
