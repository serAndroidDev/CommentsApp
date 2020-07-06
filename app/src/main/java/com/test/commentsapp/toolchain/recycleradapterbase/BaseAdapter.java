package com.test.commentsapp.toolchain.recycleradapterbase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class BaseAdapter<T, DB extends ViewDataBinding>
        extends RecyclerView.Adapter<BaseAdapter.ViewHolderItem> {
    private static final String TAG = BaseAdapter.class.getSimpleName();

    public interface OnClickListener<T> {
        void onItemClick(View view, @NonNull T item);
    }

    @Nullable
    protected OnClickListener<T> mOnClickListener;
    @NonNull
    protected final Context mContext;
    @NonNull
    protected final List<T> mItems = new ArrayList<>();

    protected int mItemViewHolderHeight;
    protected int mItemViewHolderWidth; // for horizontal scrolling RecyclerView

    public BaseAdapter(@NonNull Context context, @Nullable OnClickListener<T> listener) {
        mContext = context;
        mOnClickListener = listener;
    }

    public BaseAdapter(@NonNull Context context) {
        mContext = context;
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void onViewHolderCreated(@NonNull ViewHolderItem viewHolder);

    protected abstract void onBindData(@NonNull T item, int position, @NonNull DB binding);

    public void setOnClickListener(@Nullable OnClickListener<T> listener) {
        mOnClickListener = listener;
    }

    @NonNull
    public List<T> getItems() {
        return mItems;
    }

    public void setItems(@Nullable List<T> items) {
        if (items != null) {
            mItems.clear();
            mItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void setItemViewHolderHeight(int itemViewHolderHeight) {
        mItemViewHolderHeight = itemViewHolderHeight;
    }

    public void setItemViewHolderWidth(int itemViewHolderWidth) {
        mItemViewHolderWidth = itemViewHolderWidth;
    }

    public void add(@NonNull T item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addToBeginning(@NonNull T item) {
        mItems.add(0, item);
        notifyItemInserted(0);
    }

    public void addAll(@NonNull List<T> items) {
        if (!items.isEmpty()) {
            mItems.addAll(items);
            notifyItemRangeInserted(mItems.size() - items.size(), items.size());
        }
    }

    public void remove(@NonNull T item) {
        int position = mItems.indexOf(item);
        if (position > -1) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeAll(@NonNull List<T> items) {
        mItems.removeAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DB binding = DataBindingUtil
                .inflate(inflater, getLayoutRes(), parent, false);
        setItemViewHeight(binding.getRoot());
        setItemViewWidth(binding.getRoot());
        ViewHolderItem viewHolder = new ViewHolderItem(binding);
        onViewHolderCreated(viewHolder);
        return viewHolder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.ViewHolderItem viewHolder, int position) {
        viewHolder.mItem = mItems.get(position);
        onBindData((T) viewHolder.mItem, position, (DB) viewHolder.mBinding);
    }

    private void setItemViewHeight(@NonNull View itemView) {
        if (mItemViewHolderHeight > 0) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
            int calculatedHeight = mItemViewHolderHeight - params.topMargin - params.bottomMargin;
            params.height = calculatedHeight > 0 ? calculatedHeight : 0;
        }
    }

    private void setItemViewWidth(@NonNull View itemView) {
        if (mItemViewHolderWidth > 0) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
            int calculatedWidth = mItemViewHolderWidth - params.leftMargin - params.rightMargin;
            params.width = calculatedWidth > 0 ? calculatedWidth : 0;
        }
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {
        @NonNull
        public final DB mBinding;
        public T mItem;

        private ViewHolderItem(@NonNull DB binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
