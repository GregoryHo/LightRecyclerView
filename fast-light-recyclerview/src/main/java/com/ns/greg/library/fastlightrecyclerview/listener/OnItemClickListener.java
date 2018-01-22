package com.ns.greg.library.fastlightrecyclerview.listener;

/**
 * @author Gregory
 * @since 2018/1/18
 */

public interface OnItemClickListener {

  void onRootViewClick(int position);

  void onSpecificViewClick(int id, int position);
}