/*
 * This project implements the localytics android integration steps found here:
 * 	http://www.localytics.com/docs/sdks-integration-guides/android/
 * 
 */

/*
 * Initial setup: You need to add the application key as Meta Data in the AndroidManifest.xml file.
 * It looks like this:
 *  <meta-data android:name="LOCALYTICS_APP_KEY" android:value=" APP KEY FROM STEP 2"/>
 * 
 * Also need to make sure the App has access to the Internet:
 *  <uses-permission android:name="android.permission.INTERNET">
 */

package com.example.localyticsdemo;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;


//Step 1: Import the class by adding the following to the top of your main activity.
import com.localytics.android.*;

public class MainActivity extends Activity {
	//Step 2: Add the session object as a private member variable inside your class.
	private LocalyticsSession localyticsSession;
	
	
	/*
	 * Step 3: In OnCreate, instantiate the object, open the session, and upload any data
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	 
	    // Activity Creation Code
	 
	    // Instantiate the object
	    this.localyticsSession = new LocalyticsSession(
	                   this.getApplicationContext());  // Context used to access device resources
	 
	    this.localyticsSession.open();                // open the session
	    this.localyticsSession.upload();      // upload any data
	 
	    // At this point, Localytics Initialization is done.  After uploads complete nothing
	    // more will happen due to Localytics until the next time you call it.
	    
	    
	    // Fire off a custom event tag:
	    this.localyticsSession.tagEvent("demo application start");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*
	 * Step 4: In onResume, call open and upload again. In most cases, the session will be opened 
	 *  and data uploaded by the calls in onCreate. However activities may go from onPause to 
	 *  onResume without passing through onCreate and therefore, the open call needs to appear here as 
	 *  well. If it is called twice for an already open session it will be ignored.
	 */
	@Override
	public void onResume() {
	    super.onResume();
	    this.localyticsSession.open();
	}
	
	/*
	 * Step 5: In onPause, close the session and upload data.. 
	 *  onPause is the last state which is guaranteed to be called so it makes the most sense for the close call. 
	 *  This may cause multiple close events to occur but only the final close is honored.
	 */
	@Override
	public void onPause() {
	    this.localyticsSession.close();
	    this.localyticsSession.upload();
	    super.onPause();
	}
	
	/*
	 * Step 6: Repeat the above steps for every other activities in your application. 
	 *  Calling open() in every activity’s onCreate and onResume will cause every activity to reconnect to the 
	 *  already opened session instead of creating a new one.
	 */

}
