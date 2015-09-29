package learning.com.retrofitservices;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import learning.com.retrofitservices.apiservices.RestClient;
import learning.com.retrofitservices.apiservices.model.User;


public class CreateUserFragement extends Fragment {
    EditText username;
    EditText name;
    Button addUser;
    RestClient restClient = RestClient.getInstance();

    public static CreateUserFragement newInstance() {
        return new CreateUserFragement();
    }

    public CreateUserFragement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_create_user, container, false);
        username = (EditText) root.findViewById(R.id.username);
        name = (EditText) root.findViewById(R.id.name);
        addUser = (Button) root.findViewById(R.id.btn_create_user);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _username = username.getText().toString();
                String _name = name.getText().toString();

                User user = new User(_username, _name);
                restClient.createUser(getActivity(), user);
                username.setText("");
                name.setText("");
            }
        });

        return root;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
