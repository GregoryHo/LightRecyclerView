package com.ns.greg.library.fastlightrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.ns.greg.library.fastlightrecyclerview.basic.BaseAdapterHelper;
import com.ns.greg.library.fastlightrecyclerview.basic.BaseRecyclerViewAdapter;
import com.ns.greg.library.fastlightrecyclerview.basic.BaseRecyclerViewHolder;
import java.util.List;

/**
 * Created by Gregory on 2016/6/30.
 */
public abstract class BaseSingleVHAdapter<VH extends BaseRecyclerViewHolder, T>
    extends BaseRecyclerViewAdapter<T> {

  private Context context;

  public BaseSingleVHAdapter(Context context, @NonNull List<T> list) {
    this.context = context;
    addItems(list);
  }

  @Override public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return onCreateViewHolderImp(parent);
  }

  protected abstract BaseRecyclerViewHolder onCreateViewHolderImp(ViewGroup parent);

  @Override public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
    T listItem = BaseAdapterHelper.getListItem((List<T>) getCollection(), position);
    if (listItem == null) {
      throw new IllegalStateException("Incorrect ViewHolder found");
    } else {
      holder.setItemClickable();
      onBindViewHolderImp((VH) holder, position, listItem);
    }
  }

  protected abstract void onBindViewHolderImp(VH holder, int position, T listItem);

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
}
