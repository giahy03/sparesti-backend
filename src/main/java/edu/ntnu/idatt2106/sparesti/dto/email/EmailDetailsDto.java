package edu.ntnu.idatt2106.sparesti.dto.email;

import lombok.Data;

/**
 * DTO that represents certain information about an email.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Data
public class EmailDetailsDto {
  private String recipient;
  private String subject;
  private String body;
}
