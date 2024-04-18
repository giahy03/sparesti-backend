package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.EmailCode;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for managing email codes.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
public interface EmailCodeRepository extends JpaRepository<EmailCode, Long> {

  EmailCode findByRegisterEmail(String email);

  void deleteByEmail(String email);

  void updateEmailCodeByRegisterEmail(String email, String code);

}
