// This file was generated by PermissionsDispatcher. Do not modify!
package permissions.dispatcher.sample;

import android.support.v4.app.ActivityCompat;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.PermissionUtils;

import java.lang.ref.WeakReference;

final class MainActivityPermissionsDispatcher {
    private static final int REQUEST_SHOWCAMERA = 0;

    private static final String[] PERMISSION_SHOWCAMERA = new String[] {"android.permission.CAMERA"};

    private static final int REQUEST_SHOWCONTACTS = 1;

    private static final String[] PERMISSION_SHOWCONTACTS = new String[] {"android.permission.READ_CONTACTS","android.permission.WRITE_CONTACTS"};

    private MainActivityPermissionsDispatcher() {
    }

    static void showCameraWithCheck(MainActivity target) {
        if (PermissionUtils.hasSelfPermissions(target, PERMISSION_SHOWCAMERA)) {
            target.showCamera();
        } else {
            if (PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_SHOWCAMERA)) {
                target.showRationaleForCamera(new ShowCameraPermissionRequest(target));
            } else {
                ActivityCompat.requestPermissions(target, PERMISSION_SHOWCAMERA, REQUEST_SHOWCAMERA);
            }
        }
    }

    static void showContactsWithCheck(MainActivity target) {
        if (PermissionUtils.hasSelfPermissions(target, PERMISSION_SHOWCONTACTS)) {
            target.showContacts();
        } else {
            if (PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_SHOWCONTACTS)) {
                target.showRationaleForContact(new ShowContactsPermissionRequest(target));
            } else {
                ActivityCompat.requestPermissions(target, PERMISSION_SHOWCONTACTS, REQUEST_SHOWCONTACTS);
            }
        }
    }

    static void onRequestPermissionsResult(MainActivity target, int requestCode, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_SHOWCAMERA:
                if (PermissionUtils.getTargetSdkVersion(target) < 23 && !PermissionUtils.hasSelfPermissions(target, PERMISSION_SHOWCAMERA)) {
                    target.onCameraDenied();
                    return;
                }
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    target.showCamera();
                } else {
                    if (!PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_SHOWCAMERA)) {
                        target.onCameraNeverAskAgain();
                    } else {
                        target.onCameraDenied();
                    }
                }
                break;
            case REQUEST_SHOWCONTACTS:
                if (PermissionUtils.getTargetSdkVersion(target) < 23 && !PermissionUtils.hasSelfPermissions(target, PERMISSION_SHOWCONTACTS)) {
                    return;
                }
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    target.showContacts();
                }
                break;
            default:
                break;
        }
    }

    private static final class ShowCameraPermissionRequest implements PermissionRequest {
        private final WeakReference<MainActivity> weakTarget;

        private ShowCameraPermissionRequest(MainActivity target) {
            this.weakTarget = new WeakReference<MainActivity>(target);
        }

        @Override
        public void proceed() {
            MainActivity target = weakTarget.get();
            if (target == null) return;
            ActivityCompat.requestPermissions(target, PERMISSION_SHOWCAMERA, REQUEST_SHOWCAMERA);
        }

        @Override
        public void cancel() {
            MainActivity target = weakTarget.get();
            if (target == null) return;
            target.onCameraDenied();
        }
    }

    private static final class ShowContactsPermissionRequest implements PermissionRequest {
        private final WeakReference<MainActivity> weakTarget;

        private ShowContactsPermissionRequest(MainActivity target) {
            this.weakTarget = new WeakReference<MainActivity>(target);
        }

        @Override
        public void proceed() {
            MainActivity target = weakTarget.get();
            if (target == null) return;
            ActivityCompat.requestPermissions(target, PERMISSION_SHOWCONTACTS, REQUEST_SHOWCONTACTS);
        }

        @Override
        public void cancel() {
        }
    }
}
