package tw.com.chainsea.bruce.ife;

import java.util.List;

/**
 * tw.com.chainsea.bruce.ife
 * Created by andy on 17-1-10.
 */

public interface PermissionListener {
    void onGranted();

    void onDenied(List<String> permissionList);
}
