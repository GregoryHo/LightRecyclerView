package com.ns.greg.library.fastlightrecyclerview.base;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.ns.greg.library.fastlightrecyclerview.utils.AdapterHelper;
import java.util.List;

/**
 * @author Gregory
 * @since 2016/6/30
 */
public abstract class SingleVHAdapter<VH extends BaseRecyclerViewHolder, T>
    extends BaseRecyclerViewAdapter<T> {

  private Context context;

  public SingleVHAdapter(Context context, @NonNull List<T> list) {
    this.context = context;
    addItems(list);
  }

  @Override public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return onCreateViewHolderImp(parent);
  }

  protected abstract BaseRecyclerViewHolder onCreateViewHolderImp(ViewGroup parent);

  @SuppressWarnings("unchecked") @Override
  public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
    T listItem = AdapterHelper.getListItem((List<T>) getCollection(), position);
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
