package com.shiftdev.postbud.ui.parcel_overview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParcelOverviewViewModel extends ViewModel {

     private MutableLiveData<String> mText;

     public ParcelOverviewViewModel() {
          mText = new MutableLiveData<>();
          mText.setValue("This is dashboard fragment");
     }

     public LiveData<String> getText() {
          return mText;
     }
}