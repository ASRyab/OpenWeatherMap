package com.asryab.openweathermap.data;


import android.os.Parcel;
import android.os.Parcelable;

public class WindParameters implements Parcelable
{
	private int deg;
	private double speed;
	
	public WindParameters(){}
	public WindParameters(int deg, double speed)
	{
		this.deg = deg;
		this.speed = speed;
	}

	public double getDeg()
	{
		return deg;
	}

	public void setDeg(int deg)
	{
		this.deg = deg;
	}

	public double getSpeed()
	{
		return speed;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}

	@Override
	public String toString()
	{
		return "WindParameters [deg=" + deg + ", speed=" + speed + "]";
	}

    public static final Creator<WindParameters> CREATOR = new Creator<WindParameters>()
    {
        @Override
        public WindParameters createFromParcel(Parcel source)
        {
            return new WindParameters(source);
        }

        @Override
        public WindParameters[] newArray(int size)
        {
            return new WindParameters[size];
        }
    };

    protected WindParameters(Parcel in)
    {
        deg = in.readInt();
        speed = in.readDouble();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(deg);
        dest.writeDouble(speed);
    }
}
