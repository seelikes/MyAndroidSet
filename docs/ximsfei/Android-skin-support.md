#Android-skin-support框架设计浅析
##1. 是什么？做到了什么？
Android-skin-support是一款换肤框架。  
可以在极简侵入下做到换肤功能，并且具有强大的插件换肤功能。
##2. 设计思路
###2.1 总体框架
Android-skin-support在整体设计上可以分为资源管理及换肤操作、以及协调皮肤加载及换肤操作的AsyncTask三部分。其中资源管理负责管理插件对应的资源，换肤操作负责执行最终的皮肤切换，AsyncTask负责加载皮肤及执行换肤操作。
###2.2 资源管理
####2.2.1 策略模式
资源管理采用了策略模式，分别是：  
>一、SKIN_LOADER_STRATEGY_NONE 没有换肤资源，也即只有一套资源  
>二、SKIN_LOADER_STRATEGY_ASSETS 资产换肤策略，将打包好的皮肤包放置在assets/skins之下  
>三、SKIN_LOADER_STRATEGY_BUILD_IN 应用内换肤，后缀资源名称  
>四、SKIN_LOADER_STRATEGY_PREFIX_BUILD_IN 应用内换肤，前缀资源名称  
>五、SKIN_LOADER_STRATEGY_SDCARD sdcard资源策略
###2.2.2 策略初始化
####2.2.2.1 内置策略初始化
内置实现策略初始化调用路径为:   
```
    // skin.support.SkinCompatManager
    public static SkinCompatManager withoutActivity(Application application) {
        init(application);
        SkinActivityLifecycle.init(application);
        return sInstance;
    }

    // skin.support.SkinCompatManager
    public static SkinCompatManager init(Context context) {
        if (sInstance == null) {
            synchronized (SkinCompatManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinCompatManager(context);
                }
            }
        }
        SkinPreference.init(context);
        return sInstance;
    }

    // skin.support.SkinCompatManager
    private SkinCompatManager(Context context) {
        mAppContext = context.getApplicationContext();
        initLoaderStrategy();
    }

    // skin.support.SkinCompatManager
    private void initLoaderStrategy() {
        mStrategyMap.put(SKIN_LOADER_STRATEGY_NONE, new SkinNoneLoader());
        mStrategyMap.put(SKIN_LOADER_STRATEGY_ASSETS, new SkinAssetsLoader());
        mStrategyMap.put(SKIN_LOADER_STRATEGY_BUILD_IN, new SkinBuildInLoader());
        mStrategyMap.put(SKIN_LOADER_STRATEGY_PREFIX_BUILD_IN, new SkinPrefixBuildInLoader());
    }
```
从以上代码可以看出，内置策略会在SkinCompatManager配置时加入策略缓存Map中
####2.2.2.2 自定义策略初始化
自定义策略需要在换肤逻辑启动之前调用
```
    // skin.support.SkinCompatManager
    public SkinCompatManager addStrategy(SkinLoaderStrategy strategy) {
        mStrategyMap.put(strategy.getType(), strategy);
        return this;
    }
```