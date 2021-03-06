package org.enodeframework.samples.domain.bank.deposittransaction;

import org.enodeframework.eventing.DomainEvent;

/**
 * 存款交易预存款已确认
 */
public class DepositTransactionPreparationCompletedEvent extends DomainEvent<String> {
    public String AccountId;

    public DepositTransactionPreparationCompletedEvent() {
    }

    public DepositTransactionPreparationCompletedEvent(String accountId) {
        AccountId = accountId;
    }
}
