package com.avisys.allinone.RoomDatabase;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avisys.allinone.R;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {
    private List<PersonDetails> personList;

    public PersonAdapter() {
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_layout_adapter,
                parent,false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        PersonDetails person = personList.get(position);
        holder.data.setText("Name :"+person.getName()+"\n"+" "+"Age : "+person.getAge()+" \n"+" "+"Address :"+" "+person.getAddress());
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public void setPersonList(List<PersonDetails> personList) {
        this.personList = personList;
        notifyDataSetChanged();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder{
        private TextView data;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.data);
        }
    }

}
