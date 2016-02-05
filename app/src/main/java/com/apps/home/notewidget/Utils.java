package com.apps.home.notewidget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Kamil on 2016-01-30.
 */
public class Utils {
    private static Toast toast;
    private static int[][][] widgetLayouts;

    public static void showToast(Context context, String message){
        if(toast!=null)
            toast.cancel();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static int getLayoutFile(int themeMode, int widgetMode){
        if(widgetLayouts==null) {
            widgetLayouts = new int[3][2][2];
            widgetLayouts[0][0][0] = R.layout.appwidget_title_lollipop;
            widgetLayouts[0][0][1] = R.layout.appwidget_config_lollipop;
            widgetLayouts[0][1][0] = R.layout.appwidget_title_lollipop_dark;
            widgetLayouts[0][1][1] = R.layout.appwidget_config_lollipop_dark;
            //TODO fill other cells
        }
        return widgetLayouts[0][themeMode][widgetMode];
    }

    public static int switchWidgetMode(int currentMode){
        return currentMode == Constants.WIDGET_MODE_TITLE? Constants.WIDGET_MODE_CONFIG : Constants.WIDGET_MODE_TITLE;
    }

    public static int switchThemeMode(int currentMode){
        return currentMode == Constants.WIDGET_THEME_LIGHT? Constants.WIDGET_THEME_DARK : Constants.WIDGET_THEME_LIGHT;
    }
}
