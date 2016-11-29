package br.com.semantix.restapi

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import br.com.semantix.restapi.services.GuidServiceActor
import br.com.semantix.restapi.objects.Graph
import spray.can.Http

object Main extends App {
  /* First, load graph */
  var filename = "aux/edges.dat"
    
    // read file and load graph info
    var graph = new Graph(filename)

    // calculate closeness centrality and rank
    graph.computeCentrality()
  
  
  implicit val system = ActorSystem("Rest-Service")

  val guidService = system actorOf Props(new GuidServiceActor)

  val host = system.settings.config.getString("guid-generator.host")
  val port = system.settings.config.getInt("guid-generator.port")

  IO(Http) ! Http.Bind(guidService, host, port)

  sys.addShutdownHook(system.terminate())
}
