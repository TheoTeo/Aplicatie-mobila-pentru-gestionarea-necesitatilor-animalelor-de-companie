package com.ase.dam.licenta01;

import android.os.Parcel;
import android.os.Parcelable;

public class Fediaf2021 implements Parcelable {
    private String tipAnimal;
    private int nrAnimaleEuropa;

    public Fediaf2021(int nrAnimaleEuropa,String tipAnimal) {
        this.tipAnimal = tipAnimal;
        this.nrAnimaleEuropa = nrAnimaleEuropa;
    }

    protected Fediaf2021(Parcel in) {
        tipAnimal = in.readString();
        nrAnimaleEuropa = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tipAnimal);
        dest.writeInt(nrAnimaleEuropa);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Fediaf2021> CREATOR = new Creator<Fediaf2021>() {
        @Override
        public Fediaf2021 createFromParcel(Parcel in) {
            return new Fediaf2021(in);
        }

        @Override
        public Fediaf2021[] newArray(int size) {
            return new Fediaf2021[size];
        }
    };

    public String getTipAnimal() {
        return tipAnimal;
    }

    public void setTipAnimal(String tipAnimal) {
        this.tipAnimal = tipAnimal;
    }

    public int getNrAnimaleEuropa() {
        return nrAnimaleEuropa;
    }

    public void setNrAnimaleEuropa(int nrAnimaleEuropa) {
        this.nrAnimaleEuropa = nrAnimaleEuropa;
    }

    @Override
    public String toString() {
        return "Fediaf2021{" +
                "tipAnimal='" + tipAnimal + '\'' +
                ", nrAnimaleEuropa=" + nrAnimaleEuropa +
                '}';
    }
}
