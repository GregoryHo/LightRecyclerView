package com.ns.greg.library.fastlightrecyclerview.basic;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Gregory on 2016/6/30.
 */
public abstract class BaseRecyclerViewAdapter<T>
    extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

  public static final int VISIBLE_ITEM_THRESHOLD = 15;

  public static final int VALID_ITEM_COUNT = 30;

  public interface AdapterLoadMoreListener {

    void onLoadMore();
  }

  private AdapterLoadMoreListener loadMoreListener;
  private int visibleItemThreshold = VISIBLE_ITEM_THRESHOLD;
  private int validItemCount = VALID_ITEM_COUNT;
  private boolean isLoading;
  private int currentItemCount;
  private final List<T> list = new ArrayList<>();
  private RecyclerViewHandler handler;

  public BaseRecyclerViewAdapter() {
    handler = new RecyclerViewHandler(Looper.getMainLooper());
  }

  public void postNotify() {
    handler.post(new Runnable() {
      @Override public void run() {
        notifyDataSetChanged();
      }
    });
  }

  @Override public int getItemCount() {
    if (!isAutoLoadEnable()) { // Default
      return getCollectionSize();
    } else { // Using load more listener
      if (getCollectionSize() < currentItemCount + VALID_ITEM_COUNT) {
        currentItemCount = getCollectionSize();
      } else {
        currentItemCount =
            currentItemCount == getInitItemCount() ? VALID_ITEM_COUNT : currentItemCount;
      }
    }

    return currentItemCount;
  }

  public Collection<T> getCollection() {
    return list;
  }

  public int getCollectionSize() {
    synchronized (list) {
      return list.size();
    }
  }

  public void clearItems() {
    synchronized (list) {
      list.clear();
    }
  }

  public void addItem(T item) {
    synchronized (list) {
      list.add(item);
    }
  }

  public void addItems(Collection<T> items) {
    for (T item : items) {
      addItem(item);
    }
  }

  public void removeItem(int index) {
    synchronized (list) {
      if (BaseAdapterHelper.checkIsLegalIndex(list, index)) {
        list.remove(index);
      }
    }
  }

  public void removeItem(T item) {
    synchronized (list) {
      list.remove(item);
    }
  }

  public boolean removeItemSucceeded(int index) {
    synchronized (list) {
      if (BaseAdapterHelper.checkIsLegalIndex(list, index)) {
        list.remove(index);
        return true;
      }
    }

    return false;
  }

  public T getItem(int index) {
    synchronized (list) {
      return BaseAdapterHelper.getListItem(list, index);
    }
  }

  public int getIndex(T item) {
    synchronized (list) {
      if (list.contains(item)) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
          if (list.get(i).equals(item)) {
            return i;
          }
        }
      }
    }

    return -1;
  }

  public void setItem(int index, T item) {
    synchronized (list) {
      list.set(index, item);
    }
  }

  public void setItem(T oldItem, T newItem) {
    int index = getIndex(oldItem);
    if (index > -1) {
      synchronized (list) {
        list.set(index, newItem);
      }
    }
  }

  protected abstract boolean isAutoLoadEnable();

  protected abstract int getInitItemCount();

  public AdapterLoadMoreListener getLoadMoreListener() {
    return loadMoreListener;
  }

  public void setLoadMoreListener(AdapterLoadMoreListener loadMoreListener) {
    this.loadMoreListener = loadMoreListener;
  }

  public int getVisibleItemThreshold() {
    return visibleItemThreshold;
  }

  public void setVisibleItemThreshold(int visibleItemThreshold) {
    this.visibleItemThreshold = visibleItemThreshold;
  }

  public int getValidItemCount() {
    return validItemCount;
  }

  public void setValidItemCount(int validItemCount) {
    this.validItemCount = validItemCount;
  }

  public void addCurrentItemCount(int count) {
    currentItemCount = getCollectionSize() < (currentItemCount + count) ? getCollectionSize()
        : currentItemCount + count;
  }

  public int getCurrentItemCount() {
    return currentItemCount;
  }

  public void setCurrentItemCount(int itemCount) {
    currentItemCount = itemCount;
  }

  public boolean isLoading() {
    return isLoading;
  }

  public void setLoading() {
    this.isLoading = true;
  }

  public void setLoaded() {
    this.isLoading = false;
  }

  private static class RecyclerViewHandler extends Handler {

    RecyclerViewHandler(Looper looper) {
      super(looper);
    }

    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
    }
  }
}
