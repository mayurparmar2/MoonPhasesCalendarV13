package com.demo.moonphases.common;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.demo.moonphases.R;


public class Privacy_Policy_Fragment extends Fragment {
    private static final String TAG = "policy";
    private ProgressDialog progress;
    private WebView webvw;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.privacy_fragment, viewGroup, false);
        WebView webView = (WebView) inflate.findViewById(R.id.privacy_webview);
        this.webvw = webView;
        webView.getSettings().setJavaScriptEnabled(true);
        this.webvw.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        final AlertDialog create = new AlertDialog.Builder(getActivity()).create();
        this.progress = ProgressDialog.show(getActivity(), "Please Wait...", "Loading...");
        this.webvw.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView2, String str) {
                Log.i(Privacy_Policy_Fragment.TAG, "Processing webview url click...");
                webView2.loadUrl(str);
                return true;
            }

            @Override
            public void onPageFinished(WebView webView2, String str) {
                Log.i(Privacy_Policy_Fragment.TAG, "Finished loading URL: " + str);
                if (Privacy_Policy_Fragment.this.progress.isShowing()) {
                    Privacy_Policy_Fragment.this.progress.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView webView2, int i, String str, String str2) {
                Log.e(Privacy_Policy_Fragment.TAG, "Error: " + str);
                create.setTitle("Error");
                create.setMessage(str);
                create.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i2) {
                    }
                });
                create.show();
            }
        });
        this.webvw.loadUrl("https://www.google.com");
        return inflate;
    }
}
