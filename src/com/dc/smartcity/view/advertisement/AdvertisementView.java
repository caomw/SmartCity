package com.dc.smartcity.view.advertisement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import com.dc.smartcity.activity.CordovaWebwiewActivity;
import com.dc.smartcity.bean.AdObj;
import com.dc.smartcity.util.BundleKeys;

import java.util.ArrayList;

/**
 * 广告专用.
 */
public class AdvertisementView extends BaseImageSwitcher<AdObj> {

    private String mEventId;
    private String mParam;
    private int tagLevel = 0;


    public AdvertisementView(Context context) {
        super(context);
    }

    public AdvertisementView(Context context, int tagLevel) {
        super(context);
        this.tagLevel = tagLevel;
    }

    public void setAdvertisementData(ArrayList<AdObj> datas) {
        setScreenRate(17,10 );
        setData(datas);
    }

    /**
     * 广告的比例
     *
     * @param widthRat  宽度
     * @param heightRat 高度
     */
    public void setAdvertisementRate(int widthRat, int heightRat) {
        setScreenRate(widthRat, heightRat);
    }

    public void setEventId(String eventId, String param) {
        this.mEventId = eventId;
        this.mParam = param;
    }

    @Override
    protected boolean performItemClick(AdapterView<?> parent, View view, int position, long id, int true_position) {
        if (!super.performItemClick(parent, view, position, id, true_position)) {

            final AdObj data_obj = mDatas.get(true_position);

            if (data_obj != null && !TextUtils.isEmpty(data_obj.redirectUrl)) {
                String tag = data_obj.tag;
                Intent intent = new Intent(getContext(), CordovaWebwiewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(BundleKeys.WEBVIEW_LOADURL, data_obj.redirectUrl);
                bundle.putString(BundleKeys.WEBVIEW_TITLE, "详情");
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        }
        return false;
    }

    @Override
    public String getUrlString(AdObj data) {
        return data.imageUrl;
    }

}
