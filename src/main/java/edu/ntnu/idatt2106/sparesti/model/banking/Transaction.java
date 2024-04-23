package edu.ntnu.idatt2106.sparesti.model.banking;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.MonthDay;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Class representing a transaction in a bank statement.
 */
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "transactions")
@Entity
@NoArgsConstructor
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier for the user")
  @Column(name = "id")
  @Setter(AccessLevel.NONE)
  private Long id;


  @NonNull
  private MonthDay date;
  @NonNull
  private String description;
  @NonNull
  private Double amount;
  @NonNull
  private Boolean isIncoming;
  private SsbPurchaseCategory category;

  @ManyToOne
  private BankStatement bankStatement;
}

