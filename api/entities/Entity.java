package com.sahibinden.challenge.api.entities;

import android.os.Parcel;
import android.os.Parcelable;

//Android specific interface in order to use for transferring data from one activity to another

public abstract class Entity  implements Parcelable {

    protected Entity(){
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public abstract void writeToParcel(Parcel dest, int flags) ;

    protected abstract void readFromParcel(Parcel parcel);
}
