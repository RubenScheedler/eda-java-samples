package com.timeTravellers;

import com.shared.MessageHandlerContext;
import com.shared.HandlesMessages;

import java.util.concurrent.CompletionStage;

public class CreateReservationHandler implements HandlesMessages<CreateReservation> {
    private final ReservationRepository repository;

    public CreateReservationHandler(ReservationRepository repository) {
        this.repository = repository;
    }

    public CompletionStage<Void> handle(CreateReservation command, MessageHandlerContext context) {
        return repository.get(command.id())
                .thenCompose(existingReservation -> {
                    if (existingReservation != null) {
                        // TODO what do we do?
                        return null;
                    }

                    // If there is no existing reservation we need to create one before saving.
                    Reservation reservation = new Reservation(command.id());
                    return repository.save(reservation);
                });
    }
}
