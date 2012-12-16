// SmaRM - Android Feedback Notification App (for demonstration purposes)
// Copyright (C) 2012/2013 github.com/SmaRM

// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.  If not, see http://www.gnu.org/licenses/

package org.smarthome.android;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class FeedbackActivity extends Activity {

	public static final String SERVER = "http://192.168.0.123:8080";
	
	public static final String DATA_BASE = SERVER + "/org.smarthome.data/rest";
	public static final String FEEDBACK_ALL = DATA_BASE + "/resources/feedback.xml/all";
	public static final String NOTIFICATION_TEXT = SERVER + "/SmaRM/feedback";

	public static int NOTIFICATION_ID = 1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        
		Button btnRetreive = (Button) findViewById(R.id.buttonRetreive);

		btnRetreive.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View view) {
				retreiveFeedback();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_feedback, menu);
        return true;
    }
    
    /**
     * Generates a demo feedback notification message that redirects to an
     * demo web service or web site.
     */
	public void retreiveFeedback() {
		try {
			NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(R.drawable.ic_launcher, "Feedback Notification!", System.currentTimeMillis());
			Context context = getApplicationContext();

			String notificationTitle = "Pending Feedback Entries!";
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FEEDBACK_ALL));
			PendingIntent pendingIntent = PendingIntent.getActivity(FeedbackActivity.this, 0, intent,
					Intent.FLAG_ACTIVITY_NEW_TASK);

			notification.defaults |= Notification.DEFAULT_SOUND;
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notification.defaults |= Notification.DEFAULT_LIGHTS;
			
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			notification.setLatestEventInfo(context, notificationTitle, NOTIFICATION_TEXT, pendingIntent);
			notificationManager.notify(NOTIFICATION_ID, notification);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
