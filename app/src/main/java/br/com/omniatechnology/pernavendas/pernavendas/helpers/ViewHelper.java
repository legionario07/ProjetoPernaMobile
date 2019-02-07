package br.com.omniatechnology.pernavendas.pernavendas.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import rx.functions.Action;
import rx.functions.Action0;

public class ViewHelper {

    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context){

        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage(context.getString(R.string.processando));
        progressDialog.setTitle(context.getString(R.string.aguarde));
        progressDialog.show();
    }

    public static void showProgressDialog(Context context, String message){

        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage(message);
        progressDialog.setTitle(context.getString(R.string.aguarde));
        progressDialog.show();
    }

    public static void showProgressDialog(Context context, String message, String title){

        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage(message);
        progressDialog.setTitle(title);
        progressDialog.show();
    }


    public static void closeProgressDialog(){

        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
            progressDialog = null;
        }

    }

    public static Action0 showProgressDialogAction(final Context context){
        return new Action0() {
            @Override
            public void call() {
                showProgressDialog(context);
            }
        };
    }


    public static Action0 closeProgressDialogAction(final Context context){
        return new Action0() {
            @Override
            public void call() {
                closeProgressDialog();
            }
        };
    }



}

