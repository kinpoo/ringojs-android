package com.github.kinpoo.jsdemo;

import android.os.Build;

import org.mozilla.javascript.GeneratedClassLoader;
import org.ringojs.engine.RingoContextFactory;

import java.io.File;
import java.lang.reflect.Constructor;

public class ContextClassLoader implements RingoContextFactory.ContextClassLoader {

    private final File cacheDir;

    public ContextClassLoader(File cacheDir) {
        this.cacheDir = cacheDir;
    }

    @Override
    public GeneratedClassLoader createClassLoader(ClassLoader parent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return createInMemoryAndroidClassLoader(parent);
        }
        return createFileAndroidClassLoader(parent);
    }

    private GeneratedClassLoader createInMemoryAndroidClassLoader(ClassLoader parent) {
        try {
            Class<?> clazz = Class.forName("com.faendir.rhino_android.InMemoryAndroidClassLoader");
            Constructor<?> constructor = clazz.getDeclaredConstructor(ClassLoader.class);
            constructor.setAccessible(true);
            return (GeneratedClassLoader) constructor.newInstance(parent);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private GeneratedClassLoader createFileAndroidClassLoader(ClassLoader parent) {
        try {
            Class<?> clazz = Class.forName("com.faendir.rhino_android.FileAndroidClassLoader");
            Constructor<?> constructor = clazz.getDeclaredConstructor(ClassLoader.class, File.class);
            constructor.setAccessible(true);
            return (GeneratedClassLoader) constructor.newInstance(parent, cacheDir);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }
}