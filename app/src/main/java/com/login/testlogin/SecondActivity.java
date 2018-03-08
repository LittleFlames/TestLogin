package com.login.testlogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText invitation,phone,validation,et_passwd,true_passwd;
    private Button registered;
    private ImageView iv_showPassword;//密码是否明文显示
    private Boolean showPassword = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_main);
        invitation = findViewById(R.id.invitation);
        phone = findViewById(R.id.phone);
        validation = findViewById(R.id.validation);
        et_passwd = findViewById(R.id.passwd);
        true_passwd = findViewById(R.id.true_passwd);
        registered = findViewById(R.id.registered);
        iv_showPassword = (ImageView) findViewById(R.id.iv_showPassword);
        registered.setOnClickListener(this);
        iv_showPassword.setOnClickListener(this);

    }


    public void okhttp(String phone,String passWord){
        String url = "http://test2.longruinet.com/Registered.ashx";
        OkHttpClient okHttpClient = new OkHttpClient();
//http://test2.longruinet.com/Registered.ashx?loginName=loginName&loginPwd=123456&checkCode=123456&invitationCode=124
        RequestBody body = new FormBody.Builder()
                .add("loginName", phone)
                .add("loginPwd", passWord)
                .add("checkCode", "123456")
                .add("invitationCode", "123")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Gson gson = new Gson();
                final Bean bean = gson.fromJson(string, Bean.class);
                final Bean.ResDataBean resData = bean.getResData();
                System.out.println(resData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SecondActivity.this, resData.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registered:
                String invitation = this.invitation.getText().toString();
                String phone = this.phone.getText().toString();
                String validation = this.validation.getText().toString();
                String passwd = this.et_passwd.getText().toString();
                String true_passwd = this.true_passwd.getText().toString();
               if(TextUtils.isEmpty(phone)){
                    Toast.makeText(this, "手机号不能为空...", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(validation)){
                    Toast.makeText(this, "验证码不能为空...", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(passwd)){
                    Toast.makeText(this, "密码不能为空...", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(true_passwd)){
                    Toast.makeText(this, "确认密码不能为空...", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!FormValidation.isMobile(phone)){
                    Toast.makeText(this, "请输入正确手机号...", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!passwd.equals(true_passwd)){
                    Toast.makeText(this, "密码输入不一致...", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    okhttp(phone,MD5Util.MD5(passwd));
                }
            case R.id.iv_showPassword:
                if (showPassword) {// 显示密码
                    iv_showPassword.setImageDrawable(getResources().getDrawable(R.mipmap.eye2));
                    et_passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_passwd.setSelection(et_passwd.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    iv_showPassword.setImageDrawable(getResources().getDrawable(R.mipmap.eye2));
                    et_passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_passwd.setSelection(et_passwd.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;
        }
    }
}
