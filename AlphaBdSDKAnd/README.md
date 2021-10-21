# Alpha sdk 接入文档

## 一、整体划分

### 1、整个sdk分为基础核心包和事件包

### 2、仓库地址：

```
maven {
    url  "https://dl-maven-android.mintegral.com/repository/alpha_detailroi_and_sdk_common"
}
```



### 3、包依赖：

```
implementation 'com.fxmvp.detailroi:common:1.0.0'
implementation 'com.fxmvp.detailroi:core:1.0.0'
implementation 'com.fxmvp.detailroi:event:1.0.0' //如果不需要上报事件的话，可以不引入该包
```





## 二、使用方法

### 1、基础核心包能力

#### 1.1、初始化（请在application中进行初始化）

`AlphaSDKFactory.getAlphaSDK().init(getApplication(),"current_channel","my_appid");`

#### 1.2、退出应用（需要开发者主动调用，否则无法上报退出）

`AlphaSDKFactory.getAlphaSDK().exit();`

#### 1.3、更新渠道号

`AlphaSDKFactory.getAlphaSDK().updateChannel("new_channel");`

#### 1.4、打开修改调试开关状态 （线上版本务必关闭！！！默认是关闭状态）

`AlphaSDKFactory.getAlphaSDK().enDebug(true);`

### 2、事件包能力

#### 2.1、上报IAP事件

```
UserEventManager m = (UserEventManager) AlphaSDKFactory.getAlphaSDK().getUserEventManager();
IAPEventBean iapEventBean = new IAPEventBean();
iapEventBean.setAmount(1.0f);
iapEventBean.setCurrency("CNY");
iapEventBean.setPayStatus(IAPPayStateEnum.success);
m.sendIAPEvent(iapEventBean);
```

#### 2.2、上报自定义事件

```
        UserEventManager m = (UserEventManager) AlphaSDKFactory.getAlphaSDK().getUserEventManager();
        JSONObject diyData = new JSONObject();
        m.track("diyEventName",diyData);
```