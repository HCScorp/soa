Feature: Approver

  Background:
    Given The Approver service deployed

  Scenario: Submitting a BTR for a flight and approving it

    Given a flight from Nice to Paris for today at 100€
    And a BTR for this flight
    When the btr is submitted
    Then the btr is registered
    When the btr is approved
    Then its status is APPROVED