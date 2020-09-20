package com.example.hackrice2020.ui.links;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

        ImageButton cdc = (ImageButton) root.findViewById(R.id.cdcButton);
        cdc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                goToUrl("https://www.cdc.gov/coronavirus/2019-ncov/index.html");
            }
        });

        ImageButton who = (ImageButton) root.findViewById(R.id.whoButton);
        who.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                goToUrl("https://www.who.int/emergencies/diseases/novel-coronavirus-2019");
            }
        });

        ImageButton jh = (ImageButton) root.findViewById(R.id.jhButton);
        jh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                goToUrl("https://coronavirus.jhu.edu/map.html");
            }
        });

        /**TextView text = (TextView) root.findViewById(R.id.text_links);
        text.setMovementMethod(LinkMovementMethod.getInstance());

        text = (TextView) root.findViewById(R.id.text_links1);
        text.setMovementMethod(LinkMovementMethod.getInstance());

        text = (TextView) root.findViewById(R.id.text_links2);
        text.setMovementMethod(LinkMovementMethod.getInstance());**/



        return root;
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
