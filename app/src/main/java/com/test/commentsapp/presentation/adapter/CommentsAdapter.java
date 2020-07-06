package com.test.commentsapp.presentation.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.test.commentsapp.R;
import com.test.commentsapp.data.model.Comment;
import com.test.commentsapp.databinding.ItemCommentBinding;
import com.test.commentsapp.toolchain.recycleradapterbase.BaseAdapter;

public class CommentsAdapter extends BaseAdapter<Comment, ItemCommentBinding> {

    public CommentsAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.item_comment;
    }

    @Override
    protected void onViewHolderCreated(@NonNull ViewHolderItem viewHolder) {

    }

    @Override
    protected void onBindData(@NonNull Comment item, int position, @NonNull ItemCommentBinding binding) {
        binding.textId.setText(item.getCommentId());
        binding.textName.setText(item.getName());
        binding.textEmail.setText(item.getEmail());
        binding.textBody.setText(item.getBody());
    }
}
