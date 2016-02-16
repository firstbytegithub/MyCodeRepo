package com.example.chengqi.mycoderepo.app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.chengqi.mycoderepo.R;

import java.util.Arrays;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private static final String TAG = "NewAppWidget";

    private static final String ACTION_BROADCAST = "com.archermind.appwidget.broadcast";

    private static NewAppWidget appWidget = null;

    private String mTxt = "not set";

    public static NewAppWidget getInstance() {
        if (appWidget == null) {
            appWidget = new NewAppWidget();
        }
        return appWidget;
    }

    public void notifyChange(AppService01 service) {
        mTxt= service.getShowText();

        onUpdate(service, AppWidgetManager.getInstance(service), null);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();

        if (action.equals(ACTION_BROADCAST)) {
            Log.d(TAG, "receive broadcast");
        } else if (action.equals("android.appwidget.action.APPWIDGET_UPDATE")) {
            Log.d(TAG, "receive android.appwidget.action.APPWIDGET_UPDATE");
        }
    }

    private RemoteViews prepareView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        Intent intentActivity = new Intent(context, DemoActivity.class);
        PendingIntent pendingActivity = PendingIntent.getActivity(context, 0, intentActivity, 0);
        views.setOnClickPendingIntent(R.id.button106, pendingActivity);

        Intent intentAction = new Intent("com.archermind.com.widgettest");
        PendingIntent pendingAction = PendingIntent.getActivity(context, 0, intentAction, 0);
        views.setOnClickPendingIntent(R.id.button107, pendingAction);

        ComponentName serviceName = new ComponentName(context, AppService01.class);

        Intent intentBroadcast = new Intent(ACTION_BROADCAST);
//        intentBroadcast.setComponent(serviceName);
        PendingIntent pendingBroadcast = PendingIntent.getBroadcast(context, 0, intentBroadcast, 0);
        views.setOnClickPendingIntent(R.id.button108, pendingBroadcast);

        // TODO: this code don't work, how to add broadcast intent for service?
//        Intent intentBroadcast = new Intent(AppService01.ACTION01);
////        intentBroadcast.setComponent(serviceName);
//        PendingIntent pendingBroadcast = PendingIntent.getBroadcast(context, 0, intentBroadcast, 0);
//        views.setOnClickPendingIntent(R.id.button108, pendingBroadcast);

        Intent intentService = new Intent(AppService01.ACTION01);
        intentService.setComponent(serviceName);
        PendingIntent pendingService = PendingIntent.getService(context, 0, intentService, 0);
        views.setOnClickPendingIntent(R.id.button109, pendingService);

        views.setTextViewText(R.id.textView41, mTxt);

        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate=" + this);

        RemoteViews views = prepareView(context);

        // There may be multiple widgets active, so update all of them
        if (appWidgetIds != null) {
            final int N = appWidgetIds.length;
            for (int i = 0; i < N; i++) {
                updateAppWidget(context, appWidgetManager, appWidgetIds[i], views);
            }
        } else {
            appWidgetManager.updateAppWidget(new ComponentName(context, this.getClass()), views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.d(TAG, "onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.d(TAG, "onDisabled");
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, RemoteViews views) {

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

