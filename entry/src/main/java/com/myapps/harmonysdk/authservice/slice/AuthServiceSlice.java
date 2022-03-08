package com.myapps.harmonysdk.authservice.slice;

import com.huawei.agconnect.auth.*;
import com.huawei.hmf.tasks.HarmonyTask;
import com.myapps.harmonysdk.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class AuthServiceSlice extends AbilitySlice {

    private final static HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x0001, "auth_demo");

    //private Text toastMessage;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_auth_service);

        //toastMessage = (Text) findComponentById(ResourceTable.Id_msg_toast);

        // Check whether a user has signed in.
        checkCurrentUser();

        Button login = (Button) findComponentById(ResourceTable.Id_Login);
        login.setClickedListener(component -> login());

        Button registerPage = (Button) findComponentById(ResourceTable.Id_RegisterPage);
        registerPage.setClickedListener(component -> {
            present(new AuthServiceRegisterSlice(), new Intent());
            terminate();
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private void checkCurrentUser() {
        AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
        if (user != null) {
            present(new AuthServiceDetailSlice(), new Intent());
            terminate();
        }
    }

    private void login() {
        String countryCode = getCountryCode();
        String phoneNumber = getPhoneNumber();
        String password = getPassword();

        /* create a phone credential
         * the credential used for login.
         * password and verifyCode can not be both null.
         */
        AGConnectAuthCredential phoneAuthCredential =
                PhoneAuthProvider.credentialWithPassword(countryCode, phoneNumber, password);

        /* signIn */
        HarmonyTask<SignInResult> task = AGConnectAuth.getInstance().signIn(phoneAuthCredential);
        task.addOnSuccessListener(signInResult -> {
            log("signIn success");
            //showUser(signInResult.getUser());
            present(new AuthServiceDetailSlice(), new Intent());
            terminate();
        }).addOnFailureListener(e -> {
            log("signInWithCode fail:" + e);
            showToast();

        });
    }

    private String getCountryCode() {
        TextField field = (TextField) findComponentById(ResourceTable.Id_countryCode);
        return field.getText();
    }

    private String getPhoneNumber() {
        TextField field = (TextField) findComponentById(ResourceTable.Id_phoneNumber);
        return field.getText();
    }

    private String getPassword() {
        TextField field = (TextField) findComponentById(ResourceTable.Id_password);
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
