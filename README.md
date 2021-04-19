# framwork 修改时间2020-11-4
组件化集成开发框架。


#picturecontrol
是图片选择工具模块，后续如果需要直播，视频，录音，语音等功能在这个库里添加
cameralivary里面是可自定义的相机组件和查看大图功能，tools里面主要是图片压缩工具类和拍照，选择图片的入口dialog
album是相册工具类，用于展示获取本地的图片 提供选择界面，文件的选择没有优化目前没需求等后续有类似需要时写一个
view Attachview 自定义的控件，用于展示，添加图片照片，RoundProgreeBar是进度展示效果，都已封装进AttachView中
ReqFileUtils 是对图片上传地址 和过程做处理的工具类


#libcommon,
基础功能包，
netHttp网络请求框架Retrfit,rxjava,butterknife注解，用的Retrofit2,所以请求封装的是FlowabBaseSubscrber,
HttpService 是OKhttp,Retrofit单例工具封装，基本请求直接用它调用方法就可以了
动态权限工具，permssion,建议使用这个获取权限PermissionCheckUtils.requestPermissions(activity, Constants.REQUEST_CODE, new String[]); // 动态请求权限
Base中封装了 actitity,fragment,application基类
Basemvp抽象 封装基类 具体的说明后面我再仔细阅读修改后写个详细的说明
scanning 是二维扫描集成,功能的是健全的，看需求对应做修改吧
widgt 是几个列表选择框,地理位置,时间等可扩展优化 
update是一个应用升级工具的封装


#beacon-lib
是一款蓝牙信标定位sdk封装的模块，里面有蓝牙扫描，信标获取，定位服务，本地的3d地图静态资源。
assets 静态地图资源文件
beaconSensor是信标的处理工具包，具体代码是SDK里拿的不详细说明 有问题自己翻代码读逻辑。
bean里面是一些实体类，主要是地图坐标，蓝牙信标的参数
location里面是几个服务，有保活，拉起，定位上传服务，主要的业务逻辑基本就在定位上传服务中SensorManageService，关于电源优化，保活优化的方案实现可以在这里做一些调整优化。
html是个专门展示地图的工具webview,里面主要展示地图，然后与地图js做交互，说的通俗点就是H5页面与原生WEBView交互


#main
模块是主页模块，包含登录功能，主页展示功能，如果开发新项目 基本代码都在这个里面写，
现在就两个通用的功能在里面， 一个登陆一个主页，后面如果用的时候 换新分支 最好开新项目

#baiduMap
百度地图sdk集成,完成针对百度地图功能的界面和工具类都写在这里
service包下 集成了高德地图 百度地图的后台定位 具体用法参考厂商的api


#bracelet_lib 
这个是健康手环的应用,集成了手环的SDK 可单独调试可集成调试

#watch_app
健康守护应用的业务代码



#说明
该框架里面集成了阿里的路由，每个模块也做了对应的单独运行的配置，通过gradle.properties中的开关控制，可单独调试每个模块，也可集成调式。
目前封装的效果完成初步的代码封装 抽样，libcommon还有不少可优化的代码，目前libcommon有点臃肿，抽像的程度还有优化空间，可以合理的运用设计模式来抽象调整框架的精简度

　打包jar,arr，在 build.gradle 文件中加入如下代码： 
https://www.cnblogs.com/xinaixia/p/7660173.html

复制代码
task clearJar(type: Delete) {
    delete 'build/TestJar_V1.0.jar' //jar包的名字，随便命名 
}
task makeJar(type:org.gradle.api.tasks.bundling.Jar) {
    //指定生成的jar名 
    baseName 'TestJar_V1.0'
    //从哪里打包class文件 
    from('build/intermediates/bundles/default/')
    //打包到jar后的目录结构 
    into('build/')
    //去掉不需要打包的目录和文件 
    exclude('test/', 'BuildConfig.class', 'R.class')
    //去掉R开头的文件  
    exclude{it.name.startsWith('R');}
}
makeJar.dependsOn(clearJar, build)

