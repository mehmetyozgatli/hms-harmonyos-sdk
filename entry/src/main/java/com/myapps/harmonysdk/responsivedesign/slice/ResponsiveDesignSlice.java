package com.myapps.harmonysdk.responsivedesign.slice;

import com.myapps.harmonysdk.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Image;
import ohos.agp.components.Text;
import ohos.global.configuration.DeviceCapability;

public class ResponsiveDesignSlice extends AbilitySlice {

    private Button signInWithHuaweiButton;
    private Button signOutWithHuaweiButton;
    private Button cancelAuthWithHuaweiButton;
    private Image logo_wearable;
    private Text welcome_text_wearable;
    private Image logo;
    private Text welcome_text;
    private DirectionalLayout huaweiIdButtonLayout;
    private DirectionalLayout welcomeTextLogoWearableLayout;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_responsive_design);
        initView();
        responsiveDesign();
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private void initView() {
        signInWithHuaweiButton = (Button) findComponentById(ResourceTable.Id_btn_hwid_sign_in);
        signOutWithHuaweiButton = (Button) findComponentById(ResourceTable.Id_btn_hwid_sign_out);
        cancelAuthWithHuaweiButton = (Button) findComponentById(ResourceTable.Id_btn_hwid_cancel_authorization);
        huaweiIdButtonLayout = (DirectionalLayout) findComponentById(ResourceTable.Id_huawei_id_button_layout);
        welcomeTextLogoWearableLayout = (DirectionalLayout) findComponentById(ResourceTable.Id_welcomeText_logo_wearable_layout);
        logo_wearable = (Image) findComponentById(ResourceTable.Id_logo_image_wearable);
        welcome_text_wearable = (Text) findComponentById(ResourceTable.Id_welcome_text_wearable);
        logo = (Image) findComponentById(ResourceTable.Id_logo_image);
        welcome_text = (Text) findComponentById(ResourceTable.Id_welcome_text);
    }

    private void responsiveDesign() {
        int deviceType = getResourceManager().getDeviceCapability().deviceType;
        if (deviceType == DeviceCapability.DEVICE_TYPE_WEARABLE) {
            // Wearable Layout
            welcomeTextLogoWearableLayout.setVisibility(DirectionalLayout.VISIBLE);
            welcomeTextLogoWearableLayout.setMarginTop(10);
            huaweiIdButtonLayout.setMarginBottom(80);

            // Wearable Welcome Text
            welcome_text_wearable.setTextSize(15, Text.TextSizeType.FP);
            welcome_text_wearable.setMarginTop(35);
            welcome_text_wearable.setMarginBottom(5);
            welcome_text_wearable.setText("Welcome to Wearable");

            // Wearable Logo
            logo_wearable.setHeight(80);
            logo_wearable.setWidth(80);
            logo_wearable.setScaleMode(Image.ScaleMode.CLIP_CENTER);

            // Welcome Text
            welcome_text.setVisibility(DirectionalLayout.HIDE);

            // Wearable Logo
            logo.setVisibility(DirectionalLayout.HIDE);

            // Buttons
            signInWithHuaweiButton.setTextSize(12, Text.TextSizeType.FP);
            signInWithHuaweiButton.setHeight(60);
            signInWithHuaweiButton.setMinWidth(360);
            signInWithHuaweiButton.setPadding(20, 2, 20, 2);

            signOutWithHuaweiButton.setTextSize(12, Text.TextSizeType.FP);
            signOutWithHuaweiButton.setHeight(60);
            signOutWithHuaweiButton.setMinWidth(360);
            signOutWithHuaweiButton.setPadding(20, 2, 20, 2);

            cancelAuthWithHuaweiButton.setTextSize(12, Text.TextSizeType.FP);
            cancelAuthWithHuaweiButton.setHeight(60);
            cancelAuthWithHuaweiButton.setMinWidth(360);
            cancelAuthWithHuaweiButton.setPadding(20, 2, 20, 2);

        } else if (deviceType == DeviceCapability.DEVICE_TYPE_TABLET) {
            // Wearable Layout
            welcomeTextLogoWearableLayout.setVisibility(DirectionalLayout.HIDE);

            // Welcome Text
            welcome_text.setVisibility(DirectionalLayout.VISIBLE);
            welcome_text.setText("Welcome to Tablet");
            welcome_text.setTextSize(50, Text.TextSizeType.FP);
            welcome_text.setMarginTop(40);

            // Wearable Logo
            logo.setVisibility(DirectionalLayout.VISIBLE);

        } else if (deviceType == DeviceCapability.DEVICE_TYPE_PHONE) {
            // Wearable Layout
            welcomeTextLogoWearableLayout.setVisibility(DirectionalLayout.HIDE);

            // Welcome Text
            welcome_text.setVisibility(DirectionalLayout.VISIBLE);
            welcome_text.setText("Welcome to Phone");
            welcome_text.setTextSize(35, Text.TextSizeType.FP);
            welcome_text.setMarginTop(60);

            // Wearable Logo
            logo.setVisibility(DirectionalLayout.VISIBLE);

        } else if (deviceType == DeviceCapability.DEVICE_TYPE_TV) {
            // Wearable Layout
            welcomeTextLogoWearableLayout.setVisibility(DirectionalLayout.HIDE);

            // Welcome Text
            welcome_text.setVisibility(DirectionalLayout.VISIBLE);
            welcome_text.setText("Welcome to TV");
            welcome_text.setTextSize(65, Text.TextSizeType.FP);
            welcome_text.setMarginTop(60);

            // Wearable Logo
            logo.setVisibility(DirectionalLayout.VISIBLE);
            logo.setHeight(400);
            logo.setWidth(400);
            logo.setScaleMode(Image.ScaleMode.CLIP_CENTER);
        }
    }
}
