package controllers

import akka.util.ByteString

import javax.inject._
import play.api.mvc._
import play.api.http.HttpEntity
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def echo = Action { request =>
    Ok("Got request [" + request + "]")
  }

  //It is often useful to mark the request parameter as implicit so it can be implicitly used by other APIs that need it
  //also using methods
  def action = Action { implicit request =>
    println("Called action called action");
    anotherMethod("Some para value")
    Ok("Got request [" + request + "]")
  }
  def anotherMethod(p: String)(implicit request: Request[_]) = {
    println("Called method inside action");
  }
  //action with parameter
  def hello(name: String) = Action {
    Ok("Hello " + name)
  }

  //same as helper method Ok
  def ok = Action {
    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello world!"), Some("text/plain"))
    )
  }

//  val ok           = Ok("Hello world!")
//  val notFound     = NotFound
//  val pageNotFound = NotFound(<h1>Page not found</h1>)
//  val badRequest   = BadRequest(views.html.form(formWithErrors))
//  val oops         = InternalServerError("Oops")
//  val anyStatus    = Status(488)("Strange response type")

  def aRedirect = Action {
    Redirect("/test5")
  }
  def redirected = Action { request =>
    Ok("I got Redirected [" + request + "]")
  }

}
