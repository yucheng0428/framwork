package com.lib.picturecontrol.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.FileUtils;
import com.lib.common.baseUtils.PhoneUtils;
import com.lib.common.baseUtils.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

public class ImageUtilsEx {

	// public final static int BITMAP_MAX_SIZE = 307200; // 300K
	public final static int BITMAP_MAX_SIZE = 102400; // 100K

	public final static int IMG_DOWN_SUCCESS = 0;
	public final static int IMG_DOWN_FAIL = -1;
	public final static int IMG_DOWNLOADING = 1;

	public static Bitmap getFitBitmap(String path) {
		return getFitBitmap(path, 0);
	}

	public static Bitmap getFitBitmap(String path, int optsSize) {
		File file = new File(path);
		if (!file.exists())
			return null;
		Bitmap bitmap = null;
		try {
			if (file.length() <= BITMAP_MAX_SIZE) {
				try {
					Options options = new Options();
					options.inTempStorage = new byte[16 * 1024];
					// 附加
					options.inPurgeable = true;
					options.inPreferredConfig = Config.RGB_565;
					options.inInputShareable = true;
					options.inJustDecodeBounds = false;
					try {
						FileInputStream fis = new FileInputStream(path);
						// bitmap = BitmapFactory.decodeFile(path, options);
						bitmap = BitmapFactory.decodeStream(fis, new Rect(-1,
								-1, -1, -1), options);
						fis.close();
					} catch (Exception e) {
					}
				} catch (Exception e) {
				}
			} else {
				int inSampleSize = (int) (file.length() / BITMAP_MAX_SIZE + 1);
				Options options = new Options();
				options.inTempStorage = new byte[16 * 1024];
				options.inSampleSize = inSampleSize;
				// 附加
				options.inPurgeable = true;
				options.inPreferredConfig = Config.RGB_565;
				options.inInputShareable = true;
				options.inJustDecodeBounds = false;
				try {
					FileInputStream fis = new FileInputStream(path);
					// bitmap = BitmapFactory.decodeFile(path, options);
					bitmap = BitmapFactory.decodeStream(fis, new Rect(-1, -1,
							-1, -1), options);
					fis.close();
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}
		return bitmap;
	}

	public static Bitmap getFitBitmapFromIs(InputStream fis, int optsSize) {
		// File file = new File(path);
		// if (!file.exists())
		// return null;
		int size = 0;
		try {
			size = fis.available();
			// while(size!=0){
			// size = fis.available();
			// System.out.println("单次size="+size);
			// }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Bitmap bitmap = null;
		// System.out.println("图片流大小=" + size / 1000 + "k");
		try {
			if (size <= BITMAP_MAX_SIZE) {
				try {
					Options options = new Options();
					options.inTempStorage = new byte[16 * 1024];
					// 附加
					options.inPurgeable = true;
					options.inPreferredConfig = Config.RGB_565;
					options.inInputShareable = true;
					options.inJustDecodeBounds = false;
					try {
						// FileInputStream fis = new FileInputStream(path);
						// bitmap = BitmapFactory.decodeFile(path, options);
						bitmap = BitmapFactory.decodeStream(fis, new Rect(-1,
								-1, -1, -1), options);
						// fis.close();
					} catch (Exception e) {
					} catch (Error e) {
						System.gc();
						options.inSampleSize = 4;// 降低分辨率
						bitmap = BitmapFactory.decodeStream(fis, new Rect(-1,
								-1, -1, -1), options);
					} finally {
						fis.close();
					}
				} catch (Exception e) {
				}
			} else {
				int inSampleSize = (int) (size / BITMAP_MAX_SIZE + 1);
				Options options = new Options();
				options.inTempStorage = new byte[16 * 1024];
				options.inSampleSize = inSampleSize;
				// 附加
				options.inJustDecodeBounds = false;
				options.inPurgeable = true;
				options.inPreferredConfig = Config.RGB_565;
				options.inInputShareable = true;
				try {
					// FileInputStream fis = new FileInputStream(path);
					// bitmap = BitmapFactory.decodeFile(path, options);
					bitmap = BitmapFactory.decodeStream(fis, new Rect(-1, -1,
							-1, -1), options);
					// fis.close();
				} catch (Exception e) {
				} finally {
					fis.close();
				}
			}
		} catch (Exception e) {
		}
		return bitmap;
	}

	public static boolean saveBmpToSd(Bitmap bm, String url) {
		if (bm == null) {
			return false;
		}
		String filename = convertUrlToFileName(url);
//		String dir = getDirectory(filename);
//		File file = new File(dir + "/" + filename);
		File file = new File(url);
		// File file = new File(dir);
		// System.out.println("file全路径="+file);
		try {
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);
			bm.compress(CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();
			return true;
		} catch (Exception e) {
			// Log.w(MyApplication.TAG, "FileNotFoundException");
			// StringWriter sw = new StringWriter();
			// e.printStackTrace(new PrintWriter(sw, true));
			// System.out.println("报错:" + sw.toString());
		} catch (Error e) {
			System.out.println("SD卡已满");
			// if(HomeActivity.getInstance()!=null){
			// Toast.makeText(HomeActivity.getInstance(), "SD卡已满没法保存图片和音乐",
			// Toast.LENGTH_LONG).show();
			// }
		}
		return false;
	}

	public static boolean savePngToSd(Bitmap bm, String url) {
		if (bm == null) {
			return false;
		}
		String filename = convertUrlToFileName(url);
		String dir = getDirectory(filename);
		File file = new File(dir + "/" + filename);
		// File file = new File(dir);
		try {
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);
			bm.compress(CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();
			return true;
		} catch (Exception e) {
			// Log.w(MyApplication.TAG, "FileNotFoundException");
			// StringWriter sw = new StringWriter();
			// e.printStackTrace(new PrintWriter(sw, true));
			// System.out.println("file全路径="+file);
			// System.out.println("报错:" + sw.toString());
		} catch (Error e) {
			// System.out.println("SD卡已满");
			// if(HomeActivity.getInstance()!=null){
			// Toast.makeText(HomeActivity.getInstance(), "SD卡已满没法保存图片和音乐",
			// Toast.LENGTH_LONG).show();
			// }
		}
		return false;
	}



	public static String convertUrlToFileName(String url) {
		String filename = url;
		filename = filename.replace("http://10.18.96.185:8080/", "");
		filename = filename.replace("http://14.31.15.41:18080/", "");
		filename = filename.replace("http://113.108.186.158/", "");
		filename = filename.replace("http://14.31.15.39/", "");
		filename = filename.replace("http://tp3.sinaimg.cn/", "");
		filename = filename.replace("/", "_");

		return filename;
	}

	private final static String IMUSIC_DIR = "Android/data/cn.eshore.eshool";

	// 根据文件名组成全路径前缀
	public static String getDirectory(String filename) {
		// String extStorageDirectory =
		// Environment.getExternalStorageDirectory()
		// .toString();
		String extStorageDirectory = "/mnt/sdcard";
		// String dirPath = extStorageDirectory + "/" + IMUSIC_DIR + "/photo";
		// File dirFile = new File(dirPath);
		// dirFile.mkdirs();
		// dirPath = dirPath +"/"+filename;
		// dirFile = new File(dirPath);
		// dirFile.mkdir();

		String dirPath = extStorageDirectory + "/" + IMUSIC_DIR;
		File dirFile = new File(dirPath);
		dirFile.mkdirs();
		dirPath = dirPath + "/image_cache";
		dirFile = new File(dirPath);
		dirFile.mkdir();
		return dirPath;
	}




	public static final int UNCONSTRAINED = -1;


	// 复制图片到目录保存
	public static int copyPhoto(String url) {
		String fileName = ImageUtilsEx.convertUrlToFileName(url);
		String dir = ImageUtilsEx.getDirectory(fileName);
		String pathFileName = dir + "/" + fileName;
		File file = new File(pathFileName);
		System.out.println("fileName=" + fileName + " pathFileName="
				+ pathFileName);
		if (file.exists()) {
			// String extStorageDirectory =
			// Environment.getExternalStorageDirectory()
			// .toString();
			String dirPath = "/mnt/sdcard" + "/" + "家校微博";
			File dirFile = new File(dirPath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			File newFile = new File(dirFile + "/" + fileName);
			System.out.println("newFile=" + newFile.getAbsolutePath());
			if (!newFile.exists()) {
				InputStream is;
				FileOutputStream fos;
				try {
					is = new FileInputStream(file);
					fos = new FileOutputStream(newFile);
					// 下面这一段代码不是很理解，有待研究
					byte[] buffer = new byte[8192];
					int count = 0;
					// 开始复制db文件
					while ((count = is.read(buffer)) > 0) {
						fos.write(buffer, 0, count);
					}
					fos.close();
					is.close();
					return IMG_DOWN_SUCCESS;
				} catch (Exception e) {
					e.printStackTrace();
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw, true));
					System.out.println("copyPhoto报错:" + sw.toString());
				}
			} else {
				System.out.println("newFile存在");
				return IMG_DOWN_SUCCESS;
			}
		} else {
			System.out.println("本地相片不存在");
			return IMG_DOWNLOADING;
		}
		return IMG_DOWN_FAIL;
	}

	/**
	 * 获得图像
	 * 
	 * @param path
	 * @param options
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Bitmap getBitmapByPath(String path, Options options,
                                         int width, int height) throws FileNotFoundException {
		File file = new File(path);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		FileInputStream in = null;
		in = new FileInputStream(file);
		if (options != null) {
			// Rect r = getScreenRegion(screenWidth, screenHeight);
			// int w = r.width();
			// int h = r.height();
			// int maxSize = w > h ? w : h;
			// int inSimpleSize = computeSampleSize(options, maxSize, w * h);
			// 计算图片缩放比例
			final int minSideLength = Math.min(width, height);
			int inSimpleSize = computeSampleSize(options, minSideLength, width
					* height);

			// System.out.println("Path inSimpleSize="+inSimpleSize);
			options.inSampleSize = inSimpleSize; // 设置缩放比例
			options.inPurgeable = true;
			options.inPreferredConfig = Config.RGB_565;
			options.inInputShareable = true;
			options.inJustDecodeBounds = false;
		}
		Bitmap b = null;
		try {
			b = BitmapFactory.decodeStream(in, null, options);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			System.out.println("Exception:" + sw.toString());

		} catch (Error e2) {
			// e2.printStackTrace();
			StringWriter sw2 = new StringWriter();
			e2.printStackTrace(new PrintWriter(sw2, true));
			System.out.println("Error:" + sw2.toString());
		}

		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}

	private static Rect getScreenRegion(int width, int height) {
		return new Rect(0, 0, width, height);
	}

	/**
	 * 获取需要进行缩放的比例，即options.inSampleSize
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(Options options,
                                        int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(Options options,
                                                int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 : (int) Math
				.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == UNCONSTRAINED) ? 128 : (int) Math
				.min(Math.floor(w / minSideLength),
						Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == UNCONSTRAINED)
				&& (minSideLength == UNCONSTRAINED)) {
			return 1;
		} else if (minSideLength == UNCONSTRAINED) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * @param url
	 *            图片的url
	 *            ，显示的像素大小
	 * @return 返回指定RUL的缩略图
	 * 
	 * @author jevan 2012-7-3
	 * 
	 */
	public static Bitmap loadImageFromUrl(String url, int width, int height) {
		URL m;
		InputStream i = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream out = null;
		byte[] isBuffer = new byte[1024];
		if (url == null)
			return null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
			bis = new BufferedInputStream(i, 1024 * 4);
			out = new ByteArrayOutputStream();
			int len = 0;
			while ((len = bis.read(isBuffer)) != -1) {
				out.write(isBuffer, 0, len);
			}
			out.close();
			bis.close();
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		if (out == null)
			return null;
		byte[] data = out.toByteArray();
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		int outWidth = options.outWidth; // 获得图片的实际高和宽
		int outHeight = options.outHeight;
		options.inPreferredConfig = Config.RGB_565;
		// 设置加载图片的颜色数为16bit，默认是RGB_8888，表示24bit颜色和透明通道，但一般用不上
		options.inSampleSize = 1;
		// 设置缩放比,1表示原比例，2表示原来的四分之一....
		// 计算缩放比
		if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
			int sampleSize = (outWidth / width + outHeight / height) / 2;
			options.inSampleSize = sampleSize;
			// System.out.println("使用缩放比例inSampleSize="+sampleSize+" outWidth="+outWidth+" outHeight="+outHeight);
			// LogUtil.Log("setBitmapOption",
			// "使用缩放比例inSampleSize="+sampleSize+" outWidth="+outWidth+" outHeight="+outHeight);
		}
		options.inJustDecodeBounds = false;
		Bitmap bmp = null;
		try {
			bmp = BitmapFactory.decodeByteArray(data, 0, data.length, options); // 返回缩略图
		} catch (OutOfMemoryError e) {
			System.gc();
			bmp = null;
		}
		return bmp;
	}

	// 拍照相关
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	private static boolean Camera = false;

	// 拍照
	public void onCaptureClick(Activity activity, View view) {
		if (!Camera) {
			// Camera = true;//可以多次拍
			// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// startActivityForResult(intent, REQUEST_CODE_CAPTURE);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
					Environment.getExternalStorageDirectory(), "temp.jpg")));
			activity.startActivityForResult(intent, PHOTOHRAPH);
		}
	}


	/**
	 * 写图片文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
	 * 
	 * @throws IOException
	 */
	public static void saveImage(String fileName, Bitmap bitmap)
			throws IOException {
		saveImage(fileName, bitmap, 100);
	}

	public static void saveImage(String fileName,
                                 Bitmap bitmap, int quality) throws IOException {
		if (bitmap == null)
			return;


		FileOutputStream fos = new FileOutputStream(fileName);
		bitmap.compress(CompressFormat.JPEG, 100, fos);
		try {
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	/**
	 * 获取图片的旋转角度
	 * 
	 * @param file
	 *            图片文件
	 */
	public static int getImageDegree(final File file) {
		if (null != file) {
			return getImageDegree(file.getAbsolutePath());
		}
		return 0;
	}

	/**
	 * 获取图片的旋转角度
	 * 
	 * @param path
	 *            图片的绝对地址
	 */
	public static int getImageDegree(final String path) {
		if (null == path) {
			return 0;
		}
		final File file = new File(path);
		if (!file.exists()) {
			return 0;
		}
		final int degree;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			default:
				degree = 0;
			}
			return degree;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取缩小后的位图
	 * 
	 * @param file
	 *            位图文件
	 * @param isWidth
	 *            是否以位图的宽度为缩放边
	 * @param length
	 *            想要的边的长度
	 */
	public static Bitmap getBitmap(File file, boolean isWidth, int length) {
		if (!file.exists()) {
			return null;
		}
		if (0 == length) {
			return null;
		}
		Bitmap bm;
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
		opts.inJustDecodeBounds = false;
		final int length_tmp = isWidth ? opts.outWidth : opts.outHeight;
		final int be;
		if (length_tmp > length) {
			be = (int) Math.ceil((float) length_tmp / length);
		} else {
			be = 1;
		}
		opts.inSampleSize = be;// 设置缩放比例
		bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
		return bm;
	}

	/**
	 * 获取缩小后的位图
	 * 
	 * @param path
	 *            位图文件的绝对路径
	 * @param isWidth
	 *            是否以位图的宽度为缩放边
	 * @param length
	 *            想要的边的长度
	 */
	public static Bitmap getBitmap(String path, boolean isWidth, int length) {
		if (null == path) {
			return null;
		} else {
			final File file = new File(path);
			if (!file.exists()) {
				return null;
			}
			if (0 == length) {
				return null;
			}
		}
		Bitmap bm;
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bm = BitmapFactory.decodeFile(path, opts);
		opts.inJustDecodeBounds = false;
		final int length_tmp = isWidth ? opts.outWidth : opts.outHeight;
		final int be;
		if (length_tmp > length) {
			be = (int) Math.ceil((float) length_tmp / length);
		} else {
			be = 1;
		}
		opts.inSampleSize = be;// 设置缩放比例
		bm = BitmapFactory.decodeFile(path, opts);
		return bm;
	}

	/***
	 * 获取缩小后的位图
	 * 
	 * @param file
	 *            位图文件
	 * @param length
	 *            想要的最长边的长度
	 */
	public static Bitmap getBitmap(File file, int length) {
		if (!file.exists()) {
			return null;
		}
		if (0 == length) {
			return null;
		}
		Bitmap bm;
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
		opts.inJustDecodeBounds = false;
		final boolean isWidth = opts.outWidth > opts.outHeight;
		final int length_tmp = isWidth ? opts.outWidth : opts.outHeight;
		final int be;
		if (length_tmp > length) {
			be = (int) Math.ceil((float) length_tmp / length);
		} else {
			be = 1;
		}
		opts.inSampleSize = be;// 设置缩放比例
		bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
		return bm;
	}

	/***
	 * 获取缩小后的位图
	 * 
	 * @param path
	 *            位图文件的绝对路径
	 * @param length
	 *            想要的最长边的长度
	 */
	public static Bitmap getBitmap(String path, int length) {
		if (null == path) {
			return null;
		} else {
			final File file = new File(path);
			if (!file.exists()) {
				return null;
			}
			if (0 == length) {
				return null;
			}
		}
		Bitmap bm;
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bm = BitmapFactory.decodeFile(path, opts);
		opts.inJustDecodeBounds = false;
		final boolean isWidth = opts.outWidth > opts.outHeight;
		final int length_tmp = isWidth ? opts.outWidth : opts.outHeight;
		final int be;
		if (length_tmp > length) {
			be = (int) Math.ceil((float) length_tmp / length);
		} else {
			be = 1;
		}
		opts.inSampleSize = be;// 设置缩放比例
		bm = BitmapFactory.decodeFile(path, opts);
		return bm;
	}

	/**
	 * 压缩图片
	 * 
	 * @param bitmap
	 *            待压缩的图片
	 * @param file
	 *            压缩图片保存文件
	 * @param max
	 *            压缩图片的极限大小
	 */
	public static void compressBitmap(Bitmap bitmap, File file, int max) {
		if (null == bitmap || max <= 0 || null == file || !file.exists()) {
			return;
		}
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int length, compress_percentage = 105;
			do {
				baos.reset();
				compress_percentage -= 5;
				bitmap.compress(CompressFormat.JPEG,
						compress_percentage, baos);
				length = baos.toByteArray().length >> 10;
			} while (length > max);
			baos.close();
			FileOutputStream outStream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, compress_percentage, outStream);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 压缩图片
	 * 
	 * @param file
	 *            图片所在地址，压缩后直接覆盖
	 * @param max
	 *            压缩图片的极限大小
	 */
	public static void compressBitmap(File file, int max) {
		if (max <= 0 || null == file || !file.exists()) {
			return;
		}
		final Bitmap bm = getBitmap(file, max << 1);
		if (null == bm) {
			return;
		}
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int length, compress_percentage = 105;
			do {
				baos.reset();
				compress_percentage -= 5;
				bm.compress(CompressFormat.JPEG, compress_percentage,
						baos);
				length = baos.toByteArray().length >> 10;
			} while (length > max);
			baos.close();
			FileOutputStream outStream = new FileOutputStream(file);
			bm.compress(CompressFormat.JPEG, compress_percentage, outStream);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**根据名称获取资源id**/
	public static int getRidByPicName(Context context, String picName) {
		
		picName = picName.substring(1, picName.indexOf(".gif"));
		
		Resources resources = context.getResources();
		int indentify = resources.getIdentifier(context.getPackageName() + ":drawable/" + "a_" + picName, null, null);

		if (indentify > 0) {
			return indentify;
		}
		return -1;
	}
	
	
	
	public static Bitmap readBitmapAutoSize(String filePath, int outWidth,
                                            int outHeight) {
		// outWidth和outHeight是目标图片的最大宽度和高度，用作限制
		FileInputStream fs = null;
		BufferedInputStream bs = null;
		try {
			fs = new FileInputStream(filePath);
			bs = new BufferedInputStream(fs);
			Options options = setBitmapOption(filePath, outWidth,
					outHeight);
			return BitmapFactory.decodeStream(bs, null, options);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bs.close();
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static Options setBitmapOption(String file,
                                           int width, int height) {
		Options opt = new Options();
		opt.inJustDecodeBounds = true;
		opt.inSampleSize = 2;
		// 设置只是解码图片的边距，此操作目的是度量图片的实际宽度和高度
		BitmapFactory.decodeFile(file, opt);

		int outWidth = opt.outWidth; // 获得图片的实际高和宽
		int outHeight = opt.outHeight;
		opt.inDither = false;
		opt.inPreferredConfig = Config.RGB_565;
		// 设置加载图片的颜色数为16bit，默认是RGB_8888，表示24bit颜色和透明通道，但一般用不上
		opt.inSampleSize = 1;
		// 设置缩放比,1表示原比例，2表示原来的四分之一....
		// 计算缩放比
		if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
			int sampleSize = outWidth / width + outHeight / height;
			opt.inSampleSize = sampleSize;
		}
		Log.d("ImageUtils", "opt.inSampleSize:" + opt.inSampleSize);
		

		opt.inJustDecodeBounds = false;// 最后把标志复原
		return opt;
	}
	
	
	
	/**
	 * 根据实际需要的宽高计算压缩比例
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(Options options,
                                            int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 4;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;//原始
		}

		return inSampleSize;
	}
	
	
	
	
	
	
	/**
	  * 获取指定文件大小(字节)
	  * 
	  * @param file
	  * @return
	  * @throws Exception
	  */
	private static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
			
			if(null != fis){
				fis.close();
				fis = null;
			}
		} else {
			Log.e("获取文件大小", "文件不存在!");
		}
		return size;
	}
	 
	
	
	
	/**
	 * 压缩图片并上传
	 * @param oldPath  本地图片路径
	 * @param newPath  处理后的上传图片路径
	 * @throws Exception
	 */
	public static String compressUploadPic(String oldPath, String newPath)
			throws Exception {
		
		Bitmap bitmap = null;
		long oldFileSize = getFileSize(new File(oldPath));
		
		if(oldFileSize / 1024 <= 200){//如果文件大小小于200k，则不压缩
			copyFile(oldPath, newPath);
		}else {

			int size = Utils.dpTopx(90);
			bitmap = getSmallBitmap(oldPath, size, size);

			FileOutputStream fos = new FileOutputStream(newPath);
			
			if(null != bitmap){
				
				final int degree = ImageUtilsEx.getImageDegree(new File(oldPath));
				if (degree != 0) {//图片选中角度不为零，则旋转图片(压缩处理后旋转，不易内存溢出)
					Matrix matrix = new Matrix();
					matrix.postRotate(degree);
					
					try {
						bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
								bitmap.getHeight(), matrix, false);
					} catch (OutOfMemoryError e) {
						System.gc();
				    }
				}
				
				bitmap.compress(CompressFormat.JPEG, 40, fos);
				
			}
			if(bitmap != null && !bitmap.isRecycled()){
				bitmap.recycle();
				bitmap = null;
			}
		}
		return newPath;
			
	}
		

	private static int calculateInSampleSizeTwo(Options options,
                                                int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件绝对路径
	 * @param newPath
	 *            String 复制后绝对路径 
	 * @return void
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				if(null != fs){
					fs.close();
					fs = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

    /**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 屏幕分辨率宽度大于720，压缩比例为720，否则按屏幕比例压缩
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		if(PhoneUtils.getResolution(BaseApp.getInstance())[0]<720){
			return getSmallBitmap(filePath, PhoneUtils.getResolution(BaseApp.getInstance())[0],PhoneUtils.getResolution(BaseApp.getInstance())[1]);
		}else {
			return getSmallBitmap(filePath, 720,1280);
		}
	}

	/**
	 *根据路径获得突破并压缩返回bitmap用于显示
	 * @param filePath
	 * @param reqWidth
	 * @param reqHeight
     * @return
     */
	public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight){


        final Options options = new Options();
		options.inJustDecodeBounds = true;//让解析方法禁止为bitmap分配内存
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}
	
	public static String saveBitmapFile(Bitmap bitmap){
        File file=new File(FileUtils.createFileMp3(BaseApp.getInstance()).getAbsolutePath());//将要保存图片的路径
        try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
		return file.getAbsolutePath();
   }


    /**
     * 照片角度旋转
     * @param bitmap
     * @param fileString
     * @return
     */
   public static Bitmap rotateImage(Bitmap bitmap, String fileString){
       if(null != bitmap){

           final int degree = ImageUtilsEx.getImageDegree(new File(fileString));
           if (degree != 0) {//图片选中角度不为零，则旋转图片(压缩处理后旋转，不易内存溢出)
               Matrix matrix = new Matrix();
               matrix.postRotate(degree);

               try {
                   bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                           bitmap.getHeight(), matrix, false);
               } catch (OutOfMemoryError e) {
                   System.gc();
               }
           }

       }
       return bitmap;
   }
	
	/**转Bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap changerDrab(Drawable drawable){
	        if (drawable instanceof BitmapDrawable) {
	            return ((BitmapDrawable) drawable).getBitmap();
	        } else if (drawable instanceof NinePatchDrawable) {
	            Bitmap bitmap = Bitmap
	                    .createBitmap(  
	                            drawable.getIntrinsicWidth(),  
	                            drawable.getIntrinsicHeight(),  
	                            drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
	                                    : Config.RGB_565);
	            Canvas canvas = new Canvas(bitmap);
	            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),  
	                    drawable.getIntrinsicHeight());  
	            drawable.draw(canvas);  
	            return bitmap;  
	        } else {  
	            return null;  
	        }  
	    } 
}
