package com.asryab.openweathermap.data;


import android.os.Parcel;
import android.os.Parcelable;

public class MainParameters implements Parcelable
{
	private int humidity;
	private double temperature;
	private double pressure;
	
	public MainParameters(){}
	public MainParameters(double temperature, double pressure, int humidity)
	{
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
	}

	public double getTemperature()
	{
		return temperature;
	}

	public void setTemperature(double temperature)
	{
		this.temperature = temperature;
	}

	public double getPressure()
	{
		return pressure;
	}

	public void setPressure(double pressure)
	{
		this.pressure = pressure;
	}

	public double getHumidity()
	{
		return humidity;
	}

	public void setHumidity(int humidity)
	{
		this.humidity = humidity;
	}

	@Override
	public String toString()
	{
		return "MainParameters [temperature=" + temperature + ", pressure="
				+ pressure + ", humidity=" + humidity + "]";
	}

    public static final Creator<MainParameters> CREATOR = new Creator<MainParameters>()
    {
        @Override
        public MainParameters createFromParcel(Parcel source)
        {
            return new MainParameters(source);
        }

        @Override
        public MainParameters[] newArray(int size)
        {
            return new MainParameters[size];
        }
    };

    protected MainParameters(Parcel in)
    {
        temperature = in.readDouble();
        humidity = in.readInt();
        pressure = in.readDouble();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeDouble(temperature);
        dest.writeInt(humidity);
        dest.writeDouble(pressure);
    }
}
