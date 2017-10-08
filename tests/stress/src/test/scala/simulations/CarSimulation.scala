package computerdatabase

import java.util.UUID

import scala.language.postfixOps

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import org.bson.Document

class CarSimulation extends Simulation {
  val httpConf =
    http
      .baseURL("http://localhost:9080/car/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val stressSample =
    scenario("find car")
      .repeat(10) {
        exec(session =>
          session.set()
        )
          .exec(
            http("find a car ")
              .post("registry")
              .body(StringBody(session => buildRegister(session)))
              .check(status.is(200))
          )
      }

  def buildRegister(session: Session): String = {
    val ssn = session("ssn").as[String]
    raw"""
    { "company" : "Tesla",
      "city" : "Nice",
      "model" : "S",
      "numberPlate" : "999-111",
      "bookedDays" : [{ "date" : "2017-12-24" }]
      }
      """

  }

}