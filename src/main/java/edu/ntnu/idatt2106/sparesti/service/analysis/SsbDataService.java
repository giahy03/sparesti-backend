package edu.ntnu.idatt2106.sparesti.service.analysis;

import edu.ntnu.idatt2106.sparesti.dto.ssb.response.SsbConsumptionApiResponseDto;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbIncomeQuartile;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import java.util.HashMap;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Retrieves data from the SSB API.
 */
@Service
@RequiredArgsConstructor
public class SsbDataService {
  private static final String API_LOCATION = "https://data.ssb.no/api/v0/no/table/14157";

  private final RestTemplate restTemplate;

  /**
   * Returns the expected usage of the given demography.
   *
   * @param userInfo the user information
   * @return the expected usage of the given demography
   */
  public HashMap<SsbPurchaseCategory, Double> getExpectedUsage(UserInfo userInfo
  ) {

    HashMap<SsbPurchaseCategory, Double> expectedUsage = new HashMap<>();
    List<Double> data = retrieveDataFromSsbApi(userInfo.getLivingStatus(),
        SsbIncomeQuartile.from(userInfo.getIncome()));

    List<SsbPurchaseCategory> categories = List.of(SsbPurchaseCategory.values());

    if (data.size() != categories.size()) {
      throw new RuntimeException("Data not retrieved correctly");
    }

    for (int i = 0; i < categories.size(); i++) {
      expectedUsage.put(categories.get(i), (data.get(i) * userInfo.getIncome()) / 100);
    }

    return expectedUsage;
  }

  /**
   * Retrieves data about expected spending from the SSB API.
   *
   * @param livingStatus   the living status
   * @param incomeQuartile the income quartile
   * @return the expected percentage values of spending in each category
   */
  private List<Double> retrieveDataFromSsbApi(SsbLivingStatus livingStatus,
                                              SsbIncomeQuartile incomeQuartile) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Object> requestEntity =
        new HttpEntity<>(getRequestTemplate(livingStatus, incomeQuartile),
            headers);

    ResponseEntity<SsbConsumptionApiResponseDto> responseEntity = restTemplate.exchange(
        API_LOCATION,
        HttpMethod.POST,
        requestEntity,
        SsbConsumptionApiResponseDto.class);

    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      SsbConsumptionApiResponseDto responseBody = responseEntity.getBody();
      if (responseBody == null) {
        throw new RuntimeException("Response body is null");
      }
      return responseBody.getValue();
    } else {
      throw new RuntimeException("Failed to retrieve data from SSB API");
    }
  }

  /**
   * Returns the request template for the SSB API, formatted with the given living status and
   * income.
   *
   * @param livingStatus   the living status
   * @param incomeQuartile the income quartile
   * @return the request template, formatted with the given living status and income
   */
  private String getRequestTemplate(SsbLivingStatus livingStatus,
                                    SsbIncomeQuartile incomeQuartile) {
    return String.format("""
         {
           "query": [
             {
               "code": "VareTjenesteGruppe",
               "selection": {
                 "filter": "agg_single:CoiCop2018c1",
                 "values": [
                   "00",
                   "01",
                   "02",
                   "03",
                   "04",
                   "05",
                   "06",
                   "07",
                   "08",
                   "09",
                   "10",
                   "11",
                   "12",
                   "13"
                 ]
               }
             },
             {
               "code": "HusholdType",
               "selection": {
                 "filter": "item",
                 "values": [
                   "%s"
                 ]
               }
             },
             {
               "code": "Inntektskvartil",
               "selection": {
                 "filter": "item",
                 "values": [
                   "%s"
                 ]
               }
             },
             {
               "code": "ContentsCode",
               "selection": {
                 "filter": "item",
                 "values": [
                   "AndForbrUtg"
                 ]
               }
             }
           ],
           "response": {
             "format": "json-stat2"
           }
         }
        """, livingStatus.getStatus(), incomeQuartile.getQuartileCode());
  }
}
