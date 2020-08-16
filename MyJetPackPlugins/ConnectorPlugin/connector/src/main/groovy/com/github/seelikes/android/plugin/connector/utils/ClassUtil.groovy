package com.github.seelikes.android.plugin.connector.utils

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.CtNewMethod
import javassist.bytecode.SignatureAttribute

/**
 * Created by liutiantian on 2020-08-08 19:29 星期六
 */
class ClassUtil {
    static CtClass makeInitializer(CtClass ctClass, String name, int argumentCount) {
        if (ctClass == null || name == null || name.isEmpty()) {
            return null
        }
        String ci = "com.github.seelikes.android.plugin.connector.api.ConnectorInitializer"
        CtClass cc = ClassPool.default.makeClass(name)
        CtClass ConnectorInitializer = ClassPool.default.get(ci)
        cc.setInterfaces([ConnectorInitializer])
        SignatureAttribute.ClassSignature cs = new SignatureAttribute.ClassSignature(null, null, [
            new SignatureAttribute.ClassType(
                ci,
                [
                    new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(ctClass.name))
                ]
            )
        ])
        cc.setGenericSignature(cs.encode())

        StringBuilder createClause = new StringBuilder()
        if (argumentCount == 0) {
            createClause.append("if (args == null || args.length == 0)) {\n")
            createClause.append("${it4}return new $name();\n")
            createClause.append("}\n")
            createClause.append("return null;\n")
        }
        else {
            createClause.append("if (args == null || args.length != $argumentCount)) {\n")
            createClause.append("${it4}return null;\n")
            createClause.append("}\n")
            createClause.append("return new $name(")
            for (int i = 0; i < argumentCount; ++i) {
                createClause.append("args[$i]")
            }
            createClause.append(");\n")
        }

        String body = """
            @java.lang.Override
            public ${ctClass.name} initialize(Object... args) {
                try {
                    ${createClause.toString()}
                } catch(Throwable ignored) {
                    return null;
                }
            }
        """

        CtMethod initialize = CtNewMethod.make(body, cc)
        cc.addMethod(initialize)
        cc.writeFile()

        return cc
    }
}
