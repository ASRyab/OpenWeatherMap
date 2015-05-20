package com.asryab.openweathermap.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ForecastDay implements Parcelable
{
	private TemperatureParameters tempParams;
	private double pressure;
	private int humidity;
	private long date;
	private StateParameters stateParams;
	private WindParameters windParams;
	
	public ForecastDay(){}
	
	public TemperatureParameters getTempParams()
	{
		return tempParams;
	}
	public void setTempParams(TemperatureParameters tempParams)
	{
		this.tempParams = tempParams;
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
	public StateParameters getStateParams()
	{
		return stateParams;
	}
	public void setStateParams(StateParameters stateParams)
	{
		this.stateParams = stateParams;
	}
	public WindParameters getWindParams()
	{
		return windParams;
	}
	public void setWindParams(WindParameters windParams)
	{
		this.windParams = windParams;
	}

	public long getDate()
	{
		return date;
	}

	public void setDate(long date)
	{
		this.date = date;
	}

	@Override
	public String toString()
	{
		return "ForecastDay [tempParams=" + tempParams + ", pressure="
				+ pressure + ", humidity=" + humidity + ", date=" + date
				+ ", stateParams=" + stateParams + ", windParams=" + windParams
				+ "]";
	}

    public static final Creator<ForecastDay> CREATOR = new Creator<ForecastDay>()
    {
        @Override
        public ForecastDay createFromParcel(Parcel source)
        {
            return new ForecastDay(source);
        }

        @Override
        public ForecastDay[] newArray(int size)
        {
            return new ForecastDay[size];
        }
    };

    protected ForecastDay(Parcel in)
    {
        tempParams = (TemperatureParameters) in.readParcelable(TemperatureParameters.class.getClassLoader());
        pressure = in.readDouble();
        humidity = in.readInt();
        stateParams = (StateParameters) in.readParcelable(StateParameters.class.getClassLoader());
        windParams = (WindParameters) in.readParcelable(WindParameters.class.getClassLoader());
        date = in.readLong();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeParcelable(tempParams, 0);
        dest.writeDouble(pressure);
        dest.writeInt(humidity);
        dest.writeParcelable(stateParams, 0);
        dest.writeParcelable(windParams, 0);
        dest.writeLong(date);
    }
}
