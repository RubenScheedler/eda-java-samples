package com.timeTravellers;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public interface ReservationRepository {
    CompletionStage<Reservation> get(UUID id);

    CompletionStage<Void> save(Reservation reservation);
}