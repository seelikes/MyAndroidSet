package com.example.myjetpackapplication.plugin.realm

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.github.seelikes.android.realm.annotation.RealmBaseModule
import com.github.seelikes.android.realm.annotation.RealmMigrationClass
import com.github.seelikes.android.realm.annotation.RealmMigrationMethod
import io.realm.annotations.RealmModule
import javassist.*
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 * Created by liutiantian on 2020-01-02 12:08 星期四
 */
class RealmTransform extends Transform {
    private Project project
    private RealmExtension extension

    RealmTransform(Project project, RealmExtension extension) {
        this.project = project
        this.extension = extension
    }

    @Override
    String getName() {
        return "RealmHelper"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        project.logger.info("transform door open")
        for (def classPath in project.android.bootClasspath) {
            ClassPool.default.appendClassPath classPath.toString()
        }

        project.logger.info("Log class imported to environment")

        def realmJarInput
        def jarClasses = []
        def moduleClasses = []
        def migrationClasses = []

        transformInvocation.inputs.each { input ->
            input.jarInputs.each { jarInput ->
                project.logger.warn("jarInput.file.absolutePath: " + jarInput.file.absolutePath)
                ClassPool.default.appendClassPath(jarInput.file.absolutePath)
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def jarFile = new JarFile(jarInput.file)
                def entries = jarFile.entries()
                while (entries.hasMoreElements()) {
                    def entry = entries.nextElement()
                    if (!entry.name.endsWith(".class")) {
                        continue
                    }
                    if (entry.name == "com/github/seelikes/android/realm/api/RealmApi.class") {
                        realmJarInput = jarInput
                    }
                    jarClasses.add(entry.name.replaceAll("/", ".").substring(0, entry.name.length() - 6))
                }
                jarFile.close()
                if (jarInput == realmJarInput) {
                    return
                }
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }

            input.directoryInputs.each { directoryInput ->
                ClassPool.default.appendClassPath(directoryInput.file.absolutePath)
                def fileClassMap = new HashMap()
                directoryInput.file.eachFileRecurse { file ->
                    if (!file.name.endsWith(".class") || (file.name.matches(/^R\$[a-zA-Z]+?\.class$/) || file.name == "R.class")) {
                        return
                    }

                    def ctClass = getCtClassFromFile(file)
                    if (ctClass != null) {
                        if (ctClass.isFrozen()) {
                            ctClass.defrost()
                        }
                        judgeClass(ctClass, migrationClasses, RealmMigrationClass.class)
                        judgeClass(ctClass, moduleClasses, RealmModule.class)

                        ctClass.writeFile new File(transformInvocation.context.temporaryDir, ctClass.name.replaceAll(".", "/") + ".class").absolutePath
                        fileClassMap.put(ctClass, directoryInput.file)
                        ctClass.detach()
                    }
                }

                if (!fileClassMap.isEmpty()) {
                    fileClassMap.entrySet().each { Map.Entry<CtClass, File> entry ->
                        if (entry.value.delete()) {
                            FileUtils.copyFile(new File(transformInvocation.context.temporaryDir, entry.key.name.replaceAll(".", "/") + ".class"), entry.value)
                        }
                    }
                }

                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            jarClasses.each { jarClass ->
                def ctClass = ClassPool.default.getCtClass(jarClass as String)
                if (ctClass.isFrozen()) {
                    ctClass.defrost()
                }
                judgeClass(ctClass, migrationClasses, RealmMigrationClass.class)
                judgeClass(ctClass, moduleClasses, RealmModule.class)
                ctClass.detach()
            }

        }
        project.logger.info("transform door close")

        project.logger.info("migrationClasses.size: " + migrationClasses.size())
        project.logger.info("realmJarInput != null: " + (realmJarInput != null))
        if (realmJarInput != null) {
            def migrateMethods = []
            project.logger.info("migrationClasses.size() > 0: " + (migrationClasses.size() > 0))
            if (migrationClasses.size() > 0) {
                migrationClasses.each { candidateClass ->
                    candidateClass.declaredMethods.each { declaredMethod ->
                        if ((declaredMethod as CtMethod).hasAnnotation(RealmMigrationMethod.class)) {
                            if (migrateMethods.size() == 0) {
                                migrateMethods.add(declaredMethod)
                            }
                            else {
                                def realmMigrationMethodAnnotation = (declaredMethod as CtMethod).getAnnotation(RealmMigrationMethod.class)
                                def possibleIndex
                                migrateMethods.eachWithIndex { migrateMethod, index ->
                                    def migrateMethodAnnotation = (migrateMethod as CtMethod).getAnnotation(RealmMigrationMethod.class)
                                    if (realmMigrationMethodAnnotation.oldVersion > migrateMethodAnnotation.newVersion) {
                                        possibleIndex = index
                                        return
                                    }
                                }
                                if (possibleIndex == null) {
                                    migrateMethods.add(0, declaredMethod)
                                }
                                else {
                                    migrateMethods.add(possibleIndex as int, declaredMethod)
                                }
                            }
                        }
                    }
                }
            }

            project.logger.info("migrateMethods.size: " + migrateMethods.size())
            if (migrateMethods.size() > 0) {
                def Context = ClassPool.default.get("android.content.Context")

                def jarInput = realmJarInput as JarInput
                def tmpOutputJar = new File(project.buildDir, "tmp/" + jarInput.file.name)
                def tmpOutputJarStream = new JarOutputStream(new FileOutputStream(tmpOutputJar))
                def jarFile = new JarFile(jarInput.file)
                try {
                    def entries = jarFile.entries()
                    while (entries.hasMoreElements()) {
                        def entry = entries.nextElement()
                        project.logger.info("entry: " + entry)
                        if (entry.name == "com/github/seelikes/android/realm/api/RealmLibraryMigration.class") {
                            def RealmLibraryMigration = ClassPool.default.get("com.github.seelikes.android.realm.api.RealmLibraryMigration")
                            def migrateMethod = RealmLibraryMigration.getDeclaredMethod("migrate")
                            StringBuilder migrateBody = new StringBuilder()
                            migrateBody.append("{\n")
                            migrateMethods.each { method ->
                                def realMethod = method as CtMethod
                                ClassPool.default.importPackage(realMethod.getDeclaringClass().name)
                                if (Modifier.isStatic(realMethod.getModifiers())) {
                                    migrateBody.append(realMethod.getDeclaringClass().name + "." + realMethod.name + "(\$\$);\n")
                                }
                                else {
                                    try {
                                        realMethod.getDeclaringClass().getDeclaredConstructor(Context)
                                        migrateBody.append("new " + realMethod.getDeclaringClass().simpleName + "(context)." + realMethod.name + "(\$\$);\n")
                                    }
                                    catch (NotFoundException ignored) {
                                        migrateBody.append("new " + realMethod.getDeclaringClass().simpleName + "()." + realMethod.name + "(\$\$);\n")
                                    }
                                }
                            }
                            migrateBody.append("}\n")
                            migrateMethod.setBody(migrateBody.toString())
                            tmpOutputJarStream.putNextEntry(new ZipEntry(entry.name))
                            tmpOutputJarStream.write(RealmLibraryMigration.toBytecode())
                            continue
                        }
                        if (entry.name == "com/github/seelikes/android/realm/api/RealmApi.class") {
                            ClassPool.default.importPackage("android.app.Application")
                            ClassPool.default.importPackage("io.realm.Realm")
                            ClassPool.default.importPackage("io.realm.RealmConfiguration")
                            ClassPool.default.importPackage("io.realm.RealmConfiguration\$Builder")
                            ClassPool.default.importPackage("com.github.seelikes.android.realm.api.RealmLibraryMigration")
                            def RealmApi = ClassPool.default.get("com.github.seelikes.android.realm.api.RealmApi")
                            RealmApi.declaredMethods.each { initMethod ->
                                if (initMethod.name.startsWith("init")) {
                                    StringBuilder initBody = new StringBuilder()

                                    initBody.append("{\n")

                                    // 初始化Realm
                                    initBody.append("if (\$1 != null) {\n")
                                    initBody.append("    if (\$1 instanceof Application) {\n")
                                    initBody.append("        Realm.init(\$1);\n")
                                    initBody.append("    }\n")
                                    initBody.append("    else {\n")
                                    initBody.append("        Realm.init(\$1.getApplicationContext());")
                                    initBody.append("    }\n")
                                    initBody.append("}\n")

                                    initBody.append("RealmConfiguration\$Builder builder = new RealmConfiguration\$Builder();\n")
                                    if (extension.assetFile != null && !extension.assetFile.isEmpty()) {
                                        initBody.append("builder.assetFile(\"" + extension.assetFile + "\");\n")
                                    }
                                    if (extension.compactOnLaunch) {
                                        initBody.append("builder.compactOnLaunch();\n")
                                    }
                                    if (extension.deleteRealmIfMigrationNeeded) {
                                        initBody.append("builder.deleteRealmIfMigrationNeeded();\n")
                                    }
                                    moduleClasses = moduleClasses.findAll { moduleClass ->
                                        def realModuleClass = moduleClass as CtClass
                                        if (realModuleClass.name.startsWith("io.realm")) {
                                            return false
                                        }
                                        return true
                                    }
                                    if (moduleClasses != null && !moduleClasses.isEmpty()) {
                                        CtClass baseModule
                                        for (def i = 0; i < moduleClasses.size(); ++i) {
                                            def realModuleCLass = moduleClasses[i] as CtClass
                                            ClassPool.default.importPackage(realModuleCLass.name)
                                            if (realModuleCLass.hasAnnotation(RealmBaseModule.class)) {
                                                def mod = realModuleCLass.getModifiers()
                                                if (Modifier.isAbstract(mod) && Modifier.isInterface(mod)) {
                                                    continue
                                                }
                                                def constructor = getConstructor(realModuleCLass, RealmApi, Context)
                                                if (constructor == null) {
                                                    continue
                                                }
                                                if (baseModule != null) {
                                                    throw new IllegalStateException("can not have 2 classes which has RealmBaseModule")
                                                }
                                                baseModule = realModuleCLass
                                            }
                                        }
                                        if (baseModule != null) {
                                            initBody.append("builder.modules(")
                                            try {
                                                def constructor = baseModule.getDeclaredConstructor(Context)
                                                if (constructor.visibleFrom(RealmApi)) {
                                                    initBody.append("(Object) new " + baseModule.simpleName + "(\$1),")
                                                }
                                            }
                                            catch (NotFoundException ignored) {
                                                initBody.append("(Object) new " + baseModule.simpleName + "(),")
                                            }
                                            moduleClasses.each { moduleClass ->
                                                def realModuleClass = moduleClass as CtClass
                                                if (realModuleClass == baseModule) {
                                                    return
                                                }
                                                try {
                                                    def constructor = realModuleClass.getDeclaredConstructor(Context)
                                                    if (constructor.visibleFrom(RealmApi)) {
                                                        initBody.append("(Object) new " + realModuleClass.simpleName + "(\$1),")
                                                    }
                                                }
                                                catch (NotFoundException ignored) {
                                                    def constructor = realModuleClass.getDeclaredConstructor(null)
                                                    if (constructor.visibleFrom(RealmApi)) {
                                                        initBody.append("(Object) new " + realModuleClass.name + "(),")
                                                    }
                                                }
                                            }
                                            initBody.deleteCharAt(initBody.length() - 1)
                                            initBody.append(");\n")
                                        }
                                        else {
                                            moduleClasses.each {
                                                def realModuleClass = moduleClass as CtClass
                                                try {
                                                    def constructor = realModuleClass.getDeclaredConstructor(Context)
                                                    if (constructor.visibleFrom(RealmApi)) {
                                                        initBody.append("builder.addModule((Object) new " + realModuleClass.simpleName + "(\$1));\n")
                                                    }
                                                }
                                                catch (NotFoundException ignored) {
                                                    def constructor = realModuleClass.getDeclaredConstructor(null)
                                                    if (constructor.visibleFrom(RealmApi)) {
                                                        initBody.append("builder.addModule((Object) new " + realModuleClass.simpleName + "());\n")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (extension.directory != null && !extension.directory.isEmpty()) {
                                        initBody.append("if (\$1 != null) {\n")
                                        if (extension.directory.contains(":")) {
                                            def dirs = extension.directory.split(":")
                                            initBody.append("new File(\$1." + dirs[0] + ", \"" + dirs[1] + "\");\n")
                                        }
                                        else {
                                            initBody.append("new File(\$1.getFilesDir(), \"" + extension.directory + "\");\n")
                                        }
                                    }
                                    if (extension.inMemory) {
                                        initBody.append("builder.inMemory();\n")
                                    }
                                    if (extension.name != null && !extension.name.isEmpty()) {
                                        initBody.append("builder.name(\"" + extension.name + "\");\n")
                                    }
                                    if (extension.readOnly) {
                                        initBody.append("builder.readOnly();\n")
                                    }
                                    if (extension.encryptionKey != null && !extension.encryptionKey.isEmpty()) {
                                        initBody.append("builder.encryptionKey(\"" + extension.encryptionKey + "\".getBytes());\n")
                                    }
                                    if (extension.schemaVersion >= 0) {
                                        initBody.append("builder.schemaVersion((long) " + extension.schemaVersion + ");\n")
                                    }
                                    initBody.append("builder.migration(new RealmLibraryMigration(\$1));\n")
                                    initBody.append("realm = Realm.getInstance(builder.build());\n")

                                    initBody.append("}\n")

                                    project.logger.info("initBody: $initBody")

                                    initMethod.setBody(initBody.toString())
                                    tmpOutputJarStream.putNextEntry(new ZipEntry(entry.name))
                                    tmpOutputJarStream.write(RealmApi.toBytecode())
                                }
                            }
                            continue
                        }
                        def inputStream = jarFile.getInputStream(entry)
                        try {
                            tmpOutputJarStream.putNextEntry(new ZipEntry(entry.name))
                            def bytes = new byte[8192]
                            def bytesRead
                            while ((bytesRead = inputStream.read(bytes)) != -1) {
                                tmpOutputJarStream.write(bytes, 0, bytesRead as int)
                            }
                        }
                        finally {
                            inputStream.close()
                        }
                    }
                }
                finally {
                    tmpOutputJarStream.close()
                    jarFile.close()
                }
                def jarName = tmpOutputJar.name
                def md5Name = DigestUtils.md5Hex(tmpOutputJar.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                project.logger.info("dest: " + dest)
                FileUtils.copyFile(tmpOutputJar, dest)
            }
        }
    }

    def getCtClassFromFile(File file) {
        if (file == null) {
            return null
        }
        if (!file.name.endsWith(".class")) {
            return null
        }
        if (extension.packageName != null && !extension.packageName.isEmpty()) {
            def packagePath = file.absolutePath.replaceAll("\\\\", ".")
            if (packagePath.indexOf(extension.packageName) == -1) {
                return
            }
            def canonicalName = packagePath.substring(packagePath.indexOf(extension.packageName), packagePath.length() - 6)
            return ClassPool.default.getCtClass(canonicalName)
        }
        def pathNames = file.absolutePath.split("/|\\\\")
        for (def i = pathNames.length; i > 0; --i) {
            if (pathNames[i - 1].matches("^\\d+\$")) {
                for (def j = i; j < pathNames.length; ++j) {
                    def classPath = pathNames[j..pathNames.length - 1].join("/")
                    project.logger.info("classPath: $classPath")
                    try {
                        return ClassPool.default.get(classPath.substring(0, classPath.length() - 6).replaceAll("/", '.'))
                    }
                    catch (NotFoundException ignored) {

                    }
                }
                break
            }
        }
        return null
    }

    def judgeClass(CtClass ctClass, List classes, Class annotationClass) {
        if (classes.contains(ctClass)) {
            return
        }
        for (def i = 0; i < classes.size(); ++i) {
            if ((classes[i] as CtClass).name == ctClass.name) {
                return
            }
        }
        if (ctClass.name == annotationClass.canonicalName) {
            return
        }
        project.logger.info("ctClass.name: $ctClass.name")
        int mod = ctClass.getModifiers()
        if (Modifier.isEnum(mod) || Modifier.isInterface(mod)) {
            return
        }
        if (Modifier.isAbstract(mod) || Modifier.isAnnotation(mod)) {
            return
        }

        if (ctClass.hasAnnotation(annotationClass)) {
            if (classes.size() == 0) {
                classes.add(ctClass)
            }
            else {
                for (def i = 0; i < classes.size(); ++i) {
                    if ((classes[i] as CtClass).name == ctClass.name) {
                        return
                    }
                }
                classes.add(ctClass)
            }
        }
    }

    static def getConstructor(CtClass ctClass, CtClass monitorClass, CtClass[] parameters) {
        try {
            def constructor = ctClass.getDeclaredConstructor(parameters)
            if (constructor.visibleFrom(monitorClass)) {
                return constructor
            }
            throw NotFoundException()
        }
        catch (NotFoundException ignored) {
            def constructor = ctClass.getDeclaredConstructor(null)
            if (constructor.visibleFrom(monitorClass)) {
                return constructor
            }
        }
        return null
    }
}
