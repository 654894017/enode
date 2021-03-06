package org.enodeframework.samples.domain.bank.transfertransaction;

import org.enodeframework.domain.AggregateRoot;
import org.enodeframework.samples.domain.bank.TransactionStatus;

/**
 * 聚合根，表示一笔银行内账户之间的转账交易
 */
public class TransferTransaction extends AggregateRoot<String> {
    private TransferTransactionInfo _transactionInfo;
    private int _status;
    private boolean _isSourceAccountValidatePassed;
    private boolean _isTargetAccountValidatePassed;
    private boolean _isTransferOutPreparationConfirmed;
    private boolean _isTransferInPreparationConfirmed;
    private boolean _isTransferOutConfirmed;
    private boolean _isTransferInConfirmed;

    /**
     * 构造函数
     */
    public TransferTransaction(String transactionId, TransferTransactionInfo transactionInfo) {
        super(transactionId);
        applyEvent(new TransferTransactionStartedEvent(transactionInfo));
    }

    /**
     * 确认账户验证通过
     */
    public void ConfirmAccountValidatePassed(String accountId) {
        if (_status == TransactionStatus.Started) {
            if (accountId == _transactionInfo.SourceAccountId) {
                if (!_isSourceAccountValidatePassed) {
                    applyEvent(new SourceAccountValidatePassedConfirmedEvent(_transactionInfo));
                    if (_isTargetAccountValidatePassed) {
                        applyEvent(new AccountValidatePassedConfirmCompletedEvent(_transactionInfo));
                    }
                }
            } else if (accountId == _transactionInfo.TargetAccountId) {
                if (!_isTargetAccountValidatePassed) {
                    applyEvent(new TargetAccountValidatePassedConfirmedEvent(_transactionInfo));
                    if (_isSourceAccountValidatePassed) {
                        applyEvent(new AccountValidatePassedConfirmCompletedEvent(_transactionInfo));
                    }
                }
            }
        }
    }

    /**
     * 确认预转出
     */
    public void ConfirmTransferOutPreparation() {
        if (_status == TransactionStatus.AccountValidateCompleted) {
            if (!_isTransferOutPreparationConfirmed) {
                applyEvent(new TransferOutPreparationConfirmedEvent(_transactionInfo));
            }
        }
    }

    /**
     * 确认预转入
     */
    public void ConfirmTransferInPreparation() {
        if (_status == TransactionStatus.AccountValidateCompleted) {
            if (!_isTransferInPreparationConfirmed) {
                applyEvent(new TransferInPreparationConfirmedEvent(_transactionInfo));
            }
        }
    }

    /**
     * 确认转出
     */
    public void ConfirmTransferOut() {
        if (_status == TransactionStatus.PreparationCompleted) {
            if (!_isTransferOutConfirmed) {
                applyEvent(new TransferOutConfirmedEvent(_transactionInfo));
                if (_isTransferInConfirmed) {
                    applyEvent(new TransferTransactionCompletedEvent());
                }
            }
        }
    }

    /**
     * 确认转入
     */
    public void ConfirmTransferIn() {
        if (_status == TransactionStatus.PreparationCompleted) {
            if (!_isTransferInConfirmed) {
                applyEvent(new TransferInConfirmedEvent(_transactionInfo));
                if (_isTransferOutConfirmed) {
                    applyEvent(new TransferTransactionCompletedEvent());
                }
            }
        }
    }

    /**
     * 取消转账交易
     */
    public void Cancel() {
        applyEvent(new TransferTransactionCanceledEvent());
    }

    private void Handle(TransferTransactionStartedEvent evnt) {
        _transactionInfo = evnt.TransactionInfo;
        _status = TransactionStatus.Started;
    }

    private void Handle(SourceAccountValidatePassedConfirmedEvent evnt) {
        _isSourceAccountValidatePassed = true;
    }

    private void Handle(TargetAccountValidatePassedConfirmedEvent evnt) {
        _isTargetAccountValidatePassed = true;
    }

    private void Handle(AccountValidatePassedConfirmCompletedEvent evnt) {
        _status = TransactionStatus.AccountValidateCompleted;
    }

    private void Handle(TransferOutPreparationConfirmedEvent evnt) {
        _isTransferOutPreparationConfirmed = true;
    }

    private void Handle(TransferInPreparationConfirmedEvent evnt) {
        _isTransferInPreparationConfirmed = true;
        _status = TransactionStatus.PreparationCompleted;
    }

    private void Handle(TransferOutConfirmedEvent evnt) {
        _isTransferOutConfirmed = true;
    }

    private void Handle(TransferInConfirmedEvent evnt) {
        _isTransferInConfirmed = true;
    }

    private void Handle(TransferTransactionCompletedEvent evnt) {
        _status = TransactionStatus.Completed;
    }

    private void Handle(TransferTransactionCanceledEvent evnt) {
        _status = TransactionStatus.Canceled;
    }
}