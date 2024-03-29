package vn.edu.huflit.npl_19dh110839;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogOutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogOutFragment extends DialogFragment {
    Button logOut;
    FirebaseAuth firebaseAuth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogOutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogOutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogOutFragment newInstance(String param1, String param2) {
        LogOutFragment fragment = new LogOutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_out, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        logOut=view.findViewById(R.id.btn_log_out);
        logOut.setOnClickListener(v -> {
            if(firebaseAuth.getCurrentUser()!=null){
                firebaseAuth.signOut();
                getActivity().finish();
                Intent intent=new Intent(getActivity(),SignInActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().setCancelable(true);
        super.onResume();
    }
}