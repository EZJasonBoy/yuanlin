package com.ninetowns.tootooplus.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.adapter.HomePageAdapter;
import com.ninetowns.tootooplus.bean.HomePageBean;
import com.ninetowns.tootooplus.parser.HomePageParser;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase.Mode;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;

public class DemoHomePageFragment extends PageListFragment<ListView,List<HomePageBean>,HomePageParser>{
    private View mHomePageFragmentView;
    private ListView mHomePageRefreshListView;
    @Override
    protected View onCreateFragmentView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        mHomePageFragmentView = inflater.inflate(R.layout.home_page_fragment, null);
        ViewUtils.inject(this, mHomePageFragmentView); //注入view和事件
        return mHomePageFragmentView;
    }

    @Override
    protected PullToRefreshBase<ListView> initRefreshIdView() {
        RefreshableListView refresh = (RefreshableListView) mHomePageFragmentView
                .findViewById(R.id.refresh_home_page_list);
        mHomePageRefreshListView = refresh.getRefreshableView();
        refresh.setMode(Mode.DISABLED);
//        PauseOnScrollListener listener = new PauseOnScrollListener(ImageLoader.getInstance(), true, true);
//        mRefresh.setOnScrollListener(listener);
        mHomePageRefreshListView.setFastScrollEnabled(false);
        return refresh;
    }


 

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onLoadData(true, false, false);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public HomePageParser setParser(String str) {
        HomePageParser homePageParser=new HomePageParser(str);
        return homePageParser;
     
    }


    @Override
    public RequestParamsNet getApiParmars() {
        RequestParamsNet requestPar=new RequestParamsNet();
        requestPar.setmStrHttpApi("story/singleProduct.htm?");
        return requestPar;
    }



    @Override
    public int getTotalPage() {
        return 0;
    }


    @Override
    public void getPageListParserResult(List<HomePageBean> parserResult) {
        HomePageAdapter homePageAdatper=new HomePageAdapter(getActivity(),parserResult);
        mHomePageRefreshListView.setAdapter(homePageAdatper);
        homePageAdatper.notifyDataSetChanged();
        
    
    }



}
