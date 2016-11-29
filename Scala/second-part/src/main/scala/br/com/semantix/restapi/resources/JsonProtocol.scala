package br.com.semantix.restapi.resources

import br.com.semantix.restapi.actors.Result
import spray.http.HttpResponse
import spray.httpx.unmarshalling.{FromResponseUnmarshaller, MalformedContent}
import spray.json.{DefaultJsonProtocol, _}


object JsonProtocol extends DefaultJsonProtocol {

  implicit val uidUnmarshaller = new FromResponseUnmarshaller[Result] {
    implicit val uidFormat = jsonFormat1(Result)

    def apply(response: HttpResponse) = try {
      Right(response.entity.asString.parseJson.convertTo[Result])
    } catch {
      case x: Throwable => Left(MalformedContent("Couldn't unmarshal Result.", x))
    }
  }

  implicit val uidFormat = jsonFormat1(Result)
}

