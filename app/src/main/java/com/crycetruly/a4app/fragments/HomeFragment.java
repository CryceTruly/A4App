package com.crycetruly.a4app.fragments;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.crycetruly.a4app.HealthUnitDetailActivity;
import com.crycetruly.a4app.R;
import com.crycetruly.a4app.models.Healthunit;
import com.crycetruly.a4app.utils.Preferences;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    TextView textView;
    private static final int NUM_COLUMNS = 2;
    private RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    ProgressBar progressBar;
    String photourl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ;

        progressBar = v.findViewById(R.id.progress_bar);

        recyclerView = v.findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        com.google.firebase.firestore.Query query = FirebaseFirestore.getInstance()
                .collection("healthunits").whereEqualTo("locality","Mbarara");

                //todo afix for location on phones probaby build a serve with no failure issues.whereEqualTo("locality", Preferences.getLocality(getContext()));


        FirestoreRecyclerOptions<Healthunit> options = new FirestoreRecyclerOptions.Builder<Healthunit>()
                .setQuery(query, Healthunit.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Healthunit, ChatHolder>(options) {
            @Override
            public void onBindViewHolder(@NonNull final ChatHolder holder, final int position, final Healthunit model) {
               final String key = getSnapshots().getSnapshot(position).getId();
               FirebaseStorage storage=FirebaseStorage.getInstance();

                // Create a storage reference from our app
                StorageReference storageRef = storage.getReference();
   storageRef.child(model.getPhoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                    photourl=String.valueOf(uri);

               try {
                   Glide.with(getContext()).load(photourl).into(holder.cpic);
               }catch (NullPointerException e){

               }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                     }
                });


                Log.d(TAG, "onBindViewHolder: " + model.toString());
                holder.setLocality(model.getLocality());
                holder.setName(model.getName());
                holder.setDescription(model.getDescription());
                holder.setOpen(model.getOpen());

                progressBar.setVisibility(View.GONE);


                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.d(TAG, "onClick: key");
                        Intent i = new Intent(getContext(), HealthUnitDetailActivity.class);
                        i.putExtra("snap", key);
                        i.putExtra("cname", model.getName());
                        i.putExtra("cpic", photourl);
                        i.putExtra("district", model.getLocality());
                        i.putExtra("cprofile", model.getDescription());
                        i.putExtra("copen", model.getOpen());
                        i.putExtra("phone", model.getPhone());
                        i.putExtra("lat", model.getLat());
                        i.putExtra("lng", model.getLng());
                        Pair[] pairs = new Pair[4];

                        pairs[0] = new Pair<View, String>(holder.cpic, "cpic");
                        pairs[1] = new Pair<View, String>(holder.cname, "cname");
                        pairs[2] = new Pair<View, String>(holder.cprofile, "cprofile");
                        pairs[3] = new Pair<View, String>(holder.copen, "copen");

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
                            startActivity(i, activityOptions.toBundle());

                        }else{
                            startActivity(i);
                        }

                    }
                });


            }

            @Override
            public ChatHolder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.custom_layoutfile, group, false);

                return new ChatHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


    public static class ChatHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView map, cname, cdistrict, copen, cprofile;
        ProgressBar progressBar2;
        ImageView cpic;

        public ChatHolder(View itemView) {
            super(itemView);
            mView = itemView;
            map = mView.findViewById(R.id.map);
            cname = mView.findViewById(R.id.name);
            cdistrict = mView.findViewById(R.id.district);
            copen = mView.findViewById(R.id.open);
            cprofile = mView.findViewById(R.id.description);
            cpic = mView.findViewById(R.id.healthunitphoto);


        }

        public void setName(String name) {
            TextView textView = mView.findViewById(R.id.name);
            textView.setText(name);
        }

        public void setLocality(String district) {
            TextView textView = mView.findViewById(R.id.district);
            textView.setText(district);
        }

        public void setDescription(String district) {
            TextView textView = mView.findViewById(R.id.description);
            textView.setText(district);
        }

        public void setOpen(String open) {
            TextView textView = mView.findViewById(R.id.open);
            textView.setText(open);
        }

        public void setPhoto(String photo, Context contex) {
            ImageView textView = mView.findViewById(R.id.healthunitphoto);
            Glide.with(contex).load(photo).into(textView);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}