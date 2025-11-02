package com.timeTravellers;

import com.shared.HandlesMessages;
import com.shared.MessageHandlerContext;

import java.util.concurrent.CompletionStage;

public class AddTableHandler implements HandlesMessages<AddTable> {
    private final ReservationRepository repository;

    public AddTableHandler(ReservationRepository repository) {
        this.repository = repository;
    }

    public CompletionStage<Void> handle(AddTable command, MessageHandlerContext context) {
        return repository.get(command.reservationId())
                .thenCompose(reservation -> {
                    if (reservation == null) {
                        // TODO what do we do?
                        return null;
                    }

                    reservation.addTable(command.tableNumber());

                    return repository.save(reservation);
                });
    }
}
