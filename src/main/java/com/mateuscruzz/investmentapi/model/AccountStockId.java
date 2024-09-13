package com.mateuscruzz.investmentapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AccountStockId {

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "stock_id")
    private String stockId;
}
