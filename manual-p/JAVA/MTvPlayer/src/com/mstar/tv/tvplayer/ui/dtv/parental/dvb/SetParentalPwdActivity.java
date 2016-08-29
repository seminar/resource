
package com.mstar.tv.tvplayer.ui.dtv.parental.dvb;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mstar.android.tv.TvParentalControlManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.MainMenuActivity;
import com.mstar.tv.tvplayer.ui.TvIntent;
import com.mstar.tvframework.MstarBaseActivity;

public class SetParentalPwdActivity extends MstarBaseActivity {

    private EditText old_pwd1;

    private EditText old_pwd2;

    private EditText old_pwd3;

    private EditText old_pwd4;

    private EditText new_pwd1;

    private EditText new_pwd2;

    private EditText new_pwd3;

    private EditText new_pwd4;

    private EditText confirm_pwd1;

    private EditText confirm_pwd2;

    private EditText confirm_pwd3;

    private EditText confirm_pwd4;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_parental_pwd);
        findView();
        new_pwd1.setEnabled(false);
        new_pwd2.setEnabled(false);
        new_pwd3.setEnabled(false);
        new_pwd4.setEnabled(false);

        confirm_pwd1.setEnabled(false);
        confirm_pwd2.setEnabled(false);
        confirm_pwd3.setEnabled(false);
        confirm_pwd4.setEnabled(false);
    }

    private void findView() {
        old_pwd1 = (EditText) findViewById(R.id.old_pwd1);
        old_pwd2 = (EditText) findViewById(R.id.old_pwd2);
        old_pwd3 = (EditText) findViewById(R.id.old_pwd3);
        old_pwd4 = (EditText) findViewById(R.id.old_pwd4);
        new_pwd1 = (EditText) findViewById(R.id.new_pwd1);
        new_pwd2 = (EditText) findViewById(R.id.new_pwd2);
        new_pwd3 = (EditText) findViewById(R.id.new_pwd3);
        new_pwd4 = (EditText) findViewById(R.id.new_pwd4);
        confirm_pwd1 = (EditText) findViewById(R.id.confirm_pwd1);
        confirm_pwd2 = (EditText) findViewById(R.id.confirm_pwd2);
        confirm_pwd3 = (EditText) findViewById(R.id.confirm_pwd3);
        confirm_pwd4 = (EditText) findViewById(R.id.confirm_pwd4);
        setMyEditListener(old_pwd1, old_pwd2);
        setMyEditListener(old_pwd2, old_pwd3);
        setMyEditListener(old_pwd3, old_pwd4);
        setMyEditListener(old_pwd4, new_pwd1);
        setMyEditListener(new_pwd1, new_pwd2);
        setMyEditListener(new_pwd2, new_pwd3);
        setMyEditListener(new_pwd3, new_pwd4);
        setMyEditListener(new_pwd4, confirm_pwd1);
        setMyEditListener(confirm_pwd1, confirm_pwd2);
        setMyEditListener(confirm_pwd2, confirm_pwd3);
        setMyEditListener(confirm_pwd3, confirm_pwd4);
        setMyEditListener(confirm_pwd4, null);

    }

    private void setMyEditListener(EditText edittext, final View view) {
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if (null != view) {
                    view.requestFocus();
                    if (view == new_pwd1) {
                        new_pwd1.setEnabled(true);
                        new_pwd2.setEnabled(true);
                        new_pwd3.setEnabled(true);
                        new_pwd4.setEnabled(true);
                    }
                    if (view == confirm_pwd1) {
                        confirm_pwd1.setEnabled(true);
                        confirm_pwd2.setEnabled(true);
                        confirm_pwd3.setEnabled(true);
                        confirm_pwd4.setEnabled(true);
                    }
                } else {
                    setpassword();
                }
            }
        });
    }

    private void setpassword() {
        int o1, o2, o3, o4, old_pwd;
        int n1, n2, n3, n4, new_pwd;
        int c1, c2, c3, c4, conf_pwd;

        if (old_pwd1.getText().toString().isEmpty() || old_pwd2.getText().toString().isEmpty()
                || old_pwd3.getText().toString().isEmpty()
                || old_pwd4.getText().toString().isEmpty()
                || new_pwd1.getText().toString().isEmpty()
                || new_pwd2.getText().toString().isEmpty()
                || new_pwd3.getText().toString().isEmpty()
                || new_pwd4.getText().toString().isEmpty()
                || confirm_pwd1.getText().toString().isEmpty()
                || confirm_pwd2.getText().toString().isEmpty()
                || confirm_pwd3.getText().toString().isEmpty()
                || confirm_pwd4.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(TvIntent.MAINMENU);
            intent.putExtra("currentPage", MainMenuActivity.OPTION_PAGE);
            startActivity(intent);
            finish();
            return;
        }

        o1 = Integer.parseInt(old_pwd1.getText().toString());
        o2 = Integer.parseInt(old_pwd2.getText().toString());
        o3 = Integer.parseInt(old_pwd3.getText().toString());
        o4 = Integer.parseInt(old_pwd4.getText().toString());
        old_pwd = getpassword(o1, o2, o3, o4);
        n1 = Integer.parseInt(new_pwd1.getText().toString());
        n2 = Integer.parseInt(new_pwd2.getText().toString());
        n3 = Integer.parseInt(new_pwd3.getText().toString());
        n4 = Integer.parseInt(new_pwd4.getText().toString());
        new_pwd = getpassword(n1, n2, n3, n4);
        c1 = Integer.parseInt(confirm_pwd1.getText().toString());
        c2 = Integer.parseInt(confirm_pwd2.getText().toString());
        c3 = Integer.parseInt(confirm_pwd3.getText().toString());
        c4 = Integer.parseInt(confirm_pwd4.getText().toString());
        conf_pwd = getpassword(c1, c2, c3, c4);
        Log.i("SetParentalPwdActivity", "old:" + old_pwd + " new:" + new_pwd + " conf:" + conf_pwd);
        if (new_pwd != conf_pwd) {
            Toast.makeText(this, "incorrect password", Toast.LENGTH_SHORT).show();
        } else {
            if (old_pwd != TvParentalControlManager.getInstance().getParentalPassword()) {
                Toast.makeText(this, "initial password error", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences share2 = getSharedPreferences("menu_check_pwd",
                        Activity.MODE_PRIVATE);
                Editor editor = share2.edit();
                editor.putBoolean("pwd_ok", true);
                editor.commit();
                TvParentalControlManager.getInstance().setParentalPassword(new_pwd);
                Intent intent = new Intent(TvIntent.MAINMENU);
                intent.putExtra("currentPage", MainMenuActivity.OPTION_PAGE);
                startActivity(intent);
                finish();
            }
        }

    }

    private int getpassword(int i, int j, int k, int x) {
        return i * 1000 + j * 100 + k * 10 + x;
    }
}
