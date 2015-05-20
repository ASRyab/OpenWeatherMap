package com.asryab.openweathermap.data;


import android.os.Parcel;
import android.os.Parcelable;

public class StateParameters implements Parcelable
{
	private String icon;
	private String description;
	
	public StateParameters(){}
	public StateParameters(String icon, String description)
	{
		this.icon = icon;
		this.description = description;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public String toString()
	{
		return "StateParameters [icon=" + icon + ", description=" + description
				+ "]";
	}

    public static final Creator<StateParameters> CREATOR = new Creator<StateParameters>()
    {
        @Override
        public StateParameters createFromParcel(Parcel source)
        {
            return new StateParameters(source);
        }

        @Override
        public StateParameters[] newArray(int size)
        {
            return new StateParameters[size];
        }
    };

    protected StateParameters(Parcel source)
    {
        setIcon(source.readString());
        setDescription(source.readString());
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(icon);
        dest.writeString(description);
    }
}
