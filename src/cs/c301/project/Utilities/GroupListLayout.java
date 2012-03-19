package cs.c301.project.Utilities;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

public class GroupListLayout extends LinearLayout {

	private static Activity activity;

    public GroupListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupListLayout(Context context) {
        super(context);
    }

    public static void setFilterActivity(Activity activity2) {
    	activity = activity2;
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (activity != null && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            KeyEvent.DispatcherState state = getKeyDispatcherState();
            if (state != null) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                    state.startTracking(event, this);
                    return true;
                } else if (event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled() && state.isTracking(event)) {
                	activity.onBackPressed();
                    return true;
                }
            }
        }

        return super.dispatchKeyEventPreIme(event);
    }
}
