package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random

class ApproverSimulation extends Simulation {
  val httpConf: HttpProtocolBuilder =
    http
      .baseURL("http://localhost:8080/approver-service-rest/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  var id = 0

  val stressSample: ScenarioBuilder =
    scenario("Business travel request management")
      .repeat(16) {
        exec(session => {
          session
            .set("id", "2017-" + (Random.nextInt(5) + 1).toString + "-" + (Random.nextInt(14) + 1).toString)
            .set("dateTo", "2017-" + (Random.nextInt(5) + 7).toString + "-" + (Random.nextInt(12) + 16).toString)
            .set("city", Random.nextString(Random.nextInt(10) + 3))
        })
          .exec(
            http("Submit BTR")
              .post("approver")
              .body(StringBody(session => buildSubmit(session)))
              .check(status.is(200))
              .check(jsonPath("$..token")
                .ofType[String]
                .saveAs("token")))
          .pause(1 seconds)
          .exec(
            http("Manager view BTR")
              .get("approver/1")
              .check(status.is(200)))
          .pause(4 seconds)
          .exec(
            http("Manager approve BTR")
              .put("approver/1")
              .queryParam("status", "APPROVED")
              .check(status.is(200)))
      }

  def buildSubmit(session: Session): String = {
    raw"""{
      "cars": [],
      "hotels": []
      "flights": [
        {
            "origin": "Nice",
            "destination": "Paris",
            "date": "2017-8-14",
            "price": "89",
            "journeyType": "DIRECT",
            "duration": 92,
            "category": "ECO",
            "airline": "Air France"
        },
        {
            "origin": "Nice",
            "destination": "Paris",
            "date": "2017-8-14",
            "price": "63",
            "journeyType": "DIRECT",
            "duration": 105,
            "category": "ECO",
            "airline": "EasyJet"
        }
      ]
    }
    """"
  }

  setUp(stressSample.inject(rampUsers(2048) over (32 seconds)).protocols(httpConf))
}