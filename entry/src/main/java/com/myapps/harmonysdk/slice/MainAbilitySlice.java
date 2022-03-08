package com.myapps.harmonysdk.slice;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;
import com.myapps.harmonysdk.ResourceTable;
import com.myapps.harmonysdk.appmessaging.slice.AppMessagingSlice;
import com.myapps.harmonysdk.authservice.slice.AuthServiceDetailSlice;
import com.myapps.harmonysdk.authservice.slice.AuthServiceSlice;
import com.myapps.harmonysdk.responsivedesign.slice.ResponsiveDesignSlice;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.CommonDialog;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class MainAbilitySlice extends AbilitySlice {

    private Button responsiveDesignButton;
    private Button authServiceButton;
    private Button appMessagingButton;
    private Boolean checkUser = false;

    // Dialog Box Issue
   /* private MmiPoint downPoint;
    private Float endPositionX;
    private Float startPositionX;*/

    private CommonDialog dialog;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        initView();

        responsiveDesignButton.setClickedListener(component ->
                present(new ResponsiveDesignSlice(), new Intent()));

        authServiceButton.setClickedListener(component -> {
            if (!checkCurrentUser()) {
                present(new AuthServiceSlice(), new Intent());
            }
        });

        appMessagingButton.setClickedListener(component -> {
            // present(new AppMessagingSlice(), new Intent()));

            // Example for close CustomDialog with swipe right
            Component container = LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_layout_custom_dialog, null, false);
            dialog = new CommonDialog(getContext());
            dialog.setContentCustomComponent(container);
            dialog.setSize(MATCH_CONTENT, MATCH_CONTENT);
            dialog.show();
            dialog.setSwipeToDismiss(true);
        });


        // Dialog Box Swipe Right Issue -- Custom Solution
/*        responsiveDesignButton.setClickedListener(component -> {
            Component container = LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_layout_custom_dialog, null, false);
            dialog = new CommonDialog(getContext());
            dialog.setContentCustomComponent(container);
            dialog.setSize(MATCH_CONTENT, MATCH_CONTENT);
            dialog.show();

            container.setTouchEventListener(new Component.TouchEventListener() {
                @Override
                public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
                    if(touchEvent.getAction() == TouchEvent.PRIMARY_POINT_DOWN){
                        downPoint = touchEvent.getPointerScreenPosition(0);
                        startPositionX = downPoint.getX();
                    }
                    if(touchEvent.getAction() == TouchEvent.PRIMARY_POINT_UP){
                        MmiPoint movePoint = touchEvent.getPointerScreenPosition(0);
                        endPositionX = movePoint.getX();
                        if(startPositionX<endPositionX){
                            dismissDialog();
                        }else{

                        }
                    }
                    return true;
                }
            });
        });*/
    }

/*    public void dismissDialog() {
        if (dialog != null) {
            dialog.destroy();
        }
    }*/

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private boolean checkCurrentUser() {
        AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
        if (user != null) {
            checkUser = true;
            present(new AuthServiceDetailSlice(), new Intent());
        }
        return checkUser;
    }

    private void initView() {
        responsiveDesignButton = (Button) findComponentById(ResourceTable.Id_responsive_design_button);
        authServiceButton = (Button) findComponentById(ResourceTable.Id_auth_service_button);
        appMessagingButton = (Button) findComponentById(ResourceTable.Id_app_messaging_button);
    }
}
