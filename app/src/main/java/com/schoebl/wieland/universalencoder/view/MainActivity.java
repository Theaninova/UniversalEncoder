package com.schoebl.wieland.universalencoder.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import com.schoebl.wieland.universalencoder.R;
import com.schoebl.wieland.universalencoder.controller.EncoderAdapter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText defaultText;
        final EditText key;
        final TextView encodedText;
        final TextView klartext;
        final TextView codedtext;
        final Switch   switchWidget;
        final Button button;
        final Context context = this;
        final View mainView;
        final Spinner spinner;

        defaultText = findViewById(R.id.editText);
        key = findViewById(R.id.editText5);
        encodedText = findViewById(R.id.textView4);
        codedtext = findViewById(R.id.codedText);
        klartext = findViewById(R.id.klartext);
        switchWidget = findViewById(R.id.switch1);
        button = findViewById(R.id.button);
        mainView = findViewById(R.id.main_layout);
        spinner = findViewById(R.id.spinner);

        final EncoderAdapter encoder = new EncoderAdapter();

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String selectedItem = adapterView.getItemAtPosition(pos).toString();
                switch (selectedItem) {
                    case "Cäsar":
                        encoder.index = 0;
                        break;
                    case "Vignère":
                        encoder.index = 1;
                        break;
                    case "B-Sprache":
                        encoder.index = 2;
                        break;
                }
                if (!switchWidget.isChecked()) {
                    try {
                        encoder.encoders.get(encoder.index).setEncodeParameters(key.getText().toString());
                        encoder.encoders.get(encoder.index).setDefaultText(defaultText.getText().toString());
                    } catch (Exception e) {
                        Snackbar snackbar = Snackbar
                                .make(mainView, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    encodedText.setText(encoder.encoders.get(encoder.index).getEncodedText());
                } else {
                    try {
                        encoder.encoders.get(encoder.index).setEncodeParameters(key.getText().toString());
                        encoder.encoders.get(encoder.index).setEncodedText(defaultText.getText().toString());
                    } catch (Exception e) {
                        Snackbar snackbar = Snackbar
                                .make(mainView, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    encodedText.setText(encoder.encoders.get(encoder.index).getDecodedText());
                }
            }
            @Override public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        switchWidget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    codedtext.setText("Decodierter Text");
                    klartext.setText("Codierter Text");

                    setEncodedText(encoder, defaultText.getText().toString(), mainView);
                    encodedText.setText(encoder.encoders.get(encoder.index).getDecodedText());
                } else {
                    codedtext.setText("Codierter Text");
                    klartext.setText("Decodierter Text");

                    setDefaultText(encoder, defaultText.getText().toString(), mainView);
                    encodedText.setText(encoder.encoders.get(encoder.index).getEncodedText());
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Coded Text", encodedText.getText());
                clipboard.setPrimaryClip(clip);

                Snackbar snackbar = Snackbar
                        .make(mainView, "\"" + encodedText.getText() + "\" in die Zwischenablage kopiert.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        defaultText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!switchWidget.isChecked()) {
                    setDefaultText(encoder, defaultText.getText().toString(), mainView);
                    encodedText.setText(encoder.encoders.get(encoder.index).getEncodedText());
                } else {
                    setEncodedText(encoder, charSequence.toString(), mainView);
                    encodedText.setText(encoder.encoders.get(encoder.index).getDecodedText());
                }
            }
            @Override public void afterTextChanged(Editable editable) {}
        });

        key.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                encoder.encoders.get(encoder.index).setEncodeParameters(charSequence.toString());

                if (!switchWidget.isChecked()) {
                    setDefaultText(encoder, defaultText.getText().toString(), mainView);
                    encodedText.setText(encoder.encoders.get(encoder.index).getEncodedText());
                } else {
                    setEncodedText(encoder, defaultText.getText().toString(), mainView);
                    encodedText.setText(encoder.encoders.get(encoder.index).getDecodedText());
                }
            }
            @Override public void afterTextChanged(Editable editable) {}
        });
    }

    private static void setDefaultText(EncoderAdapter encoderAdapter, String text, View mainView) {
        try {
            encoderAdapter.encoders.get(encoderAdapter.index).setDefaultText(text);
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(mainView, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private static void setEncodedText(EncoderAdapter encoderAdapter, String text, View mainView) {
        try {
            encoderAdapter.encoders.get(encoderAdapter.index).setEncodedText(text);
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(mainView, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}
