package com.example.submission2.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetServices extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteView(this.getApplicationContext(),intent);
    }
}
