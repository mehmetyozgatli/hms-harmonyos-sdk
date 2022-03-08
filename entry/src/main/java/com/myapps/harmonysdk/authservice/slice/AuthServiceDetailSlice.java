package com.myapps.harmonysdk.authservice.slice;

import com.bumptech.glide.Glide;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.ProfileRequest;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.hmf.tasks.HarmonyTask;
import com.myapps.harmonysdk.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class AuthServiceDetailSlice extends AbilitySlice {

    private final static HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x0001, "auth_demo");

    private Image userImage;
    private Text userName;
    private Text mobilePhone;
    private Button signOut;
    private Button updateProfile;
    private CircularProgressView progressView;

    private AGConnectUser user;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_auth_service_detail);

        // Init UI Components
        setUIComponent();
        setVisibility(Component.VISIBLE);

        // User Information
        user = AGConnectAuth.getInstance().getCurrentUser();
        setUserData();

        // Sign Out
        signOut.setClickedListener(component -> signOut());

        // Update Profile due to creating a new user with Phone Number
        updateProfile.setClickedListener(component -> {
            updateProfile();
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

    private void setUserData() {
        // Set Data
        if (user != null){
            // User Image
            Glide.with(getContext())
                    .load(user.getPhotoUrl())
                    .circleCrop()
                    .into(userImage);

            // User Name
            userName.setText(user.getDisplayName());
            // Phone Number
            mobilePhone.setText(user.getPhone());
        }
    }

    private void updateProfile() {
        setVisibility(Component.INVISIBLE);
        ProfileRequest.Builder profileRequest = new ProfileRequest.Builder();
        profileRequest.setPhotoUrl("https://serving.photos.photobox.com/019960973fb413bc868a5bc091eec7149231a96fff565af1aa06dd02b9a0ad779eb57702.jpg");
        profileRequest.setDisplayName("Mehmet Yozgatli");
        HarmonyTask<Void> task = user.updateProfile(profileRequest.build());
        task.addOnSuccessListener(updateResult -> {
            log("update success");
            setVisibility(Component.VISIBLE);
            setUserData();
        }).addOnFailureListener(e -> {
            log("signInWithCode fail:" + e);
            setVisibility(Component.VISIBLE);
        });
    }

    private void signOut() {
        AGConnectAuth.getInstance().signOut();
        present(new AuthServiceSlice(), new Intent());
        terminate();
    }

    private void setUIComponent() {
        userImage = (Image) findComponentById(ResourceTable.Id_user_image);
        userName = (Text) findComponentById(ResourceTable.Id_name);
        mobilePhone = (Text) findComponentById(ResourceTable.Id_mobilePhone);
        signOut = (Button) findComponentById(ResourceTable.Id_SignOut);
        updateProfile = (Button) findComponentById(ResourceTable.Id_updateProfile);
        progressView = (CircularProgressView) findComponentById(ResourceTable.Id_cpv_authService_detail);
    }

    private void setVisibility(Integer visibility) {
        if (visibility == Component.VISIBLE) {
            userName.setVisibility(visibility);
            mobilePhone.setVisibility(visibility);
            updateProfile.setVisibility(visibility);
            signOut.setVisibility(visibility);
            progressView.setVisibility(Component.INVISIBLE);
        } else if (visibility == Component.INVISIBLE) {
            userName.setVisibility(visibility);
            mobilePhone.setVisibility(visibility);
            updateProfile.setVisibility(visibility);
            signOut.setVisibility(visibility);
            progressView.setVisibility(Component.VISIBLE);
        }
    }

    private void log(String format, Object... args) {
        HiLog.debug(LABEL, format, args);
    }
}
