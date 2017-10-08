package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random

class FlightSimulation extends Simulation {
  val httpConf: HttpProtocolBuilder =
    http
      .baseURL("http://localhost:8080/flight-service-document/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val stressSample: ScenarioBuilder =
    scenario("Find random flights")
      .repeat(16) {
        exec(session => {
          session
            .set("date", "2017-" + (Random.nextInt(11) + 1).toString + "-" + (Random.nextInt(27) + 1).toString)
            .set("origin", Random.nextString(Random.nextInt(10) + 3))
            .set("destination", Random.nextString(Random.nextInt(10) + 3))
            .set("maxTravelTime", Random.nextInt(800) + 20)
        })
          .exec(
            http("Find flights simple")
              .post("flight")
              .body(StringBody(session => buildSimpleSearch(session)))
              .check(status.is(200))
          )
          .pause(1 seconds)
          .exec(
            http("Find flights complex")
              .post("flight")
              .body(StringBody(session => buildComplexSearch(session)))
              .check(status.is(200))
          )
      }

  def buildSimpleSearch(session: Session): String = {
    val date = session("date").as[String]
    val origin = session("origin").as[String]
    val destination = session("destination").as[String]
    val maxTravelTime = session("destination").as[Int]

    raw"""{
      "date" : "$date",
      "origin" : "$origin",
      "destination" : "$destination"
      }
      """"
  }

  def buildComplexSearch(session: Session): String = {
    val date = session("date").as[String]
    val origin = session("origin").as[String]
    val destination = session("destination").as[String]
    val maxTravelTime = session("maxTravelTime").as[Int]

    raw"""{
      "date" : "$date",
      "origin" : "$origin",
      "destination" : "$destination",
      "maxTravelTime" : "$maxTravelTime",
      "order" : "DESCENDING"
      }
      """"
  }
  setUp(stressSample.inject(rampUsers(2048) over (32 seconds)).protocols(httpConf))
}