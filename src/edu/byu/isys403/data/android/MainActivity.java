package edu.byu.isys403.data.android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

	ViewFlipper vf = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vf = (ViewFlipper)findViewById(R.id.viewFlipper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void loginSubmit(View view) {
    	vf.showNext();
    }
}
