package com.example.ivanovnv.spaceanalytix.Translate;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


//import com.google.cloud.translate.Translate;
//import com.google.cloud.translate.Translate.TranslateOption;
//import com.google.cloud.translate.TranslateOptions;
//import com.google.cloud.translate.Translation;



public class TranslateTextView extends AppCompatTextView {

    public TranslateTextView(Context context) {
        super(context);
    }

    public TranslateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TranslateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
       // String newText = text + " new text";

//        Translate translate = TranslateOptions.getDefaultInstance().getService();
//
//        // The text to translate
//        String textToTranslate = "Hello, world!";
//
//        // Translates some text into Russian
//        Translation translation =
//                translate.translate(
//                        textToTranslate,
//                        TranslateOption.sourceLanguage("en"),
//                        TranslateOption.targetLanguage("ru"));
//
//
//        //System.out.printf("Text: %s%n", text);
//        //System.out.printf("Translation: %s%n", translation.getTranslatedText());
//
//
//        super.setText(translation.getTranslatedText(), type);
    }
}

