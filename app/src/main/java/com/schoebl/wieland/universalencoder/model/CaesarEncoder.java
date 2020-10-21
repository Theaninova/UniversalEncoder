package com.schoebl.wieland.universalencoder.model;

/**
 * Created by wulka on 09.01.2018.
 */

public class CaesarEncoder extends DefaultEncoder {
    public CaesarEncoder(String defaultText, String encodedText, String decodedText, String encodeParameters) {
        super(defaultText, encodedText, decodedText, encodeParameters);
    }

    @Override
    protected void encode() {
        if (this.encodeParameters.equals(""))
            return;
        int key = Integer.parseInt(this.encodeParameters);

        char[] text = this.defaultText.toCharArray();
        String out  = "";

        for (char aText : text) {
            int newIndex = getCharsetIndex(aText) + key;
            while (newIndex >= charset.length)
                newIndex = newIndex - charset.length;
            out = out + charset[newIndex];
        }

        encodedText = out;
    }

    @Override
    protected void decode() {
        if (this.encodeParameters.equals(""))
            return;

        int key = Integer.parseInt(this.encodeParameters);

        char[] text = this.encodedText.toCharArray();
        String out  = "";

        for (char aText : text) {
            int newIndex = getCharsetIndex(aText) - key;
            while (newIndex < 0)
                newIndex = newIndex + charset.length;
            out = out + charset[newIndex];
        }

        decodedText = out;
    }
}
