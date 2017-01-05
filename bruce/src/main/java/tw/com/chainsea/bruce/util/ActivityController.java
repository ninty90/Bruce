package tw.com.chainsea.bruce.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * ActivityController
 * Created by Fleming on 2017/1/5.
 */

public class ActivityController {

    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishAllActivitys() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
