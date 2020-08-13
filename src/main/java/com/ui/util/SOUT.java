package com.ui.util;

import javafx.scene.control.TextArea;

import static com.ui.sample.Controller.MAP;

/**
 * @author : zhilin
 * @date : 2020/08/13
 */
public class SOUT {
    public static void println(String msg){
        TextArea areaText = (TextArea) MAP.get("areaText");
        areaText.appendText(msg+"\n");
    }
}
