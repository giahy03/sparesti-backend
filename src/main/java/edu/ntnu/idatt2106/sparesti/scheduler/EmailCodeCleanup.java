package edu.ntnu.idatt2106.sparesti.scheduler;

import edu.ntnu.idatt2106.sparesti.service.email.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The class provides functionality for periodically cleaning up expired EmailCode entities
 * in the database.
 * It uses the {@link EmailVerificationService} to perform the operation in the database.
 *
 * @author Ramin Samavat
 * @version 1.0
 */
@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class EmailCodeCleanup {

  private final EmailVerificationService emailVerificationService;

  /**
   * Scheduled task method for cleaning up expired email verification codes.
   * This method is automatically called at regular intervals of 300 thousand milliseconds
   * (5 minutes).
   */
  @Scheduled(fixedDelay = 300000)
  public void cleanupExpiredEmailCodes() {
    log.debug("Scheduled cleanup expired email codes.");
    emailVerificationService.cleanupExpiredCodes();
  }
}
