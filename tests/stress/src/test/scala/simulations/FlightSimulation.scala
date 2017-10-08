package computerdatabase

import java.util.UUID

import scala.language.postfixOps

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class FlightSimulation extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:9080/flight/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")
}