package com.avisys.allinone.pagination;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.avisys.allinone.R;
import com.avisys.allinone.pagination.adapter.UserAdapter;
import com.avisys.allinone.pagination.model.User;
import com.avisys.allinone.pagination.viewmodel.UserViewModel;

public class PaginationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final String TAG = "UserDataSource";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination);

        recyclerView = findViewById(R.id.recyclerView);

        final UserAdapter adapter = new UserAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        * As soon as the @Object of UserViewModel gets created
        * it calls the @method init() just after creation of UserViewModel
        */

        UserViewModel itemViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        Log.e(TAG,"1::"+itemViewModel);

        itemViewModel.userPagedList.observe(this, new Observer<PagedList<User>>() {
            @Override public void onChanged(PagedList<User> users) {
                Log.e(TAG,"6::");
                /**
                 * Set the new list to be displayed.
                 * <p>
                 * If a list is already being displayed, a diff will be computed on a background thread, which
                 * will dispatch Adapter.notifyItem events on the main thread.
                 *
                 * @param pagedList The new list to be displayed.
                 */
                adapter.submitList(users);
            }
        });
        recyclerView.setAdapter(adapter);
    }

}