package org.overlake.mat803.multiplechoicemathquiz.GraphPoints;

import android.os.Parcel;
import android.os.Parcelable;

public class XYValue implements Parcelable {

    private double x;
    private double y;

    public XYValue(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected XYValue(Parcel in) {
        x = in.readDouble();
        y = in.readDouble();
    }

    public static final Creator<XYValue> CREATOR = new Creator<XYValue>() {
        @Override
        public XYValue createFromParcel(Parcel in) {
            return new XYValue(in);
        }

        @Override
        public XYValue[] newArray(int size) {
            return new XYValue[size];
        }
    };

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(x);
        dest.writeDouble(y);
    }
}
