package edu.ntnu.idatt2106.sparesti.model.user;

import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The user class represents a user entity in the application.
 * This class implements the Spring Security {@link UserDetails} interface.
 * Each User object encapsulates information about a user.
 *
 * @author Tobias Oftedal
 * @author Ramtin Samavat
 * @version 1.0
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Schema(description = "A user entity.")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier for the user")
  @Column(name = "user_id")
  @Setter(AccessLevel.NONE)
  private Long userId;

  @Schema(description = "The user's email address.")
  @Column(name = "email", nullable = false, unique = true)
  @NonNull
  private String email;

  @Schema(description = "The user's password.")
  @Column(name = "password", nullable = false)
  @NonNull
  private String password;

  @Schema(description = "The user's first name.")
  @Column(name = "first_name", nullable = false)
  @NonNull
  private String firstName;

  @Schema(description = "The user's last name.")
  @Column(name = "last_name", nullable = false)
  @NonNull
  private String lastName;

  @Schema(description = "The user's saving goals.")
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<SavingGoal> goals;

  @Schema(description = "The user's badges.")
  @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
  private Set<Badge> badges;

  @Enumerated(EnumType.STRING)
  @Schema(description = "The user's role.")
  @Column(name = "role", nullable = false)
  @NonNull
  private Role role;

  /**
   * Retrieves the roles/authorities associated with this user.
   *
   * @return The collection of granted permissions.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  /**
   * Retrieves the user's password.
   *
   * @return The user's password.
   */
  @Override
  public @NonNull String getPassword() {
    return this.password;
  }

  /**
   * Retrieves the user's email address.
   *
   * @return The user's email address.
   */
  @Override
  public @NonNull String getUsername() {
    return this.email;
  }

  /**
   * Indicates whether the user's account is non-expired.
   *
   * @return true if the user's account is non-expired, false otherwise.
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * Indicates whether the user's account is non-locked.
   *
   * @return true if the user's account is non-locked, false otherwise.
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * Indicates whether the user's credentials are non-expired.
   *
   * @return true if the user's credentials are non-expired, false otherwise.
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * Indicates whether the user's account is enabled.
   *
   * @return true if the user's account is enabled, false otherwise.
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}
