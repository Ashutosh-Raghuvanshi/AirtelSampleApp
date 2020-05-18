package com.example.airtelsampleapp.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.airtelsampleapp.R;
import com.example.airtelsampleapp.model.Address;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private EditText mCity;
    private EditText mAddress;
    private RecyclerView mRecyclerView;
    private AddressAdapter mAddressAdapter;
    private ArrayList<Address> addresses;
    private String query, city;
    private Disposable d2;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        mCity = rootView.findViewById(R.id.cityNameTextView);
        mAddress = rootView.findViewById(R.id.addressTextView);
        mRecyclerView = rootView.findViewById(R.id.suggestionsView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.init(getActivity().getApplicationContext());
        mViewModel.getAddressList().observe(this, new Observer<ArrayList<Address>>() {
            @Override
            public void onChanged(ArrayList<Address> addresses) {
                mAddressAdapter.setData(addresses);
                mAddressAdapter.notifyDataSetChanged();
            }
        });
        initRecyclerView();
        d2 = RxTextView.textChanges(mAddress).debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        query = charSequence.toString();
                        city = mCity.getText().toString();
                        if(query != null && !query.isEmpty() && city != null && !city.isEmpty()) {
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mViewModel.QueryAddress(charSequence.toString(), mCity.getText().toString());
                        } else {
                            mRecyclerView.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void initRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        if(mAddressAdapter == null){
            mAddressAdapter = AddressAdapter.getsInstance(getContext());
        }
        mAddressAdapter.setData(addresses);
        mRecyclerView.setAdapter(mAddressAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!d2.isDisposed())
            d2.dispose();
    }
}
