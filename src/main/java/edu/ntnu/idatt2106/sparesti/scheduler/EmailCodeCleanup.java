package edu.ntnu.idatt2106.sparesti.scheduler;

import edu.ntnu.idatt2106.sparesti.service.email.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The class provides functionality for periodically cleaning
 * up expired email code entities in the database.
 *
 * @author Ramin Samavat
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class EmailCodeCleanup {

  private final EmailVerificationService emailVerificationService;

  /**
   * Scheduled task method for cleaning up expired email verification codes.
   */
  @Scheduled(fixedDelay = 300000)
  public void cleanupExpiredEmailCodes() {
    emailVerificationService.cleanupExpiredCodes();
  }
}
