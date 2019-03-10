package com.example.transfermoney.manager.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONException;
import com.example.transfermoney.common.ResultStatus;
import com.example.transfermoney.manager.AccountManager;
import com.example.transfermoney.model.Account;
import com.example.transfermoney.model.Money;
import com.example.transfermoney.model.ResultModel;
import com.example.transfermoney.request.TransferRequest;

@Component
public class DaoMemAccountManager implements AccountManager {
	private static final Logger logger = LoggerFactory.getLogger(DaoMemAccountManager.class);
	private static Map<String, Account> accounts = new HashMap<>();
	
	enum CurrencyCode {
		HKD, USD, GBP;
	}
	
	@Override
	public Map<String, Account> init() {
		Account a = new Account();
		Account b = new Account();
		Account c = new Account();
		a.setId("1");
		a.setAccountName("USD Account");
		a.setBalance(new Money(new BigDecimal("1000"), CurrencyCode.USD.name()));
		b.setId("2");
		b.setAccountName("HKD Account");
		b.setBalance(new Money(new BigDecimal("1000"), CurrencyCode.HKD.name()));
		c.setId("3");
		c.setAccountName("HKD Account");
		c.setBalance(new Money(new BigDecimal("1000"), CurrencyCode.HKD.name()));
		accounts.put(a.getId(), a);
		accounts.put(b.getId(), b);
		accounts.put(c.getId(), c);
		return accounts;
	}
	
	@Override
	public Map<String, Account> getAccounts(){
		return getDummyData();
	}
	
	public static Map<String, Account> getDummyData(){
		return accounts;
	}
	
	public static Account getAccountById(String id){
		return accounts.get(id);
	}
	
	@Override
	public ResultModel transfer(TransferRequest request){
		try{
			String fromAccountId = request.getFromAccountId();
			String toAccountId = request.getToAccountId();
			String currencyCode = request.getCurrencyCode();
			BigDecimal amount = new BigDecimal(request.getAmount());
			if(fromAccountId==null || toAccountId==null || currencyCode==null){
				return ResultModel.error(ResultStatus.BAD_FORMAT);
			}
			if(fromAccountId.equals(toAccountId)){
				return ResultModel.error(ResultStatus.SAME_ACCOUNT);
			}
			Account source = accounts.get(fromAccountId);
			Account destination = accounts.get(toAccountId);
			if(source==null || destination==null){
				return ResultModel.error(ResultStatus.NOT_FOUND);
			}
			Money sourceMoney = source.getBalance();
			Money destinationMoney = destination.getBalance();
			if (!sourceMoney.getCurrencyCode().equals(currencyCode) ||
				!destinationMoney.getCurrencyCode().equals(currencyCode)){
				return ResultModel.error(ResultStatus.CONVERSATION_FORBIDDEN);
			}
			if(sourceMoney.getAmount().compareTo(amount) < 0){
				return ResultModel.error(ResultStatus.NO_MONEY);
			}
			sourceMoney.setAmount(sourceMoney.getAmount().subtract(amount));
			destinationMoney.setAmount(destinationMoney.getAmount().add(amount));
			return ResultModel.ok();
		}catch(NumberFormatException e){
			return ResultModel.error(ResultStatus.BAD_FORMAT);
		}catch(JSONException e){
			return ResultModel.error(ResultStatus.JSON_ERROR);
		}catch(Exception e){
			logger.error("Unknown error: ", e);
			return ResultModel.error(ResultStatus.TRANSFER_FAILED);
		}
	}
}
