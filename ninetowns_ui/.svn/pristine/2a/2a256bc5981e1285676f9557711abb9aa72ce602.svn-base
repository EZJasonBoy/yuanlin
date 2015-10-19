package com.ninetowns.ui.fragment;



import java.util.List;

import org.apache.http.NameValuePair;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.ninetowns.library.helper.BaseSharedPreferenceHelper;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.ui.R;
import com.ninetowns.ui.widget.dialog.ProgressiveDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 
 * @Title: BaseFragment.java
 * @Description: 是项目中所有处理fragment的基类 对加载数据
 *               、刷新数据、对数据的解析的处理，此类是模版类，所有的具体的业务由子类去实现。
 * @author wuyulong
 * @date 2015-1-7 下午3:43:53
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public abstract class BaseFragment<Result extends Object,Par extends AbsParser>
        extends Fragment {
    private ProgressiveDialog progressDialog;// 获取网络的时候加载数据的进度条
    private static final String SAVE_INSTANCE_STATE_CHILD_FRAGMENT_TAG = "child_fragment_tag";// 存储子类页标签的key值
    private String mCurrentChildFragmentTag;// 子类页标签
    boolean cach = true;// 是否缓存,因项目需求没有缓存所以供以后需求变动扩展用
    private boolean update;// 是否可以刷新
    private boolean loadmore;// 是否可以加载更多

    // 登陆实体类对象

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(SAVE_INSTANCE_STATE_CHILD_FRAGMENT_TAG,
                mCurrentChildFragmentTag);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 
     * @Title: BaseFragment.java
     * @Description: 如果需要网络访问，子类中必须重写，或者调用
     * @author wuyulong
     * @date 2014-7-14 下午1:38:57
     * @param update
     *            是否可以更新数据，loadmore是否可以加在更多，cach是否缓存数据
     * @return void
     */
    protected void onLoadData(boolean update, boolean loadmore, boolean cach) {
        refreshData(update, loadmore, cach);
    }

    private void refreshData(boolean update, boolean loadmore, boolean cach) {
        this.cach = cach;
        this.update = update;
        this.loadmore = loadmore;
        loadData(getApiParmars());
    }

    /**
     * 
     * @Title: BaseFragment.java
     * @Description: 加载数据
     * @author wuyulong
     * @date 2015-1-7 上午10:30:43
     * @param
     * @return void
     */
    public void loadData(RequestParamsNet apiParmars) {
        if (NetworkUtil.isNetworkAvaliable(getActivity())) {// 有网络
            if (update && loadmore) {// 是列表的
                lodaingRefreshBothStart();
                getNetWordData(apiParmars);
            } else if (update && loadmore == false) {// 详情的
//                showProgressDialog();
                loadingRefreshStart();
                getNetWordData(apiParmars);
            } else {
                getNetWordData(apiParmars);
            }
        } else {
            loadingRefreshEnd();
            // 查询数据库
            onNetToast(this.getActivity(), this.getActivity().getResources()
                    .getString(R.string.errcode_network_unavailable));
        }

    }
/**
 * 
* @Title: BaseFragment.java  
* @Description: 请求网络数据 
* @author wuyulong
* @date 2015-1-20 上午11:13:40  
* @param 
* @return void
 */
    public void sendHttpRequest(RequestParamsNet par, HttpMethod httpMethod,
            String httpApi) {
        HttpUtils utils = new HttpUtils();
        	utils.setDes(true);
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(BaseSharedPreferenceHelper.getReqHttpUrl(getActivity())).append("/").append(httpApi);
		printAipUrl(par, stringBuilder);
        utils.send(httpMethod!=null?httpMethod:HttpMethod.GET, stringBuilder.toString(), par, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (update && loadmore) {
                    loadingRefreshEnd();
                } else if (update && loadmore == false) {
                    loadingRefreshEnd();
                }
                if (cach) {
                    // 缓存数据库
                }
//                if (progressDialog != null) {
//                    if (progressDialog.isShowing())
//                        progressDialog.dismiss();
//                }
                if(!TextUtils.isEmpty(responseInfo.result)){//如果返回的数据不为null那么交给解析器解析
                   Par par= setParser(responseInfo.result);
                   @SuppressWarnings("unchecked")
                Result parResult = (Result) par.getParseResult(responseInfo.result);//获得子类的返回的数据
                 getParserResult(parResult);
                }else{
                    
                }

            }

            @Override
            public void onFailure(HttpException error, String msg) {

                if (cach) {// 更新数据失败
                    //
                    // 1.查询数据库有没有数据
                    // 2.有显示，没有提示网络数据错误
                }
                if (update && loadmore) {// 是列表的
                    loadingRefreshEnd();
                }
//                closeProgressDialogFragment();
            
                
            }
        });
    }
