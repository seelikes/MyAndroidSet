package com.example.myjetpackapplication.plugin.realm

/**
 * Created by liutiantian on 2020-01-02 12:55 星期四
 */
class RealmExtension {
    /**
     * 前缀报名
     */
    String packageName

    /**
     * 资产数据库文件路径
     */
    String assetFile

    /**
     * 压缩数据库文件，使用默认的回调处理器
     */
    boolean compactOnLaunch

    /**
     * 升级时删除数据库
     */
    boolean deleteRealmIfMigrationNeeded

    /**
     * 数据库文件夹地址
     */
    String directory

    /**
     * 是否是内存数据库
     */
    boolean inMemory

    /**
     * 数据库名字
     */
    String name

    /**
     * 是否只读
     */
    boolean readOnly

    /**
     * 数据库版本号
     */
    long schemaVersion

    /**
     * 数据库密钥
     */
    String encryptionKey
}
