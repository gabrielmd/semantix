package br.com.semantix.restapi.services

import akka.actor.{Actor, Props}
import br.com.semantix.restapi.directives.GuidDirective
import br.com.semantix.restapi.actors.GuidActor
import spray.routing.HttpService


class GuidServiceActor extends Actor with GuidService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing,
  // timeout handling or alternative handler registration
  def receive = runRoute(guidRoute)
}


trait GuidService extends HttpService {
  implicit def executionContext = actorRefFactory.dispatcher

  val guidGeneratorActor = actorRefFactory actorOf Props(new GuidActor)
  
  lazy val guidRoute = new GuidDirective(guidGeneratorActor).route
}
