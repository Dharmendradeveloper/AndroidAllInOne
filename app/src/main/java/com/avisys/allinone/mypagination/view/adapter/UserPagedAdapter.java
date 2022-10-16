package com.avisys.allinone.mypagination.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.avisys.allinone.R;
import com.avisys.allinone.databinding.UserListAdapterBinding;
import com.avisys.allinone.mypagination.data.model.User;

public class UserPagedAdapter extends PagedListAdapter<User, UserPagedAdapter.UserViewHolder> {

//    protected UserPagedAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback) {
//        super(diffCallback);
//    }
    public UserPagedAdapter() {
        super(USER_ITEM_CALLBACK);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        UserListAdapterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.user_list_adapter,parent,false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.binding.setUser(getItem(position));/*here getItem(position) return User object*/
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        private UserListAdapterBinding binding;
        public UserViewHolder(@NonNull UserListAdapterBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
    /*
    * Work with adapter DiffUtil Callback class
    * */
    private static final DiffUtil.ItemCallback<User> USER_ITEM_CALLBACK = new DiffUtil.ItemCallback<User>(){

        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem==newItem;
        }
    };
}
