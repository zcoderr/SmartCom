package xyz.zhenhua.smartcom.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

import xyz.zhenhua.smartcom.R;

import static android.R.attr.button;
import static android.R.attr.centerBright;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private int POS = 1;
    private BDLocationListener myListener = new MyLocationListener();// 注册定位监听，返回定位的结果
    private LocationClientOption.LocationMode mCurrentMode;// 定位模式
    private BitmapDescriptor mCurrentMarker;
    public BaiduMap mBaiduMap;
    public MapView mapView;
    MapStatus mMapStatus;
    Button backMyLocation;
    SharedPreferences sharedPreferences;
    LocationManager locationManager;
    public LocationClient mLocationClient = null; // LocationClient类是定位sdk的核心类
    LatLng myLoc;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = (MapView) getView().findViewById(R.id.map_home);
        backMyLocation = (Button)getView().findViewById(R.id.btn_home_back);
        mBaiduMap = mapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        // 初始化定位
        mLocationClient = new LocationClient(getContext());
        // 注册定位监听
        mLocationClient.registerLocationListener(myListener);
        // 配置定位SDK参数
        initLocation();
        //注册监听事件

        backMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationClientOption option = new LocationClientOption();
                // option.setLocationMode(LocationMode.Hight_Accuracy);//
                // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
                // 坐标类型分为三种：国测局经纬度坐标系(gcj02)，百度墨卡托坐标系(bd09)，百度经纬度坐标系(bd09ll)。
                option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
                int span = 0;
                option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
                option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
                option.setOpenGps(true);// 可选，默认false,设置是否使用gps
                option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
                option.setNeedDeviceDirect(true);// 返回的结果包含手机的方向
                // option.setLocationNotify(true);//
                // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
                // option.setIsNeedLocationDescribe(true);//
                // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
                option.setIsNeedLocationPoiList(true);//
                // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
                // option.setIgnoreKillProcess(false);//
                // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
                // option.SetIgnoreCacheException(false);//
                // 可选，默认false，设置是否收集CRASH信息，默认收集
                // option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
                mLocationClient.setLocOption(option);
                mLocationClient.start();// 开始定位

            }
        });
    }


    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        // option.setLocationMode(LocationMode.Hight_Accuracy);//
        // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        // 坐标类型分为三种：国测局经纬度坐标系(gcj02)，百度墨卡托坐标系(bd09)，百度经纬度坐标系(bd09ll)。
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 返回的结果包含手机的方向
        // option.setLocationNotify(true);//
        // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        // option.setIsNeedLocationDescribe(true);//
        // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//
        // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        // option.setIgnoreKillProcess(false);//
        // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        // option.SetIgnoreCacheException(false);//
        // 可选，默认false，设置是否收集CRASH信息，默认收集
        // option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();// 开始定位




    }
    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }

            mBaiduMap.setMyLocationEnabled(true);
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.drawable.home);
            myLoc =  new LatLng(location.getLatitude(),location.getLongitude());
            mMapStatus = new MapStatus.Builder()
                    .zoom(20)
                    .target(myLoc)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
            mBaiduMap.setMapStatus(mMapStatusUpdate);

            //保存坐标到本地
            sharedPreferences = getContext().getSharedPreferences("location",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("x",location.getLatitude()+"");
            editor.putString("y",location.getLongitude()+"");
            editor.commit();

//            //设定中心点坐标
//            LatLng cenpt =  new LatLng(location.getLatitude(),location.getLongitude());
////定义地图状态
//            MapStatus mMapStatus = new MapStatus.Builder()
//                    .target(cenpt)
//                    .build();
////定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//
//            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
////改变地图状态
//            mBaiduMap.setMapStatus(mMapStatusUpdate);


            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
                Log.i("BaiduLocationApiDem", sb.toString());
            }


        }




    }

}
