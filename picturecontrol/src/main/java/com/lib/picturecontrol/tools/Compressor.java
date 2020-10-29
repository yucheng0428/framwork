package com.lib.picturecontrol.tools;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.lib.common.baseUtils.ImageUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;

/**
 * 压缩工具类
 */
public class Compressor {
    private int maxWidth = 612;
    private int maxHeight = 816;
    private CompressFormat compressFormat;
    private int quality;
    private String destinationDirectoryPath;

    public Compressor(Context context) {
        this.compressFormat = CompressFormat.JPEG;
        this.quality = 80;
        this.destinationDirectoryPath = context.getCacheDir().getPath() + File.separator + "images";
    }

    public Compressor setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public Compressor setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public Compressor setCompressFormat(CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
        return this;
    }

    public Compressor setQuality(int quality) {
        this.quality = quality;
        return this;
    }

    public Compressor setDestinationDirectoryPath(String destinationDirectoryPath) {
        this.destinationDirectoryPath = destinationDirectoryPath;
        return this;
    }

    public File compressToFile(File imageFile) throws IOException {
        return this.compressToFile(imageFile, imageFile.getName());
    }

    public File compressToFile(File imageFile, String compressedFileName) throws IOException {
        return ImageUtil.compressImage(imageFile, this.maxWidth, this.maxHeight, this.compressFormat, this.quality, this.destinationDirectoryPath + File.separator + compressedFileName);
    }

    public Bitmap compressToBitmap(File imageFile) throws IOException {
        return ImageUtil.decodeSampledBitmapFromFile(imageFile, this.maxWidth, this.maxHeight);
    }

    public Flowable<File> compressToFileAsFlowable(File imageFile) {
        return this.compressToFileAsFlowable(imageFile, imageFile.getName());
    }

    public Flowable<File> compressToFileAsFlowable(final File imageFile, final String compressedFileName) {
        return Flowable.defer(new Callable<Flowable<File>>() {
            public Flowable<File> call() {
                try {
                    return Flowable.just(Compressor.this.compressToFile(imageFile, compressedFileName));
                } catch (IOException var2) {
                    return Flowable.error(var2);
                }
            }
        });
    }

    public Flowable<Bitmap> compressToBitmapAsFlowable(final File imageFile) {
        return Flowable.defer(new Callable<Flowable<Bitmap>>() {
            public Flowable<Bitmap> call() {
                try {
                    return Flowable.just(Compressor.this.compressToBitmap(imageFile));
                } catch (IOException var2) {
                    return Flowable.error(var2);
                }
            }
        });
    }
}

