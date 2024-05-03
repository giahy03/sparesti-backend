package edu.ntnu.idatt2106.sparesti.mapper;


import edu.ntnu.idatt2106.sparesti.dto.analysis.AnalysisItemDto;
import edu.ntnu.idatt2106.sparesti.model.analysis.AnalysisItem;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the AnalysisItem entity.
 *
 * @author Tobias Oftedal
 */
@Mapper(componentModel = "spring")
public interface AnalysisItemMapper {

  AnalysisItemMapper INSTANCE = Mappers.getMapper(AnalysisItemMapper.class);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "category", qualifiedByName = "mapCategory", source = "purchaseCategory")
  @Mapping(target = "expectedValue", source = "expectedValue")
  @Mapping(target = "actualValue", source = "actualValue")
  AnalysisItemDto toAnalysisItemDto(AnalysisItem analysisItem);

  /**
   * Maps a SsbPurchaseCategory to a string.
   *
   * @param purchaseCategory The purchase category to map.
   * @return The category code.
   */
  @Named("mapCategory")
  default String mapCategory(SsbPurchaseCategory purchaseCategory) {
    return purchaseCategory.getCategoryCode();
  }
}
