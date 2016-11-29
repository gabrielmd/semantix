package br.com.semantix.restapi.directives

import akka.actor.ActorRef
import akka.pattern.ask
import br.com.semantix.restapi.actors.Result
import br.com.semantix.restapi.Main
import br.com.semantix.restapi.resources.JsonProtocol._
import br.com.semantix.restapi.actors.GuidActor.GetCentrality

import spray.httpx.SprayJsonSupport._

import scala.concurrent.ExecutionContext

import spray.json._
import DefaultJsonProtocol._ 

case class PostParams(a: Integer, b:Integer)

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit object PostParamsFormat extends RootJsonFormat[PostParams] {
    def write(c: PostParams) = JsObject(
      "a" -> JsNumber(c.a),
      "b" -> JsNumber(c.b)
    )
    def read(value: JsValue) = {
      value.asJsObject.getFields("a", "b") match {
        case Seq(JsNumber(a), JsNumber(b)) =>
          new PostParams(a.toInt, b.toInt)
        case _ => throw new DeserializationException("PostParams expected")
      }
    }
  }
}

import MyJsonProtocol._

class GuidDirective(guidActor: ActorRef)
(implicit executionContext: ExecutionContext) extends CommonDirective {

  // Routes: GET and POST
	val route = path("centrality") {
		get {
			complete{
			  (guidActor ? GetCentrality).mapTo[Result]
      }
		}
	} ~
	path("addEdge") { 
		post { 
			handleWith { json: String =>  //handleWith will unmarshall the input
          val params = json.parseJson.convertTo[PostParams]
          Main.graph.addEdge(params.a, params.b)
          // re-calculate closeness centrality and rank
          Main.graph.computeCentrality()
          Result("success").toJson.toString
        }
		}
	}

}

