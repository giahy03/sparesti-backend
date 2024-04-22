package edu.ntnu.idatt2106.sparesti.repository.user;

import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing UserInfo entities.
 * The interface extends JpaRepository, allowing basic CRUD
 * (Create, Read, Update, Delete) operations on the entities.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
