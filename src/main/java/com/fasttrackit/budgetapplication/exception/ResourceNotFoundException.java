package com.fasttrackit.budgetapplication.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{
    private long entityId;

    public ResourceNotFoundException(String message, long entityId) {
        super(message);
        this.entityId = entityId;
    }
}
