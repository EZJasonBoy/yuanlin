package com.ninetowns.tootooplus.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.ninetowns.tootooplus.bean.MobileAlbumBean;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;


/**
 * 
* @ClassName: CoverRecommendSelectActivity 
* @Description: 相册界面
* @author wuyulong
* @date 2015-1-29 上午10:22:33 
*
 */
public class CoverRecommendSelectActivity extends MobileAlbumActivity{
    private Bundle intentbundle;

    @Override
    public void getType() {
       intentbundle= getIntent().getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
    }
    
    @Override
    public void onitemclick(List<MobileAlbumBean> mobileAlbumBeans, int position) {
        Intent photo_intent = new Intent(this,AlbumPhotoCHRecommendConvertActivity.class);
        //从本地查出来的字段
        photo_intent.putExtra("folder_name", mobileAlbumBeans.get(position).getMob_album_folder_name());
        photo_intent.putExtra(ConstantsTooTooEHelper.BUNDLE, intentbundle);
        startActivity(photo_intent);
        finish();
    }

}
