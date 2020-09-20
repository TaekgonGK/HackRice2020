package com.example.hackrice2020.ui.links;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackrice2020.R;

public class LinksFragment extends Fragment{

    private LinksViewModel linksViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        linksViewModel =
                ViewModelProviders.of(this).get(LinksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_links, container, false);
        linksViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        TextView text = (TextView) root.findViewById(R.id.text_links);
        text.setMovementMethod(LinkMovementMethod.getInstance());

        text = (TextView) root.findViewById(R.id.text_links1);
        text.setMovementMethod(LinkMovementMethod.getInstance());

        text = (TextView) root.findViewById(R.id.text_links2);
        text.setMovementMethod(LinkMovementMethod.getInstance());



        return root;
    }

}
