package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.analysis.AnalysisItemDto;
import edu.ntnu.idatt2106.sparesti.dto.analysis.BankStatementAnalysisDto;
import edu.ntnu.idatt2106.sparesti.model.analysis.AnalysisItem;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the BankStatementAnalysis entity.
 */
@Mapper(componentModel = "spring", uses = {AnalysisItemMapper.class})
public interface AnalysisMapper {
  AnalysisMapper INSTANCE = Mappers.getMapper(AnalysisMapper.class);

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "analysisItems", source = "analysisItems", qualifiedByName =
          "mapAnalysisItems")
  })
  BankStatementAnalysisDto bankStatementAnalysisIntoBankStatementAnalysisDto(
      BankStatementAnalysis bankStatementAnalysis);

  /**
   * Maps a list of AnalysisItems to a list of AnalysisItemDto objects.
   *
   * @param analysisItems The list of AnalysisItems to map.
   * @return A list of AnalysisItemDto objects.
   */
  @Named("mapAnalysisItems")
  default List<AnalysisItemDto> mapAnalysisItems(List<AnalysisItem> analysisItems) {
    return analysisItems.stream()
        .map(AnalysisItemMapper.INSTANCE::toAnalysisItemDto)
        .collect(Collectors.toList());
  }
}




