package molinov.notes.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import molinov.notes.MainActivity;
import molinov.notes.R;

public class StartFragment extends Fragment {

    private static final int RC_SIGN_IN = 40404;
    private static final String TAG = "GoogleAuth";
    private GoogleSignInClient googleSignInClient;
    private SignInButton signInButton;
    private TextView emailView;
    private MaterialButton next, vk;

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        initGoogleSign();
        initView(view);
        enableSign();
        return view;
    }

    private void initGoogleSign() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }

    private void initView(View view) {
        signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(v -> {
            signIn();
        });
        emailView = view.findViewById(R.id.email);
        next = view.findViewById(R.id.next);
        next.setOnClickListener(v -> {
            CardFragment cardFragment = CardFragment.newInstance();
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.nav_host_fragment, cardFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        });
        vk = view.findViewById(R.id.vk);
        vk.setOnClickListener(v -> {
            vkSign();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if (account != null) {
            disableSign();
            updateUI(account.getEmail());
        }
    }

    private void vkSign() {
        VK.login(requireActivity(), new ArrayList<>(Arrays.asList(VKScope.WALL, VKScope.PHOTOS)));
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            disableSign();
            updateUI(account.getEmail());
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code =" + e.getStatusCode());
        }
    }

    private void updateUI(String email) {
        emailView.setText(email);
    }

    private void enableSign() {
        signInButton.setEnabled(true);
        vk.setEnabled(true);
        next.setEnabled(false);
    }

    private void disableSign() {
        signInButton.setEnabled(false);
        vk.setEnabled(false);
        next.setEnabled(true);
    }
}
