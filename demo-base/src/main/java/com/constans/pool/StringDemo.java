package com.constans.pool;

public class StringDemo {
    public static void main(String[] args) {
        // 首先检查常量池中是否有 “1” ，如果有直接返回 “1” 的地址，如果没有，则创建一个 “1” ，然后返回地址。
        String s3 = "1";
        // 首先检查常量池中是否有“1”，有则将堆中的对象指向常量池，没有则在常量池中创建，这样在堆和常量池中都创建了对象
        String s1 = new String("1");
        String s2 = s1.intern();
        System.out.println(s1==s2);
        System.out.println(s3==s2);

    }
}
