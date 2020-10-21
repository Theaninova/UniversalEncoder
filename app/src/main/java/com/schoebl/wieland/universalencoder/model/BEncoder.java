package com.schoebl.wieland.universalencoder.model;

/**
 * Created by wulka on 05.03.2018.
 */

public class BEncoder extends DefaultEncoder {
    public BEncoder(String defaultText, String encodedText, String decodedText, String encodeParameters) {
        super(defaultText, encodedText, decodedText, encodeParameters);
    }

    @Override
    protected void encode() {
        if (this.encodeParameters.equals(""))
            return;

        char[] z = defaultText.toCharArray();
        StringBuilder codeBuilder = new StringBuilder();

        for (char x : z) {
            codeBuilder.append(x);

            for (char v : charsetVocals) {
                if (x == v) {
                    codeBuilder.append(encodeParameters);
                    codeBuilder.append(x);
                    break;
                }
            }
        }

        encodedText = codeBuilder.toString();
    }

    @Override
    protected void decode() {
        if (this.encodeParameters.equals(""))
            return;

        char[] z = encodedText.toCharArray();
        StringBuilder klarTextBuilder = new StringBuilder();

        for (int i = 0; i < z.length; i++) {
            klarTextBuilder.append(z[i]);

            for (char v : charsetVocals) {
                if (z[i] == v) {
                    i = i + 2;
                    break;
                }
            }
        }

        decodedText = klarTextBuilder.toString();
    }
}
