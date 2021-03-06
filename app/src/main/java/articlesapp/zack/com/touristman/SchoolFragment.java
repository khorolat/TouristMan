package articlesapp.zack.com.touristman;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.

 */
public class SchoolFragment extends Fragment {

        public static final String NAME = "Schools";
        private ListAdapter SchoolAdapter;

        EditText editTextName;
        EditText editTextAddress;
        Button buttonAdd;

        DatabaseReference databaseSchool;

        ListView listViewMall;

        ArrayList<Schools> SchoolList;


    public SchoolFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_school, container, false);
            databaseSchool = FirebaseDatabase.getInstance().getReference("school");

            editTextName = (EditText) v.findViewById(R.id.editTextName);
            editTextAddress = (EditText) v.findViewById(R.id.editTextAdress);
            buttonAdd = (Button) v.findViewById(R.id.buttonAdd);

            listViewMall = (ListView) v.findViewById(R.id.listViewSchool);

            SchoolList = new ArrayList<>();

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addMall();

                }
            });
            return v;
        }
        @Override
        public void onStart() {
            super.onStart();
            databaseSchool.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    SchoolList.clear();


                    for (DataSnapshot schoolSnapshot : dataSnapshot.getChildren()){
                        Schools school = schoolSnapshot.getValue(Schools.class);

                        SchoolList.add(school);
                    }

                    SchoolAdapter adapter = new SchoolAdapter(getActivity(), SchoolList);
                    listViewMall.setAdapter(adapter);




                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    private void addMall() {
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        if (!TextUtils.isEmpty(name)){

            databaseSchool.push().getKey();

            String id = databaseSchool.push().getKey();

            Schools school = new Schools(id, name, address);
            databaseSchool.child(id).setValue(school);

            Toast.makeText(getActivity(), "School added", Toast.LENGTH_LONG);

        }else {
            Toast.makeText(getActivity(), "You should enter a name", Toast.LENGTH_SHORT);
        }

    }

}
