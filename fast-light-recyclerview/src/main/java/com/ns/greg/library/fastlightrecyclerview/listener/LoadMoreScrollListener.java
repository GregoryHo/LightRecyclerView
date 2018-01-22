package com.ns.greg.library.fastlightrecyclerview.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.ns.greg.library.fastlightrecyclerview.base.BaseRecyclerViewAdapter;

/**
 * @author Gregory
 * @since 2017/7/26
 */
public class LoadMoreScrollListener extends RecyclerView.OnScrollListener {

  @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);
    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    if (recyclerView.getChildCount() > 0) {
      int totalItemCount = linearLayoutManager.getItemCount();
      int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
      final BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) recyclerView.getAdapter();
      if (!recyclerView.isComputingLayout()
          && totalItemCount <= lastVisibleItem + BaseRecyclerViewAdapter.VISIBLE_ITEM_THRESHOLD) {
        if (adapter.getItemCount() >= adapter.getCollectionSize()) {
          return;
        }

        if (adapter.canLoad()) {
          final int loadingIndex = adapter.getItemCount() - 1;
          recyclerView.postDelayed(new Runnable() {
            @Override public void run() {
              adapter.notifyItemChanged(loadingIndex);
              adapter.setLoaded();
              adapter.notifyItemChanged(loadingIndex);
              int startIndex = loadingIndex + 1;
              adapter.addCurrentItemCount(BaseRecyclerViewAdapter.VALID_ITEM_COUNT);
              int endIndex = adapter.getItemCount();
              adapter.notifyItemRangeInserted(startIndex, endIndex);
            }
          }, 500);
        }
      }
    }
  }
}
