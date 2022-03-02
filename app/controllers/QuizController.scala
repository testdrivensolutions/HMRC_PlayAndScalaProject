package controllers

import models.{QuizData, QuizResult}
import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}
import play.api.i18n.I18nSupport
import play.api.libs.json.JsResult
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class QuizController @Inject()(ws: WSClient, cc: ControllerComponents)
  extends AbstractController(cc) with I18nSupport{

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

  def getSingleResultAsObject: Future[JsResult[QuizResult]] = ws.url("https://opentdb.com/api.php?amount=10").get().map { response =>
    (response.json).validate[QuizResult]
  }
  def quiz = Action.async {
    // send the string in the body of a 200 response
    getSingleResultAsObject.map(str => {
      val result = str.get
      Ok(views.html.quiz(result.quizResults))
    })
  }

}
