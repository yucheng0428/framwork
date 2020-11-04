# framwork
组件化快速开发基础框架。
目前有4个模块，

#picturecontrol
是图片选择工具模块，后续应该还要接入直播，视频，录音，语音等功能
cameralivary里面是可自定义的相机组件和查看大图功能，tools里面主要是图片压缩工具类和拍照，选择图片的入口dialog
album是相册工具类，用于展示获取本地的图片 提供选择界面，文件的选择没有优化目前没需求等后续有类似需要时写一个
view Attachview 自定义的控件，用于展示，添加图片照片，RoundProgreeBar是进度展示效果，都已封装进AttachView中
ReqFileUtils 是对图片上传地址 和过程做处理的工具类


#libcommon,
基础功能包，
netHttp网络请求框架Retrfit,rxjava,butterknife注解，用的Retrofit2,所以请求封装的是FlowabBaseSubscrber,
HttpService 是OKhttp,Retrofit单例工具封装，基本请求直接用它调用方法就可以了
动态权限工具，permssion,建议使用这个获取权限PermissionCheckUtils.requestPermissions(activity, Constants.REQUEST_CODE, new String[]); // 动态请求权限
Base中封装了 act,frgm,application基类
Basemvp抽象 封装基类 具体的说明后面我再仔细阅读修改后写个详细的说明
scanning 是二维扫描集成,功能的是健全的，看需求对应做修改吧
widgt 是几个列表选择框 
update是一个应用升级工具的封装


#beacon-lib
是一款蓝牙信标定位sdk封装的模块，里面有蓝牙扫描，信标获取，定位服务，本地的3d地图静态资源。
assets 静态地图资源文件
beaconSensor是信标的处理工具包，具体代码是SDK里拿的不详细说明 有问题自己翻代码读逻辑。
bean里面是一些实体类，主要是地图坐标，蓝牙信标的参数
location里面是几个服务，有保活，拉起，定位上传服务，主要的业务逻辑基本就在定位上传服务中SensorManageService，如果后面有什么电源，保活优化的需求就看看其他的类
html是个专门展示地图的工具webview,里面主要展示地图，然后与地图js做交互，说的通俗点就是H5页面与原生WEBView交互


#main
模块是主页模块，包含登录功能，主页展示功能，如果开发新项目 基本代码都在这个里面写，
现在就两个通用的功能在里面， 一个登陆一个主页，后面如果用的时候 换新分支 最好开新项目

#说明
该框架里面集成了阿里的路由，每个模块也做了对应的单独运行的配置，通过seeting中的开关控制，可单独调试每个模块，也可集成调式。
目前封装的效果完成初步的代码封装 抽样，libcommon还有不少可优化的代码，目前libcommon有点臃肿，抽样的层度还不够，还需要的好好学习设计模式来抽象调整框架的精简度
