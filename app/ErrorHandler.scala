import javax.inject.Singleton

import controllers.routes
import play.api.http.HttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc.{RequestHeader, Result}

import scala.concurrent.Future

@Singleton
class ErrorHandler extends HttpErrorHandler {

  val NOT_FOUND = 404
  val BAD_REQUEST = 400
  val FORBIDDEN = 403
  val PROXY_AUTHENTICATION_REQUIRED = 407

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {

    statusCode match {

      case NOT_FOUND                     => Future.successful(Redirect(routes.SessionsController.sessions(1, None)).flashing("error" -> "Page not found!"))
      case BAD_REQUEST                   => Future.successful(Redirect(routes.SessionsController.sessions(1, None)).flashing("error" -> "Bad Request!"))
      case FORBIDDEN                     => Future.successful(Redirect(routes.SessionsController.sessions(1, None)).flashing("error" -> "Forbidden Area!"))
      case PROXY_AUTHENTICATION_REQUIRED => Future.successful(Redirect(routes.SessionsController.sessions(1, None))
        .flashing("error" -> "Proxy Authentication Required!"))
      case _                             => Future.successful(Redirect(routes.SessionsController.sessions(1, None))
        .flashing("error" -> "Something went wrong!"))
    }
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {

    Future.successful(
      Redirect(routes.SessionsController.sessions(1, None)).flashing("error" -> "An internal server error occured ")
    )
  }

}

