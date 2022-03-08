package com.myapps.harmonysdk.authservice.slice;

import com.huawei.agconnect.auth.*;
import com.huawei.hmf.tasks.HarmonyTask;
import com.huawei.hmf.tasks.TaskExecutors;
import com.myapps.harmonysdk.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.TextField;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class AuthServiceRegisterSlice extends AbilitySlice {

    private final static HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x0001, "auth_demo");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_auth_service_register);

        Button verify = (Button) findComponentById(ResourceTable.Id_VerifyRegister);
        verify.setClickedListener(component -> verify());

        Button register = (Button) findComponentById(ResourceTable.Id_Register);
        register.setClickedListener(component -> register());
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private void verify() {
        String countryCode = getCountryCode();
        String phoneNumber = getPhoneNumber();

        int action = VerifyCodeSettings.ACTION_REGISTER_LOGIN;
        VerifyCodeSettings settings = VerifyCodeSettings.newBuilder()
                .action(action)
                .sendInterval(30)
                .build();

        HarmonyTask<VerifyCodeResult> task = AGConnectAuth.getInstance()
                .requestVerifyCode(countryCode, phoneNumber, settings);

        task.addOnSuccessListener(TaskExecutors.uiThread(), verifyCodeResult -> log("verifyPhoneCode success"))
                .addOnFailureListener(TaskExecutors.uiThread(), e -> log("verifyPhoneCode fail:" + e));

    }

    private void register(){
        String countryCode = getCountryCode();
        String phoneNumber = getPhoneNumber();
        String verifyCode = getVerify();
        String password = getPassword();

        PhoneUser phoneUser = new PhoneUser.Builder()
                .setCountryCode(countryCode)
                .setPhoneNumber(phoneNumber)
                .setVerifyCode(verifyCode)
                .setPassword(password)  //optional
                .build();
        AGConnectAuth.getInstance().createUser(phoneUser)
                .addOnSuccessListener(signInResult -> {
                    // After an account is created, the user has signed in by default.
                    present(new AuthServiceDetailSlice(), new Intent());
                    terminate();
                })
                .addOnFailureListener(e -> {
                    showToast();
                    log("register fail:" + e);
                });
    }


    private String getCountryCode() {
        TextField field = (TextField) findComponentById(ResourceTable.Id_countryCodeRegister);
        return field.getText();
    }

    private String getPhoneNumber() {
        TextField field = (TextField) findComponentById(ResourceTable.Id_phoneNumberRegister);
        return field.getText();
    }

    private String getPassword() {
        TextField field = (TextField) findComponentById(ResourceTable.Id_passwordRegister);
        return field.getText();
    }

    private String getVerify() {
        TextField field = (TextField) findComponentById(ResourceTable.Id_verifyCodeRegister);
        return field.getText();
    }

    private void log(String format, Object... args) {
        HiLog.debug(LABEL, format, args);
    }

    private void showToast(){
        DirectionalLayout toastLayout = (DirectionalLayout) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_layout_custom_toast, null, false);
        //toastMessage.setText(message);
        new ToastDialog(getContext())
                .setContentCustomComponent(toastLayout)
                .setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT)
                .setAlignment(LayoutAlignment.CENTER)
                .show();
    }
}

