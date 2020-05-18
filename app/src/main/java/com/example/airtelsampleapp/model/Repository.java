package com.example.airtelsampleapp.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Repository {
    private final String TAG = Repository.class.getSimpleName();
    private Context mContext;
    MutableLiveData<ArrayList<Address>> addressesLiveData = new MutableLiveData<>();
    ArrayList<Address> addresses = new ArrayList<>();
    public Repository(Context context){
        mContext = context;
    }

    public void getAddressList(String query, String city){
        String addressJsnStr = null;
        try{
            FetchAddressTask fetchAddressTask = new FetchAddressTask();
            addressJsnStr = fetchAddressTask.execute(query, city).get();
        } catch (InterruptedException | ExecutionException e){
            Log.d(TAG, e.getMessage());
        }

        parseJsonData(addressJsnStr);
    }

    private void parseJsonData(String addressJsonStr){
        final String OWM_DATA = "data";
        final String OWM_ADDRESS_LIST = "addressList";
        final String OWM_ID = "id";
        final String OWM_CITY = "city";
        final String OWM_ADDRESS =  "addressString";

        addresses.clear();

        try {
            JSONObject addressJson = new JSONObject(addressJsonStr);
            JSONObject data = addressJson.getJSONObject(OWM_DATA);
            JSONArray addressList = data.getJSONArray(OWM_ADDRESS_LIST);

            for(int i =0; i<addressList.length(); i++){
                JSONObject place = addressList.getJSONObject(i);
                Address addressObj = new Address();
                addressObj.setId(place.getString(OWM_ID));
                addressObj.setCity(place.getString(OWM_CITY));
                addressObj.setAddress(place.getString(OWM_ADDRESS));
                addresses.add(addressObj);
            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        addressesLiveData.postValue(addresses);
    }

    public MutableLiveData<ArrayList<Address>> getAddressesLiveData(){
        return addressesLiveData;
    }

}
