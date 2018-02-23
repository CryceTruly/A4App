package com.crycetruly.a4app.fragments;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crycetruly.a4app.AddPhoneActivity;
import com.crycetruly.a4app.CounsellorDetailActivity;
import com.crycetruly.a4app.R;
import com.crycetruly.a4app.StartActivity;
import com.crycetruly.a4app.models.Councillor;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CouncillorFragment extends Fragment {
    private RecyclerView recyclerview;
    private ProgressBar progressBar;
    private Context mContext = getContext();
    private FirestoreRecyclerAdapter adapter;
    private static final String TAG = "CouncillorFragment";
    private StorageReference reference;
    private String photourl;

    public CouncillorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_councillor, container, false);
        progressBar = v.findViewById(R.id.progress_bar);

if (FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()==null){

}else{
    Intent i=new Intent(getContext(), AddPhoneActivity.class);
    Log.d(TAG, "onCreateView: userno is null");
    i.putExtra("from","councillors");
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(i);
    Log.d(TAG, "onCreateView: ");
}
        init(v);

        Query query = FirebaseFirestore.getInstance()
                .collection("counsellors");

        FirestoreRecyclerOptions<Councillor> options = new FirestoreRecyclerOptions.Builder<Councillor>()
                .setQuery(query, Councillor.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Councillor, CouncillorViewHollder>(options) {
            @Override
            public void onBindViewHolder(@NonNull final CouncillorViewHollder holder, final int position, final Councillor model) {
                FirebaseStorage storage=FirebaseStorage.getInstance();

                // Create a storage reference from our app
                StorageReference storageRef = storage.getReference();
                storageRef.child(model.getPhoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        photourl= String.valueOf(uri);

                        try {
                            Glide.with(getContext()).load(photourl).into(holder.image);
                        }catch (NullPointerException e){

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });

                holder.setGender(model.getGender());
                holder.setName(model.getName());
                holder.setProfileDesc(model.getProfile());

                holder.setAwayAvailable(model.getStatus());
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onBindViewHolder: onbindviewholder called");


                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String key = getSnapshots().getSnapshot(position).getId();
                        Log.d(TAG, "onClick: key");
                        Intent i = new Intent(getContext(), CounsellorDetailActivity.class);
                        i.putExtra("snap", key);
                        i.putExtra("cname", model.getName());
                        i.putExtra("cpic", photourl);
                        i.putExtra("cprofile", model.getProfile());
                        Pair[] pairs = new Pair[3];

                        pairs[0] = new Pair<View, String>(holder.image, "cpic");
                        pairs[1] = new Pair<View, String>(holder.name, "cname");
                        pairs[2] = new Pair<View, String>(holder.desc, "cprofile");

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
                            startActivity(i, activityOptions.toBundle());

                        }
                        startActivity(i);
                    }
                });
            }

            @Override
            public CouncillorViewHollder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.single_councillor, group, false);

                return new CouncillorViewHollder(view);
            }
        };

        recyclerview.setAdapter(adapter
        );
        return v;
    }

    @Override
    public void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();
    }

    private void init(View v) {
        progressBar = v.findViewById(R.id.progress_bar);
        recyclerview = v.findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
    }


    public static class CouncillorViewHollder extends RecyclerView.ViewHolder {
        View mView;
        CircleImageView image;
        TextView name, desc;

        public CouncillorViewHollder(View itemView) {
            super(itemView);
            mView = itemView;
            image = mView.findViewById(R.id.cpic);
            name = mView.findViewById(R.id.cname);
            desc = mView.findViewById(R.id.cprofile);
        }


        public void setPhoto(String photo, Context context) {

            CircleImageView imageView = mView.findViewById(R.id.cpic);
            try {
                Glide.with(context).load(photo).into(imageView);
            } catch (NullPointerException e) {

            }
        }

        public void setName(String name) {
            TextView textView = mView.findViewById(R.id.cname);
            textView.setText(name.trim());
        }

        public void setGender(String name) {
            TextView genderview = mView.findViewById(R.id.gender);
            genderview.setText(name);
            genderview.setTypeface(genderview.getTypeface(), Typeface.BOLD);
        }

        public void setProfileDesc(String name) {
            TextView prof = mView.findViewById(R.id.cprofile);
            prof.setText(name.trim());
        }

        public void setAwayAvailable(String time) {
            TextView away = mView.findViewById(R.id.away);
            if (time.equals("online")) {
                away.setText("Available");
            } else {
                away.setText("Away");
            }
        }
    }
}

