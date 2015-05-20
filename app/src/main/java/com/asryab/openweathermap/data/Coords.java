package com.asryab.openweathermap.data;


import android.os.Parcel;
import android.os.Parcelable;

public class Coords implements Parcelable
{
	private double lantitude;
	private double longitude;
	
	public Coords() {}
	public Coords(double lat, double lon)
	{
		setLantitude(lat);
		setLongitude(lon);
	}
	
	public double getLantitude()
	{
		return lantitude;
	}
	
	public void setLantitude(double lantitude)
	{
		this.lantitude = lantitude;
	}
	
	public double getLongitude()
	{
		return longitude;
	}
	
	public void setLongitude(double longitude)
    {
		this.longitude = longitude;
	}
	
	@Override
	public String toString()
	{
		return "Coords [lantitude=" + lantitude + ", longitude=" + longitude
				+ "]";
	}

    public static final Creator<Coords> CREATOR = new Creator<Coords>()
    {
        @Override
        public Coords createFromParcel(Parcel source)
        {
            return new Coords(source);
        }

        @Override
        public Coords[] newArray(int size)
        {
            return new Coords[size];
        }
    };

    protected Coords(Parcel in)
    {
        setLantitude(in.readDouble());
        setLongitude(in.readDouble());
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeDouble(lantitude);
        dest.writeDouble(longitude);
    }
}
