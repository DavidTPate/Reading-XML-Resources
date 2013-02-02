package com.craftingmobile.tutorials.xmlresourceexample;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

public class LoginConfig {
    private static final String TAG               = "LoginConfig";

    /**
     * Whether to hide Login functionality from the user.
     */
    private static boolean      mShowSaveUsername = false;

    public static boolean getShowSaveUsername() {
        return mShowSaveUsername;
    }

    public static void init(Context context) {
        Log.v(TAG, "LoginConfig.init()");

        loadLoginSettings(context);
    }

    public static final void beginDocument(XmlPullParser parser, String firstElementName) throws XmlPullParserException, IOException {
        int type;
        while ((type = parser.next()) != parser.START_TAG && type != parser.END_DOCUMENT) {
            ;
        }

        if (type != parser.START_TAG) {
            throw new XmlPullParserException("No start tag found");
        }

        if (!parser.getName().equals(firstElementName)) {
            throw new XmlPullParserException("Unexpected start tag: found " + parser.getName() + ", expected " + firstElementName);
        }
    }

    public static final void nextElement(XmlPullParser parser) throws XmlPullParserException, IOException {
        int type;
        while ((type = parser.next()) != parser.START_TAG && type != parser.END_DOCUMENT) {
            ;
        }
    }

    private static void loadLoginSettings(Context context) {
        XmlResourceParser parser = context.getResources().getXml(R.xml.login_config);

        try {
            beginDocument(parser, "login_config");

            while (true) {
                nextElement(parser);
                String tag = parser.getName();

                if (tag == null) {
                    break;
                }

                String name = parser.getAttributeName(0);
                String value = parser.getAttributeValue(0);
                String text = null;

                if (parser.next() == XmlPullParser.TEXT) {
                    text = parser.getText();
                }

                Log.v(TAG, "tag: " + tag + " value: " + value + " - " + text);

                if ("name".equalsIgnoreCase(name)) {
                    if ("bool".equals(tag)) {
                        // bool config tags go here
                        if ("showSaveUsername".equalsIgnoreCase(value)) {
                            mShowSaveUsername = "true".equalsIgnoreCase(text);
                        }
                    }
                }
            }
        } catch (XmlPullParserException e) {
            Log.e(TAG, "loadLoginSettings caught ", e);
        } catch (NumberFormatException e) {
            Log.e(TAG, "loadLoginSettings caught ", e);
        } catch (IOException e) {
            Log.e(TAG, "loadLoginSettings caught ", e);
        } finally {
            parser.close();
        }
    }

}
