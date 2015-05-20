package com.asryab.openweathermap.data;


import android.os.Parcel;
import android.os.Parcelable;

public class CurrentWeather implements Parcelable
{
	private String city, country;
	
	private MainParameters mainParams;
	private WindParameters windParams;
	private StateParameters stateParams;
	private Coords coords;
	private long sunset, sunrise, dt;
	
	public CurrentWeather(){}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public MainParameters getMainParams()
	{
		return mainParams;
	}

	public void setMainParams(MainParameters mainParams)
	{
		this.mainParams = mainParams;
	}

	public WindParameters getWindParams()
	{
		return windParams;
	}

	public void setWindParams(WindParameters windParams)
	{
		this.windParams = windParams;
	}

	public StateParameters getStateParams()
	{
		return stateParams;
	}

	public void setStateParams(StateParameters stateParams)
	{
		this.stateParams = stateParams;
	}

	public Coords getCoords()
	{
		return coords;
	}

	public void setCoords(Coords coords)
	{
		this.coords = coords;
	}

	public long getSunset()
	{
		return sunset;
	}

	public void setSunset(long sunset)
	{
		this.sunset = sunset;
	}

	public long getSunrise()
	{
		return sunrise;
	}

	public void setSunrise(long sunrise)
	{
		this.sunrise = sunrise;
	}

	public long getDate()
	{
		return dt;
	}

	public void setDate(long dt)
	{
		this.dt = dt;
	}

	@Override
	public String toString()
	{
		return "CurrentWeather [city=" + city + ", country="
				+ country + ", mainParams=" + mainParams + ", windParams="
				+ windParams + ", stateParams=" + stateParams + ", coords="
				+ coords + ", sunset=" + sunset + ", sunrise=" + sunrise
				+ ", dt=" + dt + "]";
	}


	public String toStringShow()
	{
		return "city=" + city + "\n country="
				+ country + "\n mainParams=" + mainParams + "\n windParams="
				+ windParams + "\n stateParams=" + stateParams + "\n coords="
				+ coords + "\n sunset=" + sunset + "\n sunrise=" + sunrise
				+ "\n dt=" + dt + "]";
	}

    public static final Creator<CurrentWeather> CREATOR = new Creator<CurrentWeather>()
    {
        @Override
        public CurrentWeather[] newArray(int size)
        {
            return new CurrentWeather[size];
        }

        @Override
        public CurrentWeather createFromParcel(Parcel source)
        {
            return new CurrentWeather(source);
        }
    };

    protected CurrentWeather(Parcel in)
    {
        city = in.readString();
        country = in.readString();
        dt = in.readLong();
        sunrise = in.readLong();
        sunset = in.readLong();
        mainParams = (MainParameters) in.readParcelable(MainParameters.class.getClassLoader());
        stateParams = (StateParameters) in.readParcelable(StateParameters.class.getClassLoader());
        windParams = (WindParameters) in.readParcelable(WindParameters.class.getClassLoader());
        coords = (Coords) in.readParcelable(Coords.class.getClassLoader());
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(city);
        dest.writeString(country);
        dest.writeLong(dt);
        dest.writeLong(sunrise);
        dest.writeLong(sunset);
        dest.writeParcelable(mainParams, 0);
        dest.writeParcelable(stateParams, 0);
        dest.writeParcelable(windParams, 0);
        dest.writeParcelable(coords, 0);
    }
}