/**
 * 
* @Title: printAipUrl 
* @Description: 打印访问接口的地址
* @param @param par
* @param @param stringBuilder    设定文件 
* @return void    返回类型 
* @throws
 */
private void printAipUrl(RequestParamsNet par, StringBuilder stringBuilder) {
	List<NameValuePair> request = par.getQueryStringParams();
	if(request!=null){
		StringBuilder stringBuild=new StringBuilder();
		for (NameValuePair nameValuePair : request) {
			stringBuild.append(nameValuePair.toString()).append("&");
		}
		if(stringBuild.length()>1){
			if(stringBuild.substring(0, stringBuild.length()-1) != null){
				String string=stringBuild.substring(0, stringBuild.length()-1);
				LogUtils.i("访问网络接口-----地址"+ stringBuilder.toString()+string);
			}	
		}
	}

}
    /**
     * 
    * @Title: BaseFragment.java  
    * @Description: 根据网络访问返回的数据获得解析器
    * @author wuyulong
    * @date 2015-1-20 上午11:06:19  
    * @param 
    * @return Par
     */
    public abstract Par setParser(String str);
    public abstract void getParserResult(Result parserResult);

    /**
     * 
     * @Title: BaseFragment.java
     * @Description: 获取网络数据
     * @author wuyulong
     * @date 2015-1-7 下午3:30:01
     * @param setResponseType
     *            ：get方式还是post方式 api：获取网络接口api apiParmars:设置获取网络api的参数
     * @return void
     */
    private void getNetWordData(RequestParamsNet apiParmars) {
        if (apiParmars != null && 
                !TextUtils.isEmpty(apiParmars.getmStrHttpApi())) {
            sendHttpRequest(apiParmars, apiParmars.getmHttpMethod(),
                    apiParmars.getmStrHttpApi());
        } else if (apiParmars == null) {
            LogUtils.e("NetRequestParams-------==null");
            throw new IllegalArgumentException();//抛出不合法的异常
          
        }/* else if (apiParmars.getmHttpMethod() == null) {
            LogUtils.e("NetRequestParams请求方式-------=null");
            throw new IllegalArgumentException();
        }*/ else if (TextUtils.isEmpty(apiParmars.getmStrHttpApi())) {
            LogUtils.e("NetRequestParams接口地址-------=null");
            throw new IllegalArgumentException();
        }

    }






  


    /**
     * 
     * @Title: BaseFragment.java
     * @Description: 获得参访问网络的参数类
     * @author wuyulong
     * @date 2015-1-7 下午3:35:20
     * @param
     * @return RequestParams
     */
    public abstract RequestParamsNet getApiParmars();

    /**
     * 
     * @Title: BaseFragment.java
     * @Description: 开始刷新 只能下拉刷新
     * @author wuyulong
     * @date 2015-1-7 下午3:35:52
     * @param
     * @return void
     */
    protected void loadingRefreshStart() {

    }

    /**
     * 
     * @Title: BaseFragment.java
     * @Description: 上拉刷新和下拉刷新
     * @author wuyulong
     * @date 2015-1-7 下午3:36:09
     * @param
     * @return void
     */
    protected void lodaingRefreshBothStart() {

    }

    /**
     * @Title: BaseFragment.java
     * @Description: 正在刷新
     * @author wuyulong
     * @date 2015-1-7 下午3:37:25
     * @param
     * @return void
     */
    protected void loadingRefreshing() {

    }

    /**
     * 
     * @Title: BaseFragment.java
     * @Description: 刷新结束
     * @author wuyulong
     * @date 2015-1-7 下午3:37:43
     * @param
     * @return void
     */
    protected void loadingRefreshEnd() {

    }

    /**
     * 
     * @Title: BaseFragment.java
     * @Description: 获取网络数据之后返回数据的提示
     * @author wuyulong
     * @date 2015-1-7 下午3:37:58
     * @param
     * @return void
     */
    private void onNetToast(FragmentActivity activity, String str) {
        Toast.makeText(getActivity(), str, 0).show();

    }

    /**
     * 
     * @Title: ComponentUtil.java
     * @Description: 显示dialog
     * @author wuyulong
     * @date 2014-7-14 下午4:23:26
     * @param
     * @return void
     */
    public void showProgressDialog() {
        if ((!getActivity().isFinishing()) && (progressDialog == null)) {
            progressDialog = new ProgressiveDialog(getActivity());
        }
        if (progressDialog != null) {
            progressDialog.setMessage(R.string.loading);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

    }

    /**
     * 
     * @Title: ComponentUtil.java
     * @Description: 取消dialog
     * @author wuyulong
     * @date 2014-7-14 下午4:23:48
     * @param
     * @return void
     */
    public void closeProgressDialogFragment() {
        if (progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }


}
