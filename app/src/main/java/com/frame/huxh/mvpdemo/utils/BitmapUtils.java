package com.frame.huxh.mvpdemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class BitmapUtils {

	/**
	 * drawable转bitmap
	 * 
	 * @param d
	 * @return
	 */
	public static Bitmap drawable2Bitmap(Drawable d) {
		Bitmap bitmap = null;
		try {
			BitmapDrawable bd = (BitmapDrawable) d;
			bitmap = bd.getBitmap();
		} catch (Exception e) {
			bitmap = null;
		}
		return bitmap;
	}

	/**
	 * bitmap转byte[]
	 * 
	 * @param bitmap
	 * @param format
	 * @return
	 */
	public static byte[] bitmap2Bytes(Bitmap bitmap,
			Bitmap.CompressFormat format) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(format, 100, baos);
			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * bitmap转inputStream
	 * 
	 * @param bitmap
	 * @param format
	 * @return
	 */
	public static InputStream bitmap2InputStream(Bitmap bitmap,
			Bitmap.CompressFormat format) {
		try {
			return new ByteArrayInputStream(bitmap2Bytes(bitmap, format));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 图片圆角
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap getRoundBitmap(Bitmap bitmap, int pixels) {
		if (bitmap == null) {
			return null;
		}

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xffffffff;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * recycle bitmap
	 * 
	 * @param bitmap
	 */
	public static void recycleBitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
	}

	/**
	 * 按给定的宽高缩放图片，如果宽高中有一个值为0，则按照另一个值进行等比例缩放
	 * 
	 * @param filePath
	 *            图片路径
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap createScaleBitmap(String filePath, int width,
			int height) {
		try {
			if (width <= 0 && height <= 0) {
				return BitmapFactory.decodeFile(filePath);
			}

			Options bitmapOptions = new Options();
			bitmapOptions.inJustDecodeBounds = true;// 设置这个后，再调用decode...方法
													// 只取得outHeight(图片原始高度)和outWidth(图片的原始宽度)而不加载图片即返回的bitmap为null
			BitmapFactory.decodeFile(filePath, bitmapOptions);

			if (width == 0 || height == 0) {
				if (height == 0) {
					height = width * bitmapOptions.outHeight
							/ bitmapOptions.outWidth;
				} else if (width == 0) {
					width = height * bitmapOptions.outWidth
							/ bitmapOptions.outHeight;
				}
			}

			Options options = new Options();
			int widthSample = bitmapOptions.outWidth / width;
			int heightSample = bitmapOptions.outHeight / height;
			options.inSampleSize = widthSample <= heightSample ? widthSample
					: heightSample;

			Bitmap src = BitmapFactory.decodeFile(filePath, options);
			if (src == null) {
				return null;
			}
			Bitmap dst = Bitmap.createScaledBitmap(src, width, height, true);
			if (src != dst) {
				recycleBitmap(src);
			}
			return dst;
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	/**
	 * 获取压缩图片：如果图片原宽小于指定的宽度，则使用原图，否则等比例压缩。
	 * 
	 * @param filePath
	 * @param width
	 * @return
	 */
	public static Bitmap createScaleBitmapByWidthIfNeed(String filePath,
			int width) {
		try {
			if (width <= 0) {
				return BitmapFactory.decodeFile(filePath);
			}

			Options bitmapOptions = new Options();
			bitmapOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePath, bitmapOptions);

			Options options = new Options();
			bitmapOptions.inSampleSize = bitmapOptions.outWidth / width;

			Bitmap src = BitmapFactory.decodeFile(filePath, options);
			if (src == null || bitmapOptions.outWidth <= width) {
				return src;
			} else {
				int height = width * bitmapOptions.outHeight
						/ bitmapOptions.outWidth;
				Bitmap dst = Bitmap
						.createScaledBitmap(src, width, height, true);
				if (src != dst) {
					recycleBitmap(src);
				}
				return dst;
			}
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	/**
	 * 获取压缩图片 <br>
	 * 如果图片原宽高均小于指定的宽高，则使用原图； <br>
	 * 如果图片原宽高其中一个大于指定的宽高，则按此值等比例压缩； <br>
	 * 如果图片原宽高都大于指定的宽高，则按比例大的值等比例压缩。
	 * 
	 * @param filePath
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap createScaleBitmapIfNeed(String filePath, int width,
			int height) {
		try {
			if (width <= 0 && height <= 0) {
				return BitmapFactory.decodeFile(filePath);
			}

			Options bitmapOptions = new Options();
			bitmapOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePath, bitmapOptions);

			if (bitmapOptions.outWidth <= width
					&& bitmapOptions.outHeight <= height) {
				return BitmapFactory.decodeFile(filePath);
			} else {
				if (width == 0) {
					width = height * bitmapOptions.outWidth
							/ bitmapOptions.outHeight;
				} else if (height == 0) {
					height = width * bitmapOptions.outHeight
							/ bitmapOptions.outWidth;
				} else if (bitmapOptions.outWidth * height >= bitmapOptions.outHeight
						* width) { // width sample > height sample
					height = bitmapOptions.outHeight * width
							/ bitmapOptions.outWidth;
				} else {
					width = bitmapOptions.outWidth * height
							/ bitmapOptions.outHeight;
				}

				Options options = new Options();
				options.inSampleSize = bitmapOptions.outWidth / width;
				Bitmap src = BitmapFactory.decodeFile(filePath, options);
				if (src == null) {
					return null;
				}
				Bitmap dst = Bitmap
						.createScaledBitmap(src, width, height, true);
				if (src != dst) {
					recycleBitmap(src);
				}
				return dst;
			}
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	/**
	 * 压缩图片，减少大小，并保存到临时目录中
	 * 
	 * @param filePath
	 * @return
	 */
	public static String compressBitmap(Context context, String filePath,
			int maxSize, int maxWidth, String compressPath, String directory) {
		if (!Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			return null;
		}

		try {
			Bitmap bitmap = getScaleBitmap(context, filePath, maxWidth);
			if (bitmap == null) {
				return null;
			}

			try {
				int quality = 100;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
				while (baos.toByteArray().length > maxSize && quality > 0) {
					quality -= 10;
					baos.reset();
					bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
				}
				String path = compressPath;
				if (TextUtils.isEmpty(path)) {
					if (!TextUtils.isEmpty(directory)) {
						path = directory + File.separator
								+ UUID.randomUUID().toString() + ".jpg";
					} else {
						recycleBitmap(bitmap);
						return null;
					}
				} else {
					String dir = FileUtils.getFileDir(compressPath);
					FileUtils.mkdirIfNotExist(dir);
				}
				FileUtils.saveFile(baos.toByteArray(), path);
				recycleBitmap(bitmap);
				return path;
			} catch (Exception e) {
				Log.e("BitmapUtil", "compressBitmap", e);
			}
		} catch (OutOfMemoryError e) {
			Log.e("BitmapUtils", "compressBitmap", e);
		}
		return null;
	}

	/**
	 * 拼接图片
	 * 
	 * @param bitmaps
	 *            待拼接的图片
	 * @param orientation
	 *            方向，横向或者纵向
	 * @return 拼接后的图片
	 */
	public static Bitmap mergeBitmap(List<Bitmap> bitmaps,
			MergeOrientation orientation) {
		if (bitmaps == null || bitmaps.size() == 0) {
			return null;
		}

		int resultWidth = 0;
		int resultHeight = 0;

		// 纵向
		if (orientation == MergeOrientation.MERGE_VERTICAL) {
			for (Bitmap bm : bitmaps) {
				if (bm != null) {
					resultWidth = resultWidth >= bm.getWidth() ? resultWidth
							: bm.getWidth();
					resultHeight += bm.getHeight();
				}
			}
		}
		// 横向
		else {
			for (Bitmap bm : bitmaps) {
				if (bm != null) {
					resultHeight = resultHeight >= bm.getHeight() ? resultHeight
							: bm.getHeight();
					resultWidth += bm.getWidth();
				}
			}
		}
		if (resultHeight == 0) {
			return null;
		}
		Bitmap result = Bitmap.createBitmap(resultWidth, resultHeight,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(result);

		// 下一次绘制图片的顶点
		int nextX = 0;
		int nextY = 0;
		for (Bitmap bm : bitmaps) {
			canvas.drawBitmap(bm, nextX, nextY, null);
			if (orientation == MergeOrientation.MERGE_VERTICAL) {
				nextY += bm.getHeight();
			} else {
				nextX += bm.getWidth();
			}
		}
		return result;
	}

	public enum MergeOrientation {
		MERGE_VERTICAL, MERGE_HORIZONTAL
	}

	/**
	 * 将Bitmap保存为JPEG格式文件
	 * 
	 * @param bm
	 *            Bitmap对象
	 * @param filename
	 *            文件名
	 */
	public static void saveBitmapAsJpeg(Bitmap bm, String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	 /**
	 * 根据屏幕尺寸大小缩放图片，防止OOM
	 * @param windowWidth 屏幕宽度
	 * @param windowHeight 屏幕高度
	 * @param imagePath 图片地址
	 * @return
	 */
	 public static Bitmap bitmapScaleForWindows(String imagePath, int
	 windowWidth, int windowHeight){
	 BitmapFactory.Options opts = new Options();
	 opts.inJustDecodeBounds = true;
	 BitmapFactory.decodeFile(imagePath, opts);
	 int imageWidth = opts.outWidth;
	 int imageHeight = opts.outHeight;
	 // LogUtil.log("BitmapUtil",
//	 "windowWidth："+windowWidth+"，windowHeight："+windowHeight);

	 // LogUtil.log("BitmapUtil", "图片原宽度："+imageWidth+"，图片原高度："+imageHeight);

	 double scaleX = Math.ceil((double)imageWidth/windowWidth);
	 double scaleY = Math.ceil((double)imageHeight/windowHeight);
	 // System.out.println("scaleX:"+scaleX+",scaleY:"+scaleY);

	 int scale = 1;
	 if(scaleX>=scaleY & scaleY>=1){
	 scale = (int)scaleX;
	 }
	 if(scaleY>=scaleX & scaleX>=1){
	 scale = (int)scaleY;
	 }
	 int n = 0;
	 // LogUtil.log("BitmapUtil", "原来图片缩放比例："+scale);
	 if(scale == 1){
	 scale = 1;
	 }else if(!checkPow2(scale)){

	 while(scale!=0){
	 n++;
	 scale = scale/2;
	 // System.out.println("tempscale:"+scale);
	 }
	 scale = (int)Math.pow(2, n);
	 }
	 // LogUtil.log("BitmapUtil", "最终算出图片缩放比例："+scale);

	 opts.inJustDecodeBounds = false;
	 opts.inSampleSize = scale;
	 Bitmap bitmap = BitmapFactory.decodeFile(imagePath, opts);
	 return bitmap;
	 }

	 public static boolean checkPow2(int num)
	 {
	 if (num < 0)
	 return false;

	 if (0 == (num & (num - 1)))
	 return true;
	 else
	 return false;
	 }

	public static Bitmap getScaleBitmap(Context context, String imagePath,
			int maxWidth) {
		try {
			Options bitmapOptions = new Options();
			bitmapOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(imagePath, bitmapOptions);
			int imageWidth = bitmapOptions.outWidth;

			if (maxWidth == 0) {
				maxWidth = DevUtils.getScreenWidth(context);
			} else if (maxWidth < 0) {
				maxWidth = imageWidth;
			}

			if (imageWidth < maxWidth) {
				maxWidth = imageWidth;
			}

			Bitmap bitmap = createScaleBitmapByWidthIfNeed(imagePath, maxWidth);
			return bitmap;
		} catch (OutOfMemoryError e) {
			Log.e("BitmapUtils", "compressBitmap", e);
			return null;
		}
	}

	public static void setImageBitmap(ImageView view, String fileName) {
		view.setImageBitmap(createScaleBitmapIfNeed(fileName, 0, 0));
	}

	/**
	 * 把图片格式化成正方形，且圆角化
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap toEditBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
		}
		dst_left = 0;
		dst_top = 0;
		dst_right = height;
		dst_bottom = height;
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xFF000000;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}
	 /**
	 * 需要加载的图片可能是大图，我们需要对其进行合适的缩小处理
	 *
	 */
	 public static Bitmap getScaleBitmap(String imagePath, int windowWidth,
	 int windowHeight) {
	 // Display display = this.getWindowManager().getDefaultDisplay();
	 BitmapFactory.Options ops = new BitmapFactory.Options();
	 ops.inJustDecodeBounds = true;
	 Bitmap bmp = BitmapFactory.decodeFile(imagePath, ops);
	 Log.i("BitmapUtil", "图片原宽度："+ops.outWidth+"，图片原高度："+ops.outHeight);
	 int wRatio = (int) Math.ceil(ops.outWidth / (float) windowWidth);
	 int hRatio = (int) Math.ceil(ops.outHeight / (float) windowHeight);

	 if (wRatio > 1 && hRatio > 1) {
	 if (wRatio > hRatio) {
	 ops.inSampleSize = wRatio;
	 } else {
	 ops.inSampleSize = hRatio;
	 }
	 }
		 Log.i("BitmapUtil", "最终算出图片缩放比例："+ops.inSampleSize);
	 ops.inJustDecodeBounds = false;
	 bmp = BitmapFactory.decodeFile(imagePath, ops);

	 return bmp;

	 }

}
