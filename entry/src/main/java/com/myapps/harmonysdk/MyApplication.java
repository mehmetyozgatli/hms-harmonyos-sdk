package com.myapps.harmonysdk;

import com.huawei.agconnect.AGConnectInstance;
import com.huawei.hms.accountsdk.exception.ApiException;
import com.huawei.hms.accountsdk.support.account.AccountAuthManager;
import com.huawei.hms.accountsdk.support.account.tasks.Task;
import ohos.aafwk.ability.AbilityPackage;

public class MyApplication extends AbilityPackage {

    @Override
    public void onInitialize() {
        super.onInitialize();
        initHuaweiAccountSDK();
        initializeAGC();
    }

    @Override
    public void onEnd() {
        super.onEnd();
    }

    private void initHuaweiAccountSDK() {
        Task<Void> task;
        try {
            // Call AccountAuthManager.init for the initialization.
            task = AccountAuthManager.init(this);
        } catch (ApiException apiException) {
            apiException.getStatusCode();
            return;
        }
        task.addOnSuccessListener(e -> {
            // The initialization is successful.
        });

        task.addOnFailureListener(e -> {
            // The SDK fails to be initialized.
            if (e instanceof ApiException) {
                ApiException apiException = (ApiException) e;
                // Find the failure cause from the status code. For details, please refer to Error Codes.
                apiException.getStatusCode();
            }
        });
    }

    private void initializeAGC() {
        AGConnectInstance.initialize(MyApplication.this);
    }
}
