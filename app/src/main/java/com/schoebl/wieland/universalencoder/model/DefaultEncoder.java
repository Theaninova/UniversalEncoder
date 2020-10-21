package com.schoebl.wieland.universalencoder.model;

import java.util.Objects;

/**
 * Created by wulka on 09.01.2018.
 */

public class DefaultEncoder {
    protected char[] charset = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    protected char[] charsetVocals = new char[] {'a', 'e', 'i', 'o', 'u'};
    protected String defaultText;
    protected String encodedText;
    protected String decodedText;
    protected String encodeParameters;

    public DefaultEncoder(String defaultText, String encodedText, String decodedText, String encodeParameters) {
        this.encodeParameters = encodeParameters;
        this.decodedText = decodedText;
        this.encodedText = encodedText;
        this.defaultText = defaultText;
    }

    public void setEncodeParameters(String encodeParameters) {
        if ((!Objects.equals(encodeParameters, "")) && (encodeParameters != null))
            this.encodeParameters = encodeParameters;
    }

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) throws Exception {
        this.defaultText = defaultText;

        encode();
        decode();

        if (!defaultText.equals(decodedText)) {
            System.err.println("Error while encoding. Decoded result does not equal input.");
            throw new Exception("Error while encoding. Decoded result does not equal input.");
        }
    }

    public String getEncodedText() {
        return encodedText;
    }

    public void setEncodedText(String encodedText) throws Exception {
        this.encodedText = encodedText;

        decode();
    }

    public String getDecodedText() {
        return decodedText;
    }

    protected void encode() {
        //Override this method
    }

    protected void decode() {
        //Override this method
    }

    protected int getCharsetIndex(char indexing) {
        for (int i = 0; i < charset.length; i++) {
            if (indexing == charset[i])
                return i;
        }
        return -1;
    }
}
