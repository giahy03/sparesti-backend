package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.user.UserInfoDto;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between UserInfoDto and UserInfo entities.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface UserInfoMapper {

  /**
   * Instance of the UserInfoMapper for access to its mapping methods.
   */
  UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

  /**
   * The method converts UserInfoDto to UserInfo entity.
   *
   * @param userInfoDto UserInfoDto object to be converted.
   * @return UserInfo entity converted from UserInfoDto.
   */
  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "income", source = "income"),
      @Mapping(target = "livingStatus", qualifiedByName = "mapLivingStatus"),
      @Mapping(target = "user", ignore = true),
  })
  UserInfo toUserInfo(UserInfoDto userInfoDto);

  /**
   * Custom mapping method for converting to SsbLivingStatus enum.
   *
   * @param livingStatus Living status integer value.
   * @return Corresponding enum value.
   */
  @Named("mapLivingStatus")
  default SsbLivingStatus mapLivingStatus(Integer livingStatus) {
    return SsbLivingStatus.fromInteger(livingStatus);
  }
}
