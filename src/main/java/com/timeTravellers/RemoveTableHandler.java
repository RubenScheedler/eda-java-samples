package com.timeTravellers;

import com.shared.MessageHandlerContext;
import com.shared.HandlesMessages;

import java.util.concurrent.CompletionStage;

public class RemoveTableHandler implements HandlesMessages<RemoveTable> {
    private final ReservationRepository repository;

    public RemoveTableHandler(ReservationRepository repository) {
        this.repository = repository;
    }

    public CompletionStage<Void> handle(RemoveTable command, MessageHandlerContext context) {
        return repository.get(command.reservationId())
                .thenCompose(reservation -> {
                    if (reservation == null) {
                        // TODO what do we do?
                        return null;
                    }

                    reservation.removeTable(command.tableId());
                    return repository.save(reservation);
                });
    }
}