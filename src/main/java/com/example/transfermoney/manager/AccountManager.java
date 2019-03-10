package com.example.transfermoney.manager;

import java.util.Map;

import com.example.transfermoney.model.Account;
import com.example.transfermoney.model.ResultModel;
import com.example.transfermoney.request.TransferRequest;

public interface AccountManager {
	Map<String, Account> init();
	Map<String, Account> getAccounts();
	ResultModel transfer(TransferRequest request);
}
