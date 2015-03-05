package com.orange.barrage.android.util.model;

import com.orange.barrage.android.util.config.BarrageConfigManager;
import com.orange.barrage.android.util.network.BarrageNetworkClient;

import javax.inject.Inject;

/**
 * Created by pipi on 15/3/2.
 */
public class CommonMission {

    @Inject
    protected BarrageConfigManager mConfigManager;

    @Inject
    protected BarrageNetworkClient mBarrageNetworkClient;
}
