package com.apps.home.notewidget;

/**
 * Created by k.kaszubski on 1/20/16.
 */
public interface Constants {
    String NOTES_TABLE = "NOTES";
    String ID_COL = "_id";
    String MILLIS_COL = "millis";
    String NOTE_TITLE_COL = "noteTitle";
    String NOTE_TEXT_COL = "noteText";

    String WIDGETS_TABLE = "WIDGETS";
    String WIDGET_ID_COL = "widgetId";
    String CONNECTED_NOTE_ID_COL = "noteID";
    String CURRENT_WIDGET_MODE_COL = "mode";
    String CURRENT_THEME_MODE_COL = "themeMode";
    String CURRENT_TEXT_SIZE_COL = "textSize";

    String PREFS_NAME = "prefs";
    String CONFIGURED_KEY = "configured";
    String SORT_BY_DATE_KEY = "sortByDate";
    String CURRENT_WIDGET_THEME_KEY = "currentWidgetTheme";

    String FRAGMENT_LIST = "ListFragment";
    String FRAGMENT_NOTE = "NoteFragment";

    //int WIDGET_TITLE_MODE = R.layout.appwidget_title_lollipop_light;
    //int WIDGET_CONFIG_MODE = R.layout.appwidget_config_lollipop_light;
   /* int WIDGET_TITLE_MODE = R.layout.appwidget_title_miui_light;
    int WIDGET_CONFIG_MODE = R.layout.appwidget_config_miui_light;*/

    int WIDGET_MODE_TITLE = 0;
    int WIDGET_MODE_CONFIG = 1;

    int WIDGET_THEME_LOLLIPOP = 0;
    int WIDGET_THEME_MIUI = 1;
    int WIDGET_THEME_SIMPLE = 2;

    int WIDGET_THEME_LIGHT = 0;
    int WIDGET_THEME_DARK = 1;

}
