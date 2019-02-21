package com.projects.andreafranco.workforcetracking.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserTeam implements Parcelable {

    public int id;
    public String name;
    public String surname;
    public byte[] image;
    public Double latitude;
    public Double longitude;
    public String team;
    public String shift;
    public int shiftStatus;
    public String userFunction;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserTeam createFromParcel(Parcel in) {
            return new UserTeam(in);
        }

        public UserTeam[] newArray(int size) {
            return new UserTeam[size];
        }
    };

    public UserTeam() {

    }

    public UserTeam(int id, String name, String surname, byte[] image, Double latitude, Double longitude, String team, String shift, int shiftstatus, String userfunction) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.team = team;
        this.shift = shift;
        this.shiftStatus = shiftstatus;
        this.userFunction = userfunction;
    }

    // Parcelling part
    public UserTeam(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.surname = in.readString();
        this.image = new byte[in.readInt()];
        in.readByteArray(this.image);
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.team = in.readString();
        this.shift = in.readString();
        this.shiftStatus = in.readInt();
        this.userFunction = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeInt(image.length);
        dest.writeByteArray(this.image);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.team);
        dest.writeString(this.shift);
        dest.writeInt(this.shiftStatus);
        dest.writeString(this.userFunction);
    }
}
