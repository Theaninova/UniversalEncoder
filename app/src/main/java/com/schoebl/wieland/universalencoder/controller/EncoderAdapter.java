package com.schoebl.wieland.universalencoder.controller;

import com.schoebl.wieland.universalencoder.model.BEncoder;
import com.schoebl.wieland.universalencoder.model.CaesarEncoder;
import com.schoebl.wieland.universalencoder.model.DefaultEncoder;
import com.schoebl.wieland.universalencoder.model.VignereEncoder;

import java.util.ArrayList;

/**
 * Created by wulka on 10.01.2018.
 */

public class EncoderAdapter {
    public ArrayList<DefaultEncoder> encoders;
    public int index = 0;

    public EncoderAdapter() {
        encoders = new ArrayList<>();
        encoders.add(new CaesarEncoder("", "", "", "1"));
        encoders.add(new VignereEncoder("", "", "", "1"));
        encoders.add(new BEncoder("", "", "", "b"));
    }
}
