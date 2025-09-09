package com.togithub.effectivemobilejavatask.service.transaction;

import com.togithub.effectivemobilejavatask.dto.transcational.TransactionReceiptDTO;
import com.togithub.effectivemobilejavatask.dto.transcational.TransferRequestDTO;


public interface TransactionalService {
    TransactionReceiptDTO transfer(TransferRequestDTO request);
}
