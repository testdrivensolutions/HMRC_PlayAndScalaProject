package controllers

import models.QuizResult
import play.api.libs.json.JsResult
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ViewTemplateController @Inject()(ws: WSClient, cc: ControllerComponents)
  extends AbstractController(cc) {

  def index2 = Action { implicit request =>
    val articles = List(
      ("Introduction to Play Framework", "https://www.baeldung.com/scala/play-framework-intro"),
      ("Building REST API in Scala with Play Framework", "https://www.baeldung.com/scala/play-rest-api")
    )
    Ok(views.html.index2(articles))
  }

  def getSingleResultAsObject: Future[JsResult[QuizResult]] = ws.url("https://opentdb.com/api.php?amount=10").get().map { response =>
//    println(response.json)
//    println((response.json).validate[QuizResult])
    (response.json).validate[QuizResult]
  }
  def index3 = Action.async {
    // send the string in the body of a 200 response
    getSingleResultAsObject.map(str => {
      println(str)
      val test = str.get
      println(str.get)
      Ok(views.html.index3(test.quizResults))
    })
  }

}
