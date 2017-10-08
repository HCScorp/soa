package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random

class MailSimulation extends Simulation {
  val httpConf: HttpProtocolBuilder =
    http
      .baseURL("http://localhost:8080/mail-service-rpc/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val stressSample: ScenarioBuilder =
    scenario("Send dummy email")
      .repeat(16) {
        exec().exec(
            http("Send email")
              .post("mail")
              .body(StringBody(session => buildSendMail(session)))
              .check(status.is(200)))
      }

  def buildSendMail(session: Session): String = {
    raw"""{
      "sender": "supercoolmail@gmail.com",
      "recipient": "superuncoolmail@gmail.com",
      "object": "Asking for vacation",
      "message": "Hi, your request has been approved by Jean Michel, see the summary below: ..."
      }
      """"
  }

  setUp(stressSample.inject(rampUsers(2048) over (32 seconds)).protocols(httpConf))
}