package com.jvm;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * 测试jvm的几种引用类型
 * @author cuihailong
 * @date 2022/10/27 13:43
 */
public class ReferenceTypeTest {

    public static void main(String[] args) {
        // 强引用
        RoleData roleData = new RoleData();

        // 弱引用
        WeakReference<RoleData> weakReference = new WeakReference<>(new RoleData());

        // 主动触发gc
        System.gc();

        if(Objects.isNull(roleData)){
            System.out.println("强引用的对象被回收了");
        }

        if(Objects.isNull(weakReference.get())){
            System.out.println("弱引用的对象被回收了");
        }
    }
}
