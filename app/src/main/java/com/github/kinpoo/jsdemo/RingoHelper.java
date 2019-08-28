package com.github.kinpoo.jsdemo;

import android.content.Context;

import com.wshunli.assets.CopyAssets;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.ringojs.engine.RhinoEngine;
import org.ringojs.engine.RingoConfig;
import org.ringojs.repository.Repository;
import org.ringojs.repository.ZipRepository;

import java.io.File;
import java.io.IOException;

public class RingoHelper {
    private static final String RINGO_DIR_NAME = "ringo";
    private static final String MODULES_JAR_NAME = "ringo-modules.jar";

    private static RingoHelper instance;

    private static RhinoEngine rhinoEngine;
    private static Context appContext;

    private RingoHelper(Context context) {
        appContext = context;
        extractRingoAssets();
        initRhinoEngine();
    }

    public static void initInstance(Context context) {
        if (instance == null) {
            instance = new RingoHelper(context);
        }
    }

    public static RhinoEngine getRhinoEngine() {
        return rhinoEngine;
    }

    public static void put(String name, Object value) {
        Scriptable scope = rhinoEngine.getScope();
        ScriptableObject.putProperty(scope, name, rhinoEngine.asJavaObject(value));
    }

    private void extractRingoAssets() {
        CopyAssets.with(appContext)
                .from(RINGO_DIR_NAME)
                .to(getCacheDir().getAbsolutePath())
                .copy();
    }

    private void initRhinoEngine() {
        Repository ringoHome = null;
        try {
            ringoHome = new ZipRepository(getRingoDir(MODULES_JAR_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] systemModules = {"modules", "packages"};
        String[] userModules = {getRingoDir().getAbsolutePath()};

        RingoConfig ringoConfig = null;
        try {
            ringoConfig = new RingoConfig(ringoHome, userModules, systemModules);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            rhinoEngine = new RhinoEngine(ringoConfig, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getCacheDir() {
        return appContext.getCacheDir();
    }

    private File getRingoDir() {
        return new File(getCacheDir(), RINGO_DIR_NAME);
    }

    private File getRingoDir(String name) {
        return new File(getRingoDir(), name);
    }
}
