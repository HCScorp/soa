package fr.unice.polytech.hcs.flows.expense;

public enum Status {
    // BT = business travel

    // Pre BT
    WAITING,    // Waiting for a manager to approve this BT
    APPROVED,   // BT approved, not yet started
    DENIED,     // BT refused, end of story.

    // During BT
    ONGOING,    // BT started and still ongoing
    DONE,       // BT ended, waiting for a refund approval

    // Post BT
    WAITING_FOR_EXPLANATION,    // Manager waiting for an explanation to decide whether or not refund the BT
    REFUND_ACCEPTED,            // BT refund acceptRefund
    REFUND_REFUSED              // BT refund refused
}
