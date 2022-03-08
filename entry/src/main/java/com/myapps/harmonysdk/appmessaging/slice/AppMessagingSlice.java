package com.myapps.harmonysdk.appmessaging.slice;

import com.huawei.agconnect.appmessaging.AGConnectAppMessaging;
import com.huawei.agconnect.common.api.AGCInstanceID;
import com.myapps.harmonysdk.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class AppMessagingSlice extends AbilitySlice {

    private final static HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x0001, "appMessaging_demo");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_app_messaging);

        Button createShort = (Button) findComponentById(ResourceTable.Id_button_debug);
        createShort.setClickedListener(component -> debugMessage());

        AGConnectAppMessaging.getInstance().setFetchMessageEnable(true);
        AGConnectAppMessaging.getInstance().setDisplayEnable(true);

    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private void debugMessage() {
        /*
         * Step 1
         * Obtain the AAID of the device and add the AAID to the AppGallery Connect server. For details, please refer to the development guide.
         */
        String aaid =  AGCInstanceID.getInstance().getId();
        HiLog.debug(LABEL, aaid );

        /*
         *  Step 2
         *  Call the setForceFetch API.
         *  Note: The API can be called only during version tests. Do not use it for officially released versions.
         */
        AGConnectAppMessaging.getInstance().setForceFetch();

        /*
         * Step 3
         * Trigger an event so the SDK can synchronize test device data from the AppGallery Connect server. (Ensure that the AAID of the test device has been added to the AppGallery Connect server.）
         * For example, you can press Home to exit the app and open the app again at least one second later.
         */

        /*
         * Step 4
         * Check logs to see if the test device has been added successfully.
         * Log: This device is a test device
         */
    }
}
