package com.ns.greg.library.fastlightrecyclerview.base;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.ns.greg.library.fastlightrecyclerview.utils.AdapterHelper;
import java.util.List;

/**
 * @author Gregory
 * @since 2016/7/12
 */
public abstract class HeaderFooterVHAdapter<HVH extends BaseRecyclerViewHolder, FVH extends BaseRecyclerViewHolder, BVH extends BaseRecyclerViewHolder, T>
    extends BaseRecyclerViewAdapter<T> {

  public static final int HEADER_VIEW = 1;
  public static final int BODY_VIEW = 2;
  public static final int FOOTER_VIEW = 3;

  private final boolean hasHeader;
  private final boolean hasFooter;
  private Context context;

  public HeaderFooterVHAdapter(boolean hasHeader, boolean hasFooter, Context context,
      @NonNull List<T> list) {
    this.hasHeader = hasHeader;
    this.hasFooter = hasFooter;
    this.context = context;
    addItems(list);
  }

  @Override public int getItemViewType(int position) {
    if (hasHeader && position == 0) {
      return HEADER_VIEW;
    } else if (hasFooter && position == getCollectionSize() - 1) {
      return FOOTER_VIEW;
    } else {
      return BODY_VIEW;
    }
  }

  @Override public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == HEADER_VIEW) {
      return onCreateHeaderViewHolderImp(parent);
    } else if (viewType == FOOTER_VIEW) {
      return onCreateFooterViewHolderImp(parent);
    } else {
      return onCreateBodyViewHolderImp(parent);
    }
  }

  protected abstract BaseRecyclerViewHolder onCreateHeaderViewHolderImp(ViewGroup parent);

  protected abstract BaseRecyclerViewHolder onCreateBodyViewHolderImp(ViewGroup parent);

  protected abstract BaseRecyclerViewHolder onCreateFooterViewHolderImp(ViewGroup parent);

  @SuppressWarnings("unchecked") @Override
  public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
    T listItem = AdapterHelper.getListItem((List<T>) getCollection(), position);
    if (listItem == null) {
      //throw new IllegalStateException("Incorrect ViewHolder found");
    } else {
      if (holder.getItemViewType() == HEADER_VIEW) {
        onBindHeaderViewHolderImp((HVH) holder, position, listItem);
      } else if (holder.getItemViewType() == FOOTER_VIEW) {
        onBindFooterViewHolderImp((FVH) holder, position, listItem);
      } else {
        onBindBodyViewHolderImp((BVH) holder, position, listItem);
      }
    }
  }

  protected abstract void onBindHeaderViewHolderImp(HVH holder, int position, T listItem);

  protected abstract void onBindBodyViewHolderImp(BVH holder, int position, T listItem);

  protected abstract void onBindFooterViewHolderImp(FVH holder, int position, T listItem);

  public void updateListWithNotify(@NonNull List<T> list) {
    clearItems();
    postNotify();
    addItems(list);
    postNotify();
  }

  public void updateListWithoutNotify(@NonNull List<T> list) {
    clearItems();
    postNotify();
    addItems(list);
  }

  public Context getContext() {
    return context;
  }

  public boolean hasHeader() {
    return hasHeader;
  }

  public boolean hasFooter() {
    return hasFooter;
  }
}
