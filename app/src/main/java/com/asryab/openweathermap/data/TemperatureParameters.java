package com.asryab.openweathermap.data;


import android.os.Parcel;
import android.os.Parcelable;

public class TemperatureParameters implements Parcelable
{
	private double night, morning, day, evening;

	public TemperatureParameters(){}

	public TemperatureParameters(double night, double morning, double day, double evening)
	{
		this.night = night;
		this.morning = morning;
		this.day = day;
		this.evening = evening;
	}

	public double getNight()
	{
		return night;
	}

	public void setNight(double night)
	{
		this.night = night;
	}

	public double getMorning()
	{
		return morning;
	}

	public void setMorning(double morning)
	{
		this.morning = morning;
	}

	public double getDay()
	{
		return day;
	}

	public void setDay(double day)
	{
		this.day = day;
	}

	public double getEvening()
	{
		return evening;
	}

	public void setEvening(double evening)
	{
		this.evening = evening;
	}

	@Override
	public String toString()
	{
		return "TemperatureParameters [night=" + night + ", morning=" + morning
				+ ", day=" + day + ", evening=" + evening + "]";
	}

    public static final Creator<TemperatureParameters> CREATOR = new Creator<TemperatureParameters>()
    {
        @Override
        public TemperatureParameters createFromParcel(Parcel source)
        {
            return new TemperatureParameters(source);
        }

        @Override
        public TemperatureParameters[] newArray(int size)
        {
            return new TemperatureParameters[size];
        }
    };

    protected TemperatureParameters(Parcel in)
    {
        double t[] = new double[]{};
        in.readDoubleArray(t);
        night = t[0];
        morning = t[1];
        day = t[2];
        evening = t[3];
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeDoubleArray(new double[]{night, morning, day, evening});
    }
}
