package br.com.semantix.restapi.directives

import akka.util.Timeout
import spray.routing.Directives

import scala.concurrent.duration._

trait CommonDirective extends Directives {
  implicit val timeout = Timeout(1 seconds)

  val apiPrefix = pathPrefix("api")
}