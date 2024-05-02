package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * Mapper for the Transaction entity.
 * The mapper is used to map between the two classes.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @see Transaction
 * @see TransactionDto
 */
@Mapper
public interface TransactionMapper {

  TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

  /**
   * Maps a Transaction object to a TransactionDto object.
   *
   * @param transaction The Transaction object to map.
   * @return A TransactionDto object.
   */
  TransactionDto transactionToTransactionDto(Transaction transaction);

  /**
   * Maps a TransactionDto object to a Transaction object.
   *
   * @param transactionDto The TransactionDto object to map.
   * @return A Transaction object.
   */
  Transaction transactionDtoToTransaction(TransactionDto transactionDto);
}