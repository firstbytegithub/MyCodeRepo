#include <jni.h>

JNIEXPORT jint JNICALL
Java_com_example_chengqi_mycoderepo_expert_NdkActivity_add(JNIEnv *env, jobject instance, jint a,
                                                           jint b) {

    // TODO
    return a+b;
}

JNIEXPORT jint JNICALL
Java_com_example_chengqi_mycoderepo_expert_NdkActivity_sub(JNIEnv *env, jobject instance, jint a,
                                                           jint b) {

    // TODO
    return a-b;
}