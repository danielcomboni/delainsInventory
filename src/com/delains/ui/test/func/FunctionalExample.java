package com.delains.ui.test.func;

import java.util.function.Function;

public class FunctionalExample {

    public static void main(String[] args) {

        Function<String,String> function = FunctionalExample::show;

        System.out.println(function.apply("dddd"));

    }

    static String show(String example){
        return "Hello " + example;
     }


}


