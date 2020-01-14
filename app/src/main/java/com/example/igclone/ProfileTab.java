package com.example.igclone;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {


    private EditText edtProfilename,edtBio,edtProfession,edtHobbies,edtSport;

    private Button btnUpdateInfo;


    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_profile_tab, container, false);

       edtProfilename = view.findViewById(R.id.edtprofilename);
       edtBio = view.findViewById(R.id.edtBio);
       edtProfession = view.findViewById(R.id.edtProfession);
       edtHobbies = view.findViewById(R.id.edtHobbies);
       edtSport = view.findViewById(R.id.edtSport);
       btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);



        final ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("ProfileName")== null){

            edtProfilename.setText("");

            if (parseUser.get("profileFavouriteSport")== null){
                edtSport.setText("");
            }else{
                edtSport.setText(parseUser.get("profileFavouriteSport").toString());
            }


            if (parseUser.get("ProfileHobbies")== null){

                edtHobbies.setText("");

            }else {
                edtHobbies.setText(parseUser.get("ProfileHobbies").toString());

            }


            if (parseUser.get("ProfileProfession")== null){
                edtProfession.setText("");
            }else {

                edtProfession.setText(parseUser.get("ProfileProfession").toString());
            }
        }else {
            edtProfilename.setText(parseUser.get("ProfileName").toString());
        }







       btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               parseUser.put("ProfileName",edtProfilename.getText().toString());
               parseUser.put("ProfileBio",edtBio.getText().toString());
               parseUser.put("ProfileProfession",edtProfession.getText().toString());
               parseUser.put("ProfileHobbies",edtHobbies.getText().toString());
               parseUser.put("profileFavouriteSport",edtSport.getText().toString());




               parseUser.saveInBackground(new SaveCallback() {
                   @Override
                   public void done(ParseException e) {

                       if (e == null){


                           Toast.makeText(getContext(),"Information Updated",Toast.LENGTH_SHORT).show();


                       }else {

                           Toast.makeText(getContext(),"ERROR OCCURRED",Toast.LENGTH_SHORT).show();
                       }





                   }
               });



           }
       });

       return view;



    }

}
