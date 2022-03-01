package controllers

import models.{QuizResult}
import play.api.i18n.I18nSupport
import play.api.libs.json.JsResult
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class QuizController @Inject()(ws: WSClient, cc: ControllerComponents)
  extends AbstractController(cc) with I18nSupport{

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
