package br.com.semantix.restapi.actors

import spray.json._
import DefaultJsonProtocol._ 

import akka.actor.Actor
import br.com.semantix.restapi.Main
import br.com.semantix.restapi.actors.GuidActor.GetCentrality
import br.com.semantix.restapi.objects.Centrality
import scala.io.Source

object GuidActor {
  case object GetCentrality
  
}

class GuidActor extends Actor {

  def receive = {
    case GetCentrality => sender ! Result(Main.graph.centralityJSON.toJson.toString)
  }
}

case class Result(result: String)
