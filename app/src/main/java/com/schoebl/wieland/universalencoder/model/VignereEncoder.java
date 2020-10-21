package com.schoebl.wieland.universalencoder.model;

/**
 * Created by wulka on 10.01.2018.
 */

public class VignereEncoder extends DefaultEncoder {
    public VignereEncoder(String defaultText, String encodedText, String decodedText, String encodeParameters) {
        super(defaultText, encodedText, decodedText, encodeParameters);
    }

    @Override
    protected void encode() {
        char[] key = encodeParameters.toCharArray();
        char[] decoded = defaultText.toCharArray();
        String encoded = "";

        for (int i = 0; i < decoded.length; i++) {
            for (int y = 0; y < key.length; y++) {
                if (i >= decoded.length)
                    break;

                int ind = getCharsetIndex(decoded[i]);
                int in2 = getCharsetIndex(key[y]);

                int newx = ind + in2;

                while (newx >= charset.length) {
                    newx = newx - charset.length;
                }

                encoded = encoded + charset[newx];

                i++;
            }
            i--;
        }

        encodedText = encoded;
    }

    @Override
    protected void decode() {
        char[] key = encodeParameters.toCharArray();
        char[] encoded = encodedText.toCharArray();
        String decoded = "";

        for (int i = 0; i < encoded.length; i++) {
            for (int y = 0; y < key.length; y++){
                if (i >= encoded.length)
                    break;

                int ind = getCharsetIndex(encoded[i]);
                int in2 = getCharsetIndex(key[y]);

                int newx = ind - in2;

                while (newx < 0) {
                    newx = newx + charset.length;
                }

                decoded = decoded + charset[newx];

                i++;
            }
            i--;
        }

        decodedText = decoded;
    }
}
