package com.avisys.allinone.mypagination.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import android.os.Bundle;
import android.util.Log;

import com.avisys.allinone.R;
import com.avisys.allinone.databinding.ActivityMyPagingBinding;
import com.avisys.allinone.mypagination.data.model.User;
import com.avisys.allinone.mypagination.view.adapter.UserPagedAdapter;
import com.avisys.allinone.mypagination.viewmodel.UserViewModel;

public class MyPagingActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private UserPagedAdapter mAdapter;
    private ActivityMyPagingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_paging);
        initViewModel();
        setObserver();
    }

    private void initViewModel(){
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mAdapter = new UserPagedAdapter();
    }

    private void setObserver(){
        userViewModel.init().observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(PagedList<User> users) {
                mAdapter.submitList(users);
                Log.e("Under::",""+users.size());
            }
        });
        binding.recyclerView.setAdapter(mAdapter);
    }// method closed
}