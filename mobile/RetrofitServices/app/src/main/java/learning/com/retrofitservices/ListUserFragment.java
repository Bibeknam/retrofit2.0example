package learning.com.retrofitservices;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import learning.com.retrofitservices.apiservices.RestClient;
import learning.com.retrofitservices.apiservices.model.User;

public class ListUserFragment extends Fragment  {

    List<String> usernames = new ArrayList<>();

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    RestClient restClient = RestClient.getInstance();

    RestClient.ResultReadyCallback callback = new RestClient.ResultReadyCallback() {
        @Override
        public void resultReady(List<User> users) {
            for(User user: users) {
                usernames.add(user.username);
            }

            ((ArrayAdapter) mAdapter).notifyDataSetChanged();
        }
    };


    public static ListUserFragment newInstance() {
        ListUserFragment fragment = new ListUserFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListUserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, usernames);

        restClient.setCallback(callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        AbsListView mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // restClient.getUsers();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        restClient.getUsers();
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