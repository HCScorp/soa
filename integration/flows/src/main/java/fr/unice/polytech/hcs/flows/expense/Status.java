package fr.unice.polytech.hcs.flows.expense;

public interface Status {
    // BT = business travel

    // Pre BT
    String WAITING = "WAITING";     // Waiting for a manager to approve this BT
    String APPROVED = "APPROVED";   // BT approved
    String DENIED = "DENIED";       // BT refused, end of story.

    // During BT
    String DONE = "DONE";       // BT ended, waiting for a refund approval

    // Post BT
    String WAITING_FOR_EXPLANATION = "WAITING_FOR_EXPLANATION"; // Manager waiting for an explanation to decide whether or not refund the BT
    String REFUND_ACCEPTED = "REFUND_ACCEPTED";                 // BT refund acceptRefund
    String REFUND_REFUSED = "REFUND_REFUSED";                   // BT refund refused
}
