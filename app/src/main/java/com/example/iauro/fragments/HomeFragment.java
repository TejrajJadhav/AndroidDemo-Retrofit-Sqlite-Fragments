package com.example.iauro.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iauro.DBHelper;
import com.example.iauro.MyApplication;
import com.example.iauro.R;
import com.example.iauro.adapters.PhotosAdapter;
import com.example.iauro.model.PhotoData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ProgressDialog progressDialog;
    RecyclerView rv_photos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        rv_photos = view.findViewById(R.id.rv_photos);
        initApi();

        return view;
    }

    private void initApi() {
        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        MyApplication.getApi().getPhotos().enqueue(new Callback<List<PhotoData>>() {
            @Override
            public void onResponse(Call<List<PhotoData>> call, Response<List<PhotoData>> response) {
                progressDialog.dismiss();
                List<PhotoData> photoDataList = new ArrayList<>();
                photoDataList.addAll(response.body());
                DBHelper.getInstance(requireContext()).addPhotos(photoDataList);
                initAdapter(photoDataList);

            }

            @Override
            public void onFailure(Call<List<PhotoData>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(requireActivity(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter(List<PhotoData> photoDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        rv_photos.setLayoutManager(layoutManager);
        PhotosAdapter photosAdapter = new PhotosAdapter(requireContext(), photoDataList, true);
        rv_photos.setAdapter(photosAdapter);
    }
}
