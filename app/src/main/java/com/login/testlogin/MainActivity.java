package com.login.testlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_passwd,et_phone;
    private ImageView iv_showPassword;//密码是否明文显示
    private Boolean showPassword = true;
    private Button login,registered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_passwd = (EditText) findViewById(R.id.et_passwd);
        iv_showPassword = (ImageView) findViewById(R.id.iv_showPassword);
        registered = findViewById(R.id.registered);

        et_phone = findViewById(R.id.et_phone);
        iv_showPassword.setImageDrawable(getResources().getDrawable(R.mipmap.eye2));
        login = findViewById(R.id.login);
        iv_showPassword.setOnClickListener(this);
        login.setOnClickListener(this);
        registered.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.login:
                String phone = et_phone.getText().toString();
                String passwd = et_passwd.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(this, "手机号不能为空...", Toast.LENGTH_SHORT).show();
                    return ;
                }else if(TextUtils.isEmpty(passwd)){
                    Toast.makeText(this, "密码不能为空...", Toast.LENGTH_SHORT).show();
                    return ;
                }else {
                    boolean mobile = FormValidation.isMobile(phone);
                    if(!mobile){
                        Toast.makeText(this, "请输入正确手机号...", Toast.LENGTH_SHORT).show();
                    }else {
                        okhttp(phone,MD5Util.MD5(passwd));
                        System.out.println(MD5Util.MD5(passwd));
                    }
                }
                break;
            case R.id.registered:
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void okhttp(String phone,String passWord){
        String url = "http://test2.longruinet.com/Login.ashx";
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("loginName", phone)
                .add("loginPwd", passWord)
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
                        Toast.makeText(MainActivity.this, resData.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
