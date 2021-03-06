package com.ns.greg.library.fastlightrecyclerview.listener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Gregory
 * @since 2016/6/28
 */
public abstract class BaseHideScrollListener extends RecyclerView.OnScrollListener {

  private static final int SCROLL_THRESHOLD = 50;

  private int scrolledDistance = 0;
  private boolean isHeaderVisible = false;

  @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

  }

  @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    if (scrolledDistance > SCROLL_THRESHOLD && isHeaderVisible) {
      hideHeaderViewAnimateImp(recyclerView);
    } else if (scrolledDistance < -SCROLL_THRESHOLD && !isHeaderVisible) {
      showHeaderViewAnimateImp(recyclerView);
    }

    if ((isHeaderVisible && dy > 0) || (!isHeaderVisible && dy < 0)) {
      scrolledDistance += dy;
    }
  }

  public abstract void hideHeaderViewAnimateImp(RecyclerView recyclerView);

  public abstract void showHeaderViewAnimateImp(RecyclerView recyclerView);

  public abstract void hideHeaderViewImp(RecyclerView recyclerView);

  public abstract void showHeaderViewImp(RecyclerView recyclerView);

  public void setHeaderVisible(boolean visible) {
    this.isHeaderVisible = visible;
    this.scrolledDistance = 0;
  }

  public boolean isHeaderVisible() {
    return isHeaderVisible;
  }
}
