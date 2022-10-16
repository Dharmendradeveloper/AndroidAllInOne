package com.avisys.allinone.mypagination.callback;


import com.avisys.allinone.mypagination.data.model.User;

import java.util.List;

public interface NetworkCallBack {
     List<User> onSuccess(List<User> user);

}
