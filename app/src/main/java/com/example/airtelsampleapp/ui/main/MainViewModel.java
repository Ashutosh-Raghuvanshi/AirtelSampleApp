package com.example.airtelsampleapp.ui.main;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.airtelsampleapp.model.Address;
import com.example.airtelsampleapp.model.Repository;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    MutableLiveData<ArrayList<Address>> addressList;
    Repository mRepository;

    public void init(Context context){
        mRepository = new Repository(context);
        addressList = mRepository.getAddressesLiveData();
    }

    public MutableLiveData<ArrayList<Address>> getAddressList(){
        return addressList;
    }

    public void QueryAddress(String query, String city){
        mRepository.getAddressList(query, city);
    }
}
