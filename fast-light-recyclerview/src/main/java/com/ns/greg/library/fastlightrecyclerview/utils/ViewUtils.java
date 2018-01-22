package com.ns.greg.library.fastlightrecyclerview.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author Gregory
 * @since 2018/1/22
 */

public class ViewUtils {

  /**
   * Returns the screen density
   * <p>
   * 120dpi = 0.75
   * 160dpi = 1 (default)
   * 240dpi = 1.5
   * 320dpi = 2
   * 400dpi = 2.5
   *
   * @param context application context
   * @return Device density
   */
  public static float getDensity(Context context) {
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    return metrics.density;
  }

  /**
   * Coverts px to dp.
   *
   * @param px pixel
   * @param context application context
   * @return dp
   */
  public static int convertPixel2Dp(float px, Context context) {
    return (int) (px / getDensity(context) + 0.5f);
  }

  /**
   * Coverts dp to px.
   *
   * @param dp dp
   * @param context application context
   * @return pixel
   */
  public static int convertDp2Pixel(float dp, Context context) {
    return (int) (dp * getDensity(context) + 0.5f);
  }
}
