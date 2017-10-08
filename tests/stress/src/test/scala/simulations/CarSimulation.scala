package computerdatabase

import java.util.UUID

import scala.language.postfixOps

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import java.util.Random
import scala.concurrent.duration._

class CarSimulation extends Simulation {
  val httpConf =
    http
      .baseURL("http://localhost:8080/car/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val stressSample =
    scenario("find car")
      .repeat(100) {
        exec(session =>
          session.set("model", "S")// Integer.toString(new Random.nextInt (26) + 97))
        )
          .exec(
            http("find a car ")
              .post("car")
              .body(StringBody(session => buildRegister(session)))
              .check(status.is(200))
          )
      }

  def buildRegister(session: Session): String = {
    val model = session("model").as[String]
    raw"""{
      "company" : "Tesla",
      "city" : "Nice",
      "model" : "$model",
      "numberPlate" : "999-111",
      "bookedDays" : [{ "date" : "2017-12-24" }]
      }
      """"

  }

  def buildRetrieve(session: Session): String = {
    val model = session("model").as[String]
    raw"""{
      "model": "$model"
    }""""

  }
  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}