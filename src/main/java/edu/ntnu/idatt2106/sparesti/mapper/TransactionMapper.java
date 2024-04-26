package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * Mapper for the Transaction entity.
 * The mapper is used to map between the two classes.
 *
 * @see Transaction
 * @see TransactionDto
 * @author Jeffrey Yaw Annor Tabiri
 */
@Mapper
public interface TransactionMapper {
  TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

  TransactionDto transactionToTransactionDto(Transaction transaction);

  Transaction transactionDtoToTransaction(TransactionDto transactionDto);
}