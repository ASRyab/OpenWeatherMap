package com.asryab.openweathermap.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ForecastWeather implements Parcelable
{
	private String city, country;
	private Coords coords;
	private List<ForecastDay> daysList;
	
	public ForecastWeather(){}

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

	public Coords getCoords()
	{
		return coords;
	}

	public void setCoords(Coords coords)
	{
		this.coords = coords;
	}
	
	public List<ForecastDay> getDaysList()
	{
		return daysList;
	}

	public void setDaysList(List<ForecastDay> daysList)
	{
		this.daysList = daysList;
	}
		
	@Override
	public String toString()
	{
		return "ForecastWeather [city=" + city + ", country=" + country
				+ ", daysList=" + daysList + ", coords="
				+ coords + "]";
	}

    public static final Creator<ForecastWeather> CREATOR = new Creator<ForecastWeather>()
    {
        @Override
        public ForecastWeather createFromParcel(Parcel source)
        {
            return new ForecastWeather(source);
        }

        @Override
        public ForecastWeather[] newArray(int size)
        {
            return new ForecastWeather[size];
        }
    };

    protected ForecastWeather(Parcel in)
    {
        city = in.readString();
        country = in.readString();
        coords = (Coords) in.readParcelable(Coords.class.getClassLoader());
        daysList = new ArrayList<ForecastDay>();
        in.readTypedList(daysList, ForecastDay.CREATOR);
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
        dest.writeParcelable(coords, 0);
        dest.writeTypedList(daysList);
    }
}
