package com.bjtu.redis;
import java.io.File;

import org.apache.commons.io.monitor.*;

public class FileListener implements FileAlterationListener {//文件监听类

    CustomRedis customRedis;
    public FileListener(CustomRedis customRedis){
    }

    @Override
        public void onStart(FileAlterationObserver observer) {//文件初始化
        }

        @Override
        public void onDirectoryCreate(File directory) {//有操作创建了新的文件夹
        }

        @Override
        public void onDirectoryChange(File directory) {//有操作改变了新的文件夹
        }

        @Override
        public void onDirectoryDelete(File directory) {//有操作删除了文件夹
        }

        @Override
        public void onFileCreate(File file) {//有操作新建了文件
        }

        @Override
        public void onFileChange(File file) {//有文件改变
            System.out.println("文件变化");
            customRedis.init();
        }

        @Override
        public void onFileDelete(File file) {//有文件删除
        }

        @Override
        public void onStop(FileAlterationObserver observer) {
        }
    }