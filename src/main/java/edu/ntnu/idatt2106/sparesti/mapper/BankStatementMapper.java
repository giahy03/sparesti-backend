package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.analysis.BankStatementDto;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the BankStatement entity.
 *
 * @author Tobias Oftedal
 */
@Mapper(componentModel = "spring", uses = {AnalysisMapper.class})
public interface BankStatementMapper {

  BankStatementMapper INSTANCE = Mappers.getMapper(BankStatementMapper.class);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "transactions", source = "transactions")
  @Mapping(target = "accountNumber", source = "accountNumber")
  @Mapping(target = "timestamp", source = "timestamp")
  @Mapping(target = "analysisIsPresent", source = "analysis", qualifiedByName =
      "analysisIsPresent")
  @Mapping(target = "fileName", source = "fileName")
  @Mapping(target = "accountName", source = "accountName")
  BankStatementDto bankStatementIntoBankStatementDto(BankStatement bankStatement);

  /**
   * Checks if an analysis is present.
   *
   * @param analysis The analysis to check.
   * @return True if the analysis is present, false otherwise.
   */
  @Named("analysisIsPresent")
  default boolean checkIfAnalysisIsPresent(BankStatementAnalysis analysis) {
    return analysis != null;
  }

}