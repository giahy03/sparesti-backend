package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.user.UserInfoDto;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between UserInfoDto and UserInfo entities.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Mapper
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
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "income", source = "income")
  @Mapping(target = "livingStatus", ignore = true)
  @Mapping(target = "user", ignore = true)
  UserInfo toUserInfo(UserInfoDto userInfoDto);
}
