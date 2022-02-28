package controllers

import models.QuizResult
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, JsResult, JsValue, Json, Reads, Writes}
import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

@Singleton
class WebServiceController @Inject()(ws: WSClient, val controllerComponents: ControllerComponents) extends BaseController{
  def echo = Action { request =>
    val futureResult: Future[JsValue] = ws.url("https://opentdb.com/api.php?amount=10").get().map { response =>
      response.json
    }

    futureResult.onComplete({
      case Success(jsValue) => {
        println("SUCCESS")
        println(jsValue)
        println(futureResult)
      }
      case Failure(exception) => {
        println("FAILURE")
        println("something went wrong " + exception)
      }
    })

    Ok("Got request in Web Service  [" + request + "]")
  }

  //https://stackoverflow.com/questions/33755111/return-the-page-only-when-future-calculation-is-finished 26/02/2022
  //Let's say you have a function in your service that returns a Future containing a String:
  def getSingleResult: Future[JsValue] = ws.url("https://opentdb.com/api.php?amount=10").get().map { response =>
    response.json
  }

  def singleSource = Action.async {
    // send the string in the body of a 200 response
    getSingleResult.map(str => Ok(str))
  }


  def getSingleResultAsObject: Future[JsResult[QuizResult]] = ws.url("https://opentdb.com/api.php?amount=10").get().map { response =>
    println(response.json)
    println((response.json).validate[QuizResult])
    (response.json).validate[QuizResult]
  }
  def singleSourceAsObject = Action.async {
    // send the string in the body of a 200 response
    getSingleResultAsObject.map(str => {
      Ok(str.toString)
    })
  }
}


