package com.bjtu.redis;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class FileMonitor {//文件监听

    FileAlterationMonitor monitor = null;

    public FileMonitor(long interval) throws Exception {
        monitor = new FileAlterationMonitor(interval);
    }

    public void monitor(String path, FileAlterationListener listener) {//给文件添加监听
        FileAlterationObserver observer = new FileAlterationObserver(new File(path));
        monitor.addObserver(observer);
        observer.addListener(listener);
    }

    public void onstart() throws Exception {
        monitor.start();
    }
}
