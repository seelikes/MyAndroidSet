#include <jni.h>
#include <string>
#include <ctime>
#include <android/log.h>

void native_whileTimeNative(long mills) {
    time_t startTime = time(nullptr);
    while (true) {
        long currentTime = time(nullptr);
        __android_log_print(ANDROID_LOG_INFO, "native-lib", "202001201700, native_whileTimeNative, t: %ld; startTime: %ld", currentTime, startTime);
        if (currentTime - startTime > startTime + mills) {
            break;
        }
    }
}

static JNINativeMethod methods[] = {
    {"whileTimeNative", "(J)V", (void *) native_whileTimeNative},
};

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv *env = nullptr;
    jint result = -1;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return -1;
    }

    jclass clazz = env->FindClass("com/example/myjetpackapplication/business/anr/AnrActivity");
    if (clazz == nullptr) {
        return -1;
    }

    if (env->RegisterNatives(clazz, methods, 1) < 0) {
        return -1;
    }

    return JNI_VERSION_1_4;
}