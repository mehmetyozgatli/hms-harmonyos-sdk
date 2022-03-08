package com.myapps.harmonysdk;

import com.myapps.harmonysdk.appmessaging.slice.AppMessagingSlice;
import com.myapps.harmonysdk.authservice.slice.AuthServiceDetailSlice;
import com.myapps.harmonysdk.authservice.slice.AuthServiceRegisterSlice;
import com.myapps.harmonysdk.authservice.slice.AuthServiceSlice;
import com.myapps.harmonysdk.responsivedesign.slice.ResponsiveDesignSlice;
import com.myapps.harmonysdk.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());

        setSwipeToDismiss(true);

        addActionRoute("action.appmessaging", AppMessagingSlice.class.getName());
        addActionRoute("action.authservice", AuthServiceSlice.class.getName());
        addActionRoute("action.authserviceregister", AuthServiceRegisterSlice.class.getName());
        addActionRoute("action.authservicedetail", AuthServiceDetailSlice.class.getName());
        addActionRoute("action.responsivedesign", ResponsiveDesignSlice.class.getName());

    }
}
