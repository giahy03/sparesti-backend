package edu.ntnu.idatt2106.sparesti.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents certain information about an email.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetailsDto {
  private String recipient;
  private String subject;
  private String body;
  private String attachment;
}
