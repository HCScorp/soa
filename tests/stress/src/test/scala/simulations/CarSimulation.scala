package computerdatabase

import java.time.LocalDate
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
      .repeat(6) {
        exec(session => {
          session.set("date", randomDay())
          session.set("model", Integer.toString(new Random().nextInt(26) + 97))
        }

        )
          .exec(
            http("find a car with different date")
              .post("car")
              .body(StringBody(session => buildCarDate(session)))
              .check(status.is(200))
          )

          .pause(1 seconds)
          .exec(
            http("find a car with the model")
              .post("car")
              .body(StringBody(session => buildCarModel(session)))
              .check(status.is(200))
          )

      }


  def randomDay(): String = {
    val minDate = LocalDate.of(2017, 1, 10).toEpochDay.asInstanceOf[Integer]
    val maxDate = LocalDate.of(2017, 11, 10).toEpochDay.asInstanceOf[Integer]
    // mount the real random date !
    val random = Integer.toString(new Random().nextInt(5) + (maxDate.intValue() - minDate.intValue()))
    // create the request
    random
  }


  def buildCarDate(session: Session): String = {
    val date = session("date").as[String]
    raw"""{
      "company" : "Tesla",
      "city" : "Nice",
      "model" : "S",
      "numberPlate" : "999-111",
      "bookedDays" : [{ "date" : "$date" }]
      }
      """"
  }

  def buildCarModel(session: Session): String = {
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

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}