package xyz.zhenhua.smartcom.activity;

import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import xyz.zhenhua.smartcom.BNDemoMainActivity;
import xyz.zhenhua.smartcom.R;
import xyz.zhenhua.smartcom.entity.LatLon;

import static com.baidu.mapapi.BMapManager.getContext;
import static xyz.zhenhua.smartcom.R.drawable.marker;

public class HelpListActivity extends AppCompatActivity {
    private BDLocationListener myListener = new MyLocationListener();// 注册定位监听，返回定位的结果
    private LocationClientOption.LocationMode mCurrentMode;// 定位模式
    private BitmapDescriptor mCurrentMarker;
    private double StartX,StartY;
    public BaiduMap mBaiduMap;
    public MapView mapView;
    MapStatus mMapStatus;
    List<LatLon> HelpList;

    LocationManager locationManager;
    public LocationClient mLocationClient = null; // LocationClient类是定位sdk的核心类
    LatLng myLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_list);
        mapView = (MapView)findViewById(R.id.helplist_map);
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

        HelpList = new ArrayList<LatLon>();
        LatLon a = new LatLon();
        a.setLat(45.727000);
        a.setLon(126.65900);
        LatLon b = new LatLon();
        b.setLat(45.727100);
        b.setLon(126.65100);
        LatLon c = new LatLon();
        c.setLat(45.727200);
        c.setLon(126.65200);
        HelpList.add(a);
        HelpList.add(b);
        HelpList.add(c);
        for (LatLon l:HelpList){
            addMarker(l.getLat(),l.getLon());
        }
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


    public void addMarker(double x,double y){
        final double endX = x;
        final double endY = y;
        //定义Maker坐标点
        LatLng point = new LatLng(x, y);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.street_view);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        Marker mapmarker = (Marker) mBaiduMap.addOverlay(option);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getContext(), "Marker被点击了！", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putDouble("sNodeX",StartX);
                bundle.putDouble("sNodeY",StartY);
                bundle.putDouble("eNodeX",endX);
                bundle.putDouble("eNodeY",endY);
                Intent intent = new Intent(HelpListActivity.this, BNDemoMainActivity.class);
                intent.putExtra("sAnde",bundle);
                startActivity(intent);
                return false;
            }
        });
    }

    public void SetCurLoc(double startX,double startY){
        StartX = startX;
        StartY = startY;
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
            //设置当前位置和缩放级别
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
            //设置当前位置
            SetCurLoc(location.getLatitude(),location.getLongitude());
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
