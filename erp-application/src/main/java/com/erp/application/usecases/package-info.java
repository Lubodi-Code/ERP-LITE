/**
 * Use cases (application services) for the CQRS layer.
 * Each use case orchestrates the domain: it receives a command or query,
 * rebuilds domain Value Objects, invokes the aggregate, and persists the result.
 * Implementations are added starting in the next lesson.
 */
package com.erp.application.usecases;
