package com.ase.dam.licenta01;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Animal implements Parcelable {
    private String numeAnimal;
    private String genderAnimal;
    private String tipAnimal;
    private String rasaAnimal;
    private Date dataNasterii;

    public Animal(String numeAnimal, String genderAnimal, String tipAnimal, String rasaAnimal, Date dataNasterii) {
        this.numeAnimal = numeAnimal;
        this.genderAnimal = genderAnimal;
        this.tipAnimal = tipAnimal;
        this.rasaAnimal = rasaAnimal;
        this.dataNasterii = dataNasterii;
    }

    public Animal(String numeAnimal, String genderAnimal, String tipAnimal, String rasaAnimal) {
        this.numeAnimal = numeAnimal;
        this.genderAnimal = genderAnimal;
        this.tipAnimal = tipAnimal;
        this.rasaAnimal = rasaAnimal;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "numeAnimal='" + numeAnimal + '\'' +
                ", genderAnimal='" + genderAnimal + '\'' +
                ", tipAnimal='" + tipAnimal + '\'' +
                ", rasaAnimal='" + rasaAnimal + '\'' +
                ", dataNasterii=" + dataNasterii +
                '}';
    }

    public String getNumeAnimal() {
        return numeAnimal;
    }

    public void setNumeAnimal(String numeAnimal) {
        this.numeAnimal = numeAnimal;
    }

    public String getGenderAnimal() {
        return genderAnimal;
    }

    public void setGenderAnimal(String genderAnimal) {
        this.genderAnimal = genderAnimal;
    }

    public String getTipAnimal() {
        return tipAnimal;
    }

    public void setTipAnimal(String tipAnimal) {
        this.tipAnimal = tipAnimal;
    }

    public String getRasaAnimal() {
        return rasaAnimal;
    }

    public void setRasaAnimal(String rasaAnimal) {
        this.rasaAnimal = rasaAnimal;
    }

    public Date getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(Date dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public static Creator<Animal> getCREATOR() {
        return CREATOR;
    }

    protected Animal(Parcel in) {
        numeAnimal = in.readString();
        genderAnimal = in.readString();
        tipAnimal = in.readString();
        rasaAnimal = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(numeAnimal);
        dest.writeString(genderAnimal);
        dest.writeString(tipAnimal);
        dest.writeString(rasaAnimal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Animal> CREATOR = new Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };
}
