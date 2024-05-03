package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.dto.email.EmailDetailsDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/**
 * Service class for sending email with join code to a friend.
 * This class contains methods for sending an email to a friend with the join code of a challenge
 *
 * @version 1.0
 * @author Jeffrey Yao Annor Tabiri
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmailFriendCodeService {

  private final EmailServiceImpl emailService;
  private final SharedChallengeRepository sharedChallengeRepository;
  private final UserRepository userRepository;

  /**
   * Sends an email to a friend with the join code of a challenge.
   *
   * @param emailToSend the email to send the verification code to.
   * @param id the id of the challenge to send the join code to.
   */
  public void sendJoinCode(Principal principal, String emailToSend, Long id) {
    SharedChallenge challenge =
            sharedChallengeRepository
                    .findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("No challenge with id: " + id));

    checkUser(emailToSend);
    checkAuthorization(principal.getName(), challenge);


    EmailDetailsDto emailDetailsDto = buildEmailDto(emailToSend, challenge);
    emailService.sendEmail(emailDetailsDto);
  }


  /**
   * Check if user owns the challenge.
   *
   * @param email the email of the user.
   */
  private void checkAuthorization(String email, Challenge challenge) {
    if (!challenge.getUser().getEmail().equals(email)) {
      throw new IllegalArgumentException("You do not own this challenge");
    }
  }




  private void checkUser(String email) {
    userRepository
            .findUserByEmailIgnoreCase(email).orElseThrow(() ->
                    new UsernameNotFoundException("No user with email: " + email));
  }


  /**
   * Creates an email dto.
   *
   * @param email the email to send the verification code to.
   * @param challenge the challenge to send the join code to.
   * @return the email dto which is to be sent.
   */
  private EmailDetailsDto buildEmailDto(String email, SharedChallenge challenge) {
    String body = """
        You have been invited to a challenge: %s
        Your join code is: %s
        The sender of the challenge is: %s
        """.formatted(
                challenge.getTitle(),
                challenge.getSharedChallengeCode().getJoinCode(),
                challenge.getUser().getEmail());

    return EmailDetailsDto.builder()
            .recipient(email)
            .subject("Join code for challenge: " + challenge.getTitle())
            .body(body)
            .build();
  }

}
