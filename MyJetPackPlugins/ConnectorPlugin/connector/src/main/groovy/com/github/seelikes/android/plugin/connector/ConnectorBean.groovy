package com.github.seelikes.android.plugin.connector

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.github.seelikes.android.plugin.connector.annotation.Connector
import javassist.CtClass
import org.jetbrains.annotations.NotNull

/**
 * Created by liutiantian on 2020-06-21 20:36 星期日
 */
class ConnectorBean {
    /**
     * 注册信息
     */
    Connector connector

    /**
     * 注解宿主类对象
     */
    CtClass ctClass

    @Override
    int hashCode() {
        int hash = super.hashCode()
        if (ctClass != null) {
            hash = hash / 2 + ctClass.hashCode() / 2
        }
        return hash
    }

    @Override
    boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true
        }
        if (obj instanceof ConnectorBean) {
            return ctClass == obj.ctClass
        }
        return false
    }

    String getInterfaceClass() {
        String connector = this.connector.toString()
        int valueIndex = connector.indexOf("value=")
        if (valueIndex != -1) {
            int endIndex = connector.indexOf(".class", valueIndex)
            if (endIndex != -1) {
                return connector.substring(valueIndex + 6, endIndex)
            }
        }
        return null
    }
}
