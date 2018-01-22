package com.ns.greg.library.fastlightrecyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Gregory
 * @since 2017/6/19
 */

public class EasyDecoration extends RecyclerView.ItemDecoration {

  private final Rect rect;
  private final int orientation;
  private final ColorDrawable dividerDrawable;
  private final int dividerSize;

  private EasyDecoration(Rect rect, int orientation, ColorDrawable dividerDrawable,
      int dividerSize) {
    this.rect = rect;
    this.orientation = orientation;
    this.dividerDrawable = dividerDrawable;
    this.dividerSize = dividerSize;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    outRect.set(rect);
  }

  @Override public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    switch (orientation) {
      default:
        // nothing to do
        break;

      case LinearLayoutManager.VERTICAL:
        drawHorizontalLine(c, parent);
        break;

      case LinearLayoutManager.HORIZONTAL:
        drawVerticalLine(c, parent);
        break;
    }
  }

  private void drawHorizontalLine(Canvas c, RecyclerView parent) {
    int left = rect.left + parent.getPaddingLeft();
    int top;
    int right = parent.getRight() - rect.right - parent.getPaddingRight();
    int bottom;
    int offset = (rect.bottom + dividerSize) / 2;
    // minus one because the last item no needs to draw line
    int childCount = parent.getChildCount() - 1;
    for (int i = 0; i < childCount; i++) {
      View child = parent.getChildAt(i);
      RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
      top = child.getBottom() + params.bottomMargin + offset;
      bottom = top + dividerSize;
      dividerDrawable.setBounds(left, top, right, bottom);
      dividerDrawable.draw(c);
    }
  }

  private void drawVerticalLine(Canvas c, RecyclerView parent) {
    int left;
    int top = rect.top + parent.getPaddingTop();
    int right;
    int bottom = parent.getBottom() - rect.bottom - parent.getPaddingBottom();
    System.out.println("bottom: " + parent.getBottom());
    int offset = (rect.right + dividerSize) / 2;
    // minus one because the last item no needs to draw line
    int childCount = parent.getChildCount() - 1;
    for (int i = 0; i < childCount; i++) {
      View child = parent.getChildAt(i);
      RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
      left = child.getRight() + params.rightMargin + offset;
      right = left + dividerSize;
      dividerDrawable.setBounds(left, top, right, bottom);
      dividerDrawable.draw(c);
    }
  }

  public static class Builder {

    private Rect rect;
    private int orientation;
    private ColorDrawable dividerDrawable;
    private int dividerSize;

    public Builder() {
      this.rect = new Rect();
      orientation = -1;
      dividerDrawable = new ColorDrawable(Color.BLACK);
      dividerSize = 4;
    }

    public Builder setMarginLeft(int left) {
      rect.left = left;
      return this;
    }

    public Builder setMarginTop(int top) {
      rect.top = top;
      return this;
    }

    public Builder setMarginRigth(int right) {
      rect.right = right;
      return this;
    }

    public Builder setMarginBottom(int bottom) {
      rect.bottom = bottom;
      return this;
    }

    public Builder setMargin(int left, int top, int right, int bottom) {
      rect.set(left, top, right, bottom);
      return this;
    }

    public Builder setMargin(Rect rect) {
      this.rect.set(rect);
      return this;
    }

    public Builder setDividerOrientation(int orientation) {
      this.orientation = orientation;
      return this;
    }

    public Builder setDividerOrientation(RecyclerView.LayoutManager layoutManager) {
      if (layoutManager instanceof LinearLayoutManager) {
        this.orientation = ((LinearLayoutManager) layoutManager).getOrientation();
      } else {
        throw new UnsupportedOperationException(
            "Set orientation but layout manager is not LinearLayoutManager");
      }

      return this;
    }

    public Builder setDividerDrawable(@ColorInt int color) {
      this.dividerDrawable.setColor(color);
      return this;
    }

    public Builder setDividerSize(int dividerSize) {
      this.dividerSize = dividerSize;
      return this;
    }

    public EasyDecoration build() {
      if (orientation == LinearLayoutManager.VERTICAL) {
        rect.bottom = rect.bottom + dividerSize;
      } else if (orientation == LinearLayoutManager.HORIZONTAL) {
        rect.right = rect.right + dividerSize;
      }

      return new EasyDecoration(rect, orientation, dividerDrawable, dividerSize);
    }
  }
}
