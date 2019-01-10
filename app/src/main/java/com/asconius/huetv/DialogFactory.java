package com.asconius.huetv;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

public class DialogFactory {

    private ProgressDialog pdialog;
    private static DialogFactory dialogs;

    public static synchronized DialogFactory getInstance() {
        if (dialogs == null) {
            dialogs = new DialogFactory();
        }
        return dialogs;
    }

    public static void showErrorDialog(Context activityContext, String msg, int btnNameResId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);
        builder.setTitle(R.string.title_error).setMessage(msg).setPositiveButton(btnNameResId, null);
        AlertDialog alert = builder.create();
        alert.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        if (! ((Activity) activityContext).isFinishing()) {
            alert.show();
        }
    }

    public void closeProgressDialog() {
        if (pdialog != null) {
            pdialog.dismiss();
            pdialog = null;
        }
    }

    public void showProgressDialog(int resID, Context ctx) {
        String message = ctx.getString(resID);
        pdialog = ProgressDialog.show(ctx, null, message, true, true);
        pdialog.setCancelable(false);
    }

    public static void showAuthenticationErrorDialog(
            final Activity activityContext, String msg, int btnNameResId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);
        builder.setTitle(R.string.title_error).setMessage(msg)
                .setPositiveButton(btnNameResId, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activityContext.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        alert.show();
    }
}
