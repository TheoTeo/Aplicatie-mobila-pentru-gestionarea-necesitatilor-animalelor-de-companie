package com.ase.dam.licenta01;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarVeterinar implements Parcelable {
    private Date dataEveniment;
    private String numeEveniment;
    private String emailUser;

    public CalendarVeterinar(Date dataEveniment, String numeEveniment, String emailUser) {
        this.dataEveniment = dataEveniment;
        this.numeEveniment = numeEveniment;
        this.emailUser = emailUser;
    }

    public CalendarVeterinar(Date dataEveniment, String numeEveniment) {
        this.dataEveniment = dataEveniment;
        this.numeEveniment = numeEveniment;
    }

    public Date getDataEveniment() {
        return dataEveniment;
    }

    public void setDataEveniment(Date dataEveniment) {
        this.dataEveniment = dataEveniment;
    }

    public String getNumeEveniment() {
        return numeEveniment;
    }

    public void setNumeEveniment(String numeEveniment) {
        this.numeEveniment = numeEveniment;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MMM/yyyy");
        String dateOnly = dateFormat. format(dataEveniment);
        return numeEveniment+"\n"+ dateOnly;

    }

    public static Creator<CalendarVeterinar> getCREATOR() {
        return CREATOR;
    }

    protected CalendarVeterinar(Parcel in) {
        numeEveniment = in.readString();
        emailUser = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(numeEveniment);
        dest.writeString(emailUser);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CalendarVeterinar> CREATOR = new Creator<CalendarVeterinar>() {
        @Override
        public CalendarVeterinar createFromParcel(Parcel in) {
            return new CalendarVeterinar(in);
        }

        @Override
        public CalendarVeterinar[] newArray(int size) {
            return new CalendarVeterinar[size];
        }
    };
}
