package com.demo.moonphases.common;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.moonphases.R;


public class ChangeConsent_Activity extends AppCompatActivity {

    
    TextView txtConcent;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.change_consent);

        this.txtConcent = (TextView) findViewById(R.id.txtConcent);
    }


    public void showdailog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.consent_form_flyappsllc);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        TextView textView = (TextView) dialog.findViewById(R.id.txt_privacy);
        textView.setText(Html.fromHtml("<a href='https://flylabsllc.wixsite.com/flylabsllc'>Learn how our partners will collect and use data under our Privacy Policy.</a>"));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeConsent_Activity.this, Privacy_Policy_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ChangeConsent_Activity.this.startActivity(intent);
            }
        });
        ((LinearLayout) dialog.findViewById(R.id.lin_yes)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ChangeConsent_Activity.this.finish();
            }
        });
        ((LinearLayout) dialog.findViewById(R.id.lin_no)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ChangeConsent_Activity.this.finish();
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
