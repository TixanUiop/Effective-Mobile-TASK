package com.togithub.effectivemobilejavatask.service;

import com.togithub.effectivemobilejavatask.dto.TransactionReceiptDTO;
import com.togithub.effectivemobilejavatask.dto.TransferCardFromDTO;
import com.togithub.effectivemobilejavatask.dto.TransferCardToDTO;

import java.math.BigDecimal;

public interface TransactionalService {
    TransactionReceiptDTO transfer(TransferCardFromDTO from, TransferCardToDTO to, BigDecimal amount);
}
