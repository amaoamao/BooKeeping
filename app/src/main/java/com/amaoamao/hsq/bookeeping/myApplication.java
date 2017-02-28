package com.amaoamao.hsq.bookeeping;

import android.app.Application;

import com.amaoamao.hsq.bookeeping.Entity.Account;
import com.amaoamao.hsq.bookeeping.Entity.Debt;

/**
 * Created by mao on 17-2-27.
 */

public class myApplication extends org.litepal.LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if (Account.count(Account.class) == 0) {
            Account account = new Account();
            account.setName("现金");
            account.setBalance(0.0);
            account.save();
            Account account2 = new Account();
            account2.setName("支付宝");
            account2.setBalance(0.0);
            account2.save();
        }
    }
}
