package edu.ntnu.idatt2106.sparesti.model.banking;

import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.YearMonth;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Class representing a bank statement for a given month.
 */

@RequiredArgsConstructor
@Table(name = "statements")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class BankStatement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier for the user")
  @Column(name = "id")
  @Setter(AccessLevel.NONE)
  private Long id;

  @ManyToOne
  private User user;
  @NonNull
  private String accountNumber;
  @NonNull

  @OneToMany(cascade = CascadeType.ALL)
  private List<Transaction> transactions;
  @NonNull
  private YearMonth timestamp;

  @OneToOne(cascade = CascadeType.ALL)
  private BankStatementAnalysis analysis;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "account_name")
  private String accountName;


}
