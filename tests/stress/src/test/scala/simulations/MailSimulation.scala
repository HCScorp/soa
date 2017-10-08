package computerdatabase

import java.util.UUID

import scala.language.postfixOps

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class MailSimulation extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:9080/mail/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

}