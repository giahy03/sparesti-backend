package edu.ntnu.idatt2106.sparesti.model.user;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents a stored user entity.
 *
 * @author Tobias Oftedal
 * @see UserDetails
 */
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  @Setter(AccessLevel.NONE)
  private Long userId;

  @OneToMany(mappedBy="user_id")
  private Set<SavingGoal> goals;

  @Column(name = "username", nullable = false, unique = true)
  @NonNull
  @Setter(AccessLevel.NONE)
  private String username;

  @Column(name = "password", nullable = false)
  @NonNull
  private String password;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
