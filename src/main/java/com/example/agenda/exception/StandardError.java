package com.example.agenda.exception;

import java.time.LocalDateTime;

public record StandardError(
    LocalDateTime timestamp,
    Integer status,
    String error,
    String message,
    String path
) {}
