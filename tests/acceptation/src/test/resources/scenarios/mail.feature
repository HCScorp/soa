Feature: Mail

  Background:
    Given The Mail service deployed on localhost:9094

  Scenario: Sending an email

    Given a mail from test@mail.com to recipient@mail.com
    When the mail is sent
    Then the mail is sent