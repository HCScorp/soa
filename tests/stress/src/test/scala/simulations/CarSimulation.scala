package simulations

import scala.language.postfixOps
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.util.Random

class CarSimulation extends Simulation {
  val httpConf: HttpProtocolBuilder =
    http
      .baseURL("http://localhost:8080/car-service-document/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val stressSample: ScenarioBuilder =
    scenario("Find available cars in Nice")
      .repeat(16) {
        exec(session => {
          session
            .set("dateFrom", "2017-" + (Random.nextInt(5) + 1).toString + "-" + (Random.nextInt(14) + 1).toString)
            .set("dateTo", "2017-" + (Random.nextInt(5) + 7).toString + "-" + (Random.nextInt(12) + 16).toString)
            .set("city", Random.nextString(Random.nextInt(10) + 3))
        })
          .exec(
            http("Find cars by date")
              .post("car")
              .body(StringBody(session => buildSearchByDate(session)))
              .check(status.is(200)))
          .pause(1 seconds)
          .exec(
            http("Find cars by city")
              .post("car")
              .body(StringBody(session => buildSearchByCity(session)))
              .check(status.is(200)))
          .pause(1 seconds)
          .exec(
            http("Find cars by everything")
              .post("car")
              .body(StringBody(session => buildSearchByEverything(session)))
              .check(status.is(200)))
      }

  def buildSearchByDate(session: Session): String = {
    val dateFrom = session("dateFrom").as[String]
    val dateTo = session("dateTo").as[String]

    raw"""{
      "city" : "Nice",
      "dateFrom" : "$dateFrom",
      "dateTo" : "$dateTo",
      }
      """"
  }

  def buildSearchByCity(session: Session): String = {
    val city = session("city").as[String]

    raw"""{
      "city" : "$city",
      "dateFrom" : "2017-12-1",
      "dateTo" : "2017-12-18",
      }
      """"
  }

  def buildSearchByEverything(session: Session): String = {
    val dateFrom = session("dateFrom").as[String]
    val dateTo = session("dateTo").as[String]
    val city = session("city").as[String]

    raw"""{
      "city" : "$city",
      "dateFrom" : "$dateFrom",
      "dateTo" : "$dateTo",
      }
      """"
  }

  setUp(stressSample.inject(rampUsers(2048) over (32 seconds)).protocols(httpConf))
}