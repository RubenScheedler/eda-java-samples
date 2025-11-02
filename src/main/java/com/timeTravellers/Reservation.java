package com.timeTravellers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Reservation {
    private final UUID id;
    private final List<Long> tables = new ArrayList<>();
    private boolean isCancelled = false;

    public Reservation(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public List<Long> getTables() {
        return tables;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void addTable(long tableNumber) {
        if (isCancelled) {
            // What to do? currently ignore
            return;
        }

        if (!tables.contains(tableNumber)) {
            tables.add(tableNumber);
        }
    }

    public void removeTable(long tableNumber) {
        if (!tables.contains(tableNumber)) {
            // What to do? currently ignore
            return;
        }

        tables.remove(tableNumber);
    }

    public void cancel() {
        isCancelled = true;
    }
}








