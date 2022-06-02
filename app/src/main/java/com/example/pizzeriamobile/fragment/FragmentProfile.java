package com.example.pizzeriamobile.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.userdata.UserHandler;
import com.example.pizzeriamobile.logic.userdata.person.User;
import com.example.pizzeriamobile.logic.userdata.person.UserSingleton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {

    public FragmentProfile() {
        // Required empty public constructor
    }

    public static FragmentProfile newInstance() {
        FragmentProfile fragment = new FragmentProfile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
    }

    private void initialize(View view){
        loadData(view);
    }

    private void loadData(View view){
        User user = UserHandler.handler.getUser();

        ImageView imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewFragmentProfileAvatar);
        TextView textViewId = (TextView) view.findViewById(R.id.textViewFragmentProfileIdValue);
        TextView textViewFirstName = (TextView) view.findViewById(R.id.textViewFragmentProfileFirstNameValue);
        TextView textViewMiddleName = (TextView)view.findViewById(R.id.textViewFragmentProfileMiddleNameValue);
        TextView textViewLastName = (TextView)view.findViewById(R.id.textViewFragmentProfileLastNameValue);

        imageViewAvatar.setImageBitmap(user.getAvatar());
        textViewId.setText(user.getId() + "");
        textViewFirstName.setText(user.getFirstName());
        textViewMiddleName.setText(user.getMiddleName());
        textViewLastName.setText(user.getLastName());
    }

}