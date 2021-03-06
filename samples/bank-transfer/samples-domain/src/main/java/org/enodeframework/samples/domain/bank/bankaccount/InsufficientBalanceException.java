package org.enodeframework.samples.domain.bank.bankaccount;

import org.enodeframework.domain.DomainException;

import java.util.Map;

public class InsufficientBalanceException extends DomainException {
    /**
     * 账户ID
     */
    public String AccountId;
    /**
     * 交易ID
     */
    public String TransactionId;
    /**
     * 交易类型
     */
    public int TransactionType;
    /**
     * 交易金额
     */
    public double Amount;
    /**
     * 当前余额
     */
    public double CurrentBalance;
    /**
     * 当前可用余额
     */
    public double CurrentAvailableBalance;

    public InsufficientBalanceException(String accountId, String transactionId, int transactionType, double amount, double currentBalance, double currentAvailableBalance) {
        super();
        AccountId = accountId;
        TransactionId = transactionId;
        TransactionType = transactionType;
        Amount = amount;
        CurrentBalance = currentBalance;
        CurrentAvailableBalance = currentAvailableBalance;
    }

    @Override
    public void serializeTo(Map<String, Object> serializableInfo) {
        serializableInfo.put("AccountId", AccountId);
        serializableInfo.put("TransactionId", TransactionId);
        serializableInfo.put("TransactionType", "");
        serializableInfo.put("Amount", String.valueOf(Amount));
        serializableInfo.put("CurrentBalance", String.valueOf(CurrentBalance));
        serializableInfo.put("CurrentAvailableBalance", String.valueOf(CurrentAvailableBalance));
    }

    @Override
    public void restoreFrom(Map<String, Object> serializableInfo) {
        AccountId = (String) serializableInfo.get("AccountId");
        TransactionId = (String) serializableInfo.get("TransactionId");
        TransactionType = (Integer) serializableInfo.get("transactionType");
        Amount = (Double) (serializableInfo.get("Amount"));
        CurrentBalance = (Double) (serializableInfo.get("CurrentBalance"));
        CurrentAvailableBalance = (Double) (serializableInfo.get("CurrentAvailableBalance"));
    }
}
