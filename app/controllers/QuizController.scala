package controllers

import models.QuizResult
import play.api.libs.json.JsResult
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class QuizController @Inject()(ws: WSClient, cc: ControllerComponents)
  extends AbstractController(cc) {

  def getSingleResultAsObject: Future[JsResult[QuizResult]] = ws.url("https://opentdb.com/api.php?amount=10").get().map { response =>
    //    println(response.json)
    //    println((response.json).validate[QuizResult])
    (response.json).validate[QuizResult]
  }
  def quiz = Action.async {
    // send the string in the body of a 200 response
    getSingleResultAsObject.map(str => {
      println(str)
      val test = str.get
      println(str.get)
      Ok(views.html.quiz(test.quizResults))
    })
  }

}
