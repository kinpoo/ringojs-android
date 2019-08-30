package com.github.kinpoo.jsdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.kinpoo.ringojs_android.RingoHelper;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RingoHelper.initInstance(this);
        RingoHelper.put("app_activity", this);
    }

    public void toast(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void button(View v) {
        switch (v.getId()) {
            case R.id.execExpr:
                execExpr(v);
                break;
            case R.id.execTestJs:
                execTestJs(v);
                break;
            case R.id.invokeTestHello:
                invokeTestHello(v);
                break;
            case R.id.execTest2Class:
                execTest2Class(v);
                break;
            case R.id.invokeTest2Hello2:
                invokeTest2Hello2(v);
                break;

        }
    }

    private void execExpr(View v) {
        Object result = null;
        String expr = String.valueOf(((EditText) findViewById(R.id.exprText)).getText());
        try {
            result = RingoHelper.getRhinoEngine().evaluateExpression(expr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.toast("result: " + String.valueOf(result));
    }

    private void execTestJs(View v) {
        try {
            RingoHelper.getRhinoEngine().runScript("test.js");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void invokeTestHello(View v) {
        // fork to avoid sleep block main ui thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RingoHelper.getRhinoEngine().invoke("test", "hello");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void execTest2Class(View v) {
        // fork to avoid sleep block main ui thread
        try {
            RingoHelper.getRhinoEngine().runScript("test2.class");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void invokeTest2Hello2(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RingoHelper.getRhinoEngine().invoke("test2.class", "hello2");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
