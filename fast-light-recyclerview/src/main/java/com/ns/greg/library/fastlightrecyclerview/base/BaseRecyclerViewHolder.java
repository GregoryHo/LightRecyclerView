package com.ns.greg.library.fastlightrecyclerview.base;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ns.greg.library.fastlightrecyclerview.listener.OnItemClickListener;

/**
 * @author Gregory
 * @since 2016/4/28
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder
    implements IGetViewHelper, View.OnClickListener {

  private OnItemClickListener onItemClickListener;
  private SparseArray<View> viewSparseArray;
  private View view;

  public BaseRecyclerViewHolder(View itemView) {
    super(itemView);
    this.view = itemView;
    viewSparseArray = new SparseArray<>();
  }

  /**
   * Sets a {@link View.OnClickListener} on the entire parent view to trigger expansion.
   */
  public void setItemClickable() {
    itemView.setOnClickListener(this);
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public OnItemClickListener getOnItemClickListener() {
    return onItemClickListener;
  }

  @Override public void onClick(View v) {
    if (onItemClickListener != null) {
      if (v == itemView) {
        onItemClickListener.onRootViewClick(getAdapterPosition());
      } else {
        onItemClickListener.onSpecificViewClick(v.getId(), getAdapterPosition());
      }
    }
  }

  /**
   * Get view from resource
   */
  @SuppressWarnings("unchecked") protected <T extends View> T findViewByIdImp(int id) {
    View childView = viewSparseArray.get(id);
    if (childView == null) {
      childView = view.findViewById(id);
      viewSparseArray.put(id, childView);
    }

    return (T) childView;
  }

  @Override public View getView(int id) {
    return findViewByIdImp(id);
  }

  @Override public Button getButton(int id) {
    return findViewByIdImp(id);
  }

  @Override public ImageButton getImageButton(int id) {
    return findViewByIdImp(id);
  }

  @Override public ImageView getImageView(int id) {
    return findViewByIdImp(id);
  }

  @Override public TextView getTextView(int id) {
    return findViewByIdImp(id);
  }

  @Override public LinearLayout getLinearLayout(int id) {
    return findViewByIdImp(id);
  }

  @Override public RelativeLayout getRelativeLayout(int id) {
    return findViewByIdImp(id);
  }
}
