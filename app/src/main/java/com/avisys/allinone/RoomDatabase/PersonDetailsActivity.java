package com.avisys.allinone.RoomDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avisys.allinone.R;

public class PersonDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText uName,uAge,uAddress;
    private TextView submit;
    private String name,address,age;
    private RoomDatabaseBuilder roomDatabaseBuilder;
    private RecyclerView recyclerView;
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        init();
    }

    private void init(){
        uName = findViewById(R.id.name);
        uAge  = findViewById(R.id.age);
        uAddress = findViewById(R.id.address);
        submit = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new PersonAdapter();
        submit.setOnClickListener(this);
        /* instantiate room database*/
        roomDatabaseBuilder = RoomDatabaseBuilder.getInstance(this);
        Log.e("RoomObject",""+roomDatabaseBuilder);
        /* This line of code will be executed only when app gets launched*/
        if (!roomDatabaseBuilder.personDAO().getPersonDetails().isEmpty()){
            adapter.setPersonList(roomDatabaseBuilder.personDAO().getPersonDetails());
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onClick(View view) {
        name = uName.getText().toString();
        age  = uAge.getText().toString();
        address = uAddress.getText().toString();
        try {
            PersonDetails personDetails = new PersonDetails(name,Integer.parseInt(age),address);
            if (!name.isEmpty()&&!age.isEmpty()&&!address.isEmpty()){
                roomDatabaseBuilder.personDAO().addUserDetails(personDetails);
                adapter.setPersonList(roomDatabaseBuilder.personDAO().getPersonDetails());
                recyclerView.setAdapter(adapter);
                uName.setText("");
                uAge.setText("");
                uAddress.setText("");
            }else {
                Toast.makeText(view.getContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
            }
        }catch (NumberFormatException exception){
            Toast.makeText(view.getContext(), "NumberFormatException: "+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            System.out.println("any exception ");
        }


    }
}