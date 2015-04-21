package com.greghilston.dailyreport.ForecastIOLibrary.src.com.arcusweather.forecastio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForecastIOResponse {
	private ForecastIOCurrently mOutputCurrently;
	private ForecastIOMinutely mOutputMinutely;
	private ForecastIOHourly mOutputHourly;
	private ForecastIODaily mOutputDaily;
	private ForecastIOAlerts mOutputAlerts;
	private ForecastIOFlags mOutputFlags;
	private String latitude;
	private String longitude;
	private String timezone;
	private String offset;
	
	/**
	 * Constructor method which populates the child object
	 * 
	 * @param responseString json string from api call.
	 */
	public ForecastIOResponse(String responseString) {
        JSONObject forecastJsonObject = null;
        try {
			forecastJsonObject = new JSONObject(responseString);
		} catch (JSONException e) {
			return;
		}
        
        try {
        	latitude = forecastJsonObject.getString("latitude");
		} catch (JSONException e) {
            if(ForecastIO.printStackTrace) {
                e.printStackTrace();
            }
		}
        
        try {
        	longitude = forecastJsonObject.getString("longitude");
		} catch (JSONException e) {
            if(ForecastIO.printStackTrace) {
                e.printStackTrace();
            }		}
        
        try {
        	timezone = forecastJsonObject.getString("timezone");
		} catch (JSONException e) {
            if(ForecastIO.printStackTrace) {
                e.printStackTrace();
            }		}
        
        try {
        	offset = forecastJsonObject.getString("offset");
		} catch (JSONException e) {
            if(ForecastIO.printStackTrace) {
                e.printStackTrace();
            }		}
        
        try {
			JSONObject currentlyJSONObject = forecastJsonObject.getJSONObject("currently");
	        mOutputCurrently = buildForecastIOCurrently(currentlyJSONObject);
		} catch (JSONException e) {
            if(ForecastIO.printStackTrace) {
                e.printStackTrace();
            }		}
        
    	try {
			JSONObject minutelyJSONObject = forecastJsonObject.getJSONObject("minutely");
	        mOutputMinutely = buildForecastIOMinutely(minutelyJSONObject);
		} catch (JSONException e) {
            if(ForecastIO.printStackTrace) {
                e.printStackTrace();
            }		}
        
    	try {
			JSONObject hourlyJSONObject = forecastJsonObject.getJSONObject("hourly");
	        mOutputHourly = buildForecastIOHourly(hourlyJSONObject);
		} catch (JSONException e) {
            if(ForecastIO.printStackTrace) {
                e.printStackTrace();
            }		}

    	try {
			JSONObject dailyJSONObject = forecastJsonObject.getJSONObject("daily");
	        mOutputDaily = buildForecastIODaily(dailyJSONObject);
		} catch (JSONException e) {
            if(ForecastIO.printStackTrace) {
                e.printStackTrace();
            }		}

        try {
        	JSONArray alertsJSONArray = forecastJsonObject.getJSONArray("alerts");
	        mOutputAlerts = buildForecastIOAlerts(alertsJSONArray);
        } catch (JSONException e) {
            if(ForecastIO.printStackTrace) {
                e.printStackTrace();
            }        }
        
        try {
        	JSONObject flagsJSONObject = forecastJsonObject.getJSONObject("flags");
        	mOutputFlags = buildForecastIOFlags(flagsJSONObject);
        } catch (JSONException e) {
            if(ForecastIO.printStackTrace) {
                e.printStackTrace();
            }        }
        
	}

	/**
	 * This method will return a specific value based on the key string.
	 * This method will return null if the value is not found.
	 * @param keyString the value to get e.g. latitude or longitude
	 * @return the value of the data point
	 */
	public String getValue(String keyString) {
		String[] fields = keyString.split("-");
		String level = fields[0];
		String value = null;

		try {
			if(level.equals("latitude")) {
				value = latitude;
			}
			else if(level.equals("longitude")) {
				value = longitude;
			}
			else if(level.equals("timezone")) {
				value = timezone;
			}
			else if(level.equals("offset")) {
				value = offset;
			}
			else if(level.equals("currently")) {
				value = getCurrently().getValue(fields[1]);
			}
			else if(level.equals("minutely")) {
				try {
					int listIndex = Integer.parseInt(fields[1]);
					value = getMinutely().getData()[listIndex].getValue(fields[2]);
				}
				catch(NumberFormatException e) {
					value = getMinutely().getValue(fields[1]);
				}
			}
			else if(level.equals("hourly")) {
				try {
					int listIndex = Integer.parseInt(fields[1]);
					value = getHourly().getData()[listIndex].getValue(fields[2]);
				}
				catch(NumberFormatException e) {
					value = getHourly().getValue(fields[1]);
				}
			}
			else if(level.equals("daily")) {
				try {
					int listIndex = Integer.parseInt(fields[1]);
					value = getDaily().getData()[listIndex].getValue(fields[2]);
				}
				catch(NumberFormatException e) {
					value = getDaily().getValue(fields[1]);
				}
			}
			else if(level.equals("alerts")) {
				try {
					int listIndex = Integer.parseInt(fields[1]);
					value = getAlerts().getData()[listIndex].getValue(fields[2]);
				}
				catch(NumberFormatException e) {
					value = getAlerts().getData()[0].getValue(fields[1]);
				}
			}
			else if(level.equals("flags")) {
				value = getFlags().getValue(fields[1]);
			}
		}
		catch(NullPointerException e) {
            if(ForecastIO.printStackTrace) {
                e.printStackTrace();
            }

            return null;
		}
		return value;
	}
	
	/**
	 * method which returns a list of ForecastIODataPoint 
	 * @param keyString string which defines what data points are required.
	 * @return a list of ForecastIODataPoints
	 */
	public ForecastIODataPoint[] getDataPoints(String keyString) {
		ForecastIODataPoint[] value = null;
		try {
			if(keyString.equals("minutely")) {
				value = getMinutely().getData();
			}
			else if(keyString.equals("hourly")) {
				value = getHourly().getData();
			}
			else if(keyString.equals("daily")) {
				value = getDaily().getData();
			}
		}
		catch(NullPointerException e) {
			return null;
		}
		return value;
	}
	
	/**
	 * method which returns the currently object
	 * @return currently object
	 */
	public ForecastIOCurrently getCurrently() {
		return mOutputCurrently;
	}
	
	/**
	 * method which sets up the currently object
	 * @param forecastJsonObject json object used to create object
	 * @return	ForecastIOCurrently object
	 * @see ForecastIOCurrently
	 */
	public ForecastIOCurrently buildForecastIOCurrently(JSONObject forecastJsonObject) {
		return new ForecastIOCurrently(forecastJsonObject);
	}

	/**
	 * method which returns the minutely object
	 * @return minutely object
	 */
	public ForecastIOMinutely getMinutely() {
		return mOutputMinutely;
	}
	
	/**
	 * method which sets up and populates the response object
	 * @param forecastJsonObject input json object for parsing
	 * @return ForecastIOMinutely object which contains all flag info
	 * @see ForecastIOMinutely
	 */
	public ForecastIOMinutely buildForecastIOMinutely(JSONObject forecastJsonObject) {
		return new ForecastIOMinutely(forecastJsonObject);
	}

	/**
	 * method which returns hourly object
	 * @return hourly object
	 */
	public ForecastIOHourly getHourly() {
		return mOutputHourly;
	}
	
	/**
	 * method which sets up and populates the response object
	 * @param forecastJsonObject input json object for parsing
	 * @return ForecastIOHourly object which contains all flag info
	 * @see ForecastIOHourly
	 */
	public ForecastIOHourly buildForecastIOHourly(JSONObject forecastJsonObject) {
		return new ForecastIOHourly(forecastJsonObject);
	}

	/**
	 * method which returns the daily object in api call.
	 * @return daily object
	 */
	public ForecastIODaily getDaily() {
		return mOutputDaily;
	}
	
	/**
	 * method which sets up and populates the response object
	 * @param forecastJsonObject input json object for parsing
	 * @return ForecastIODaily object which contains all flag info
	 * @see ForecastIODaily
	 */
	public ForecastIODaily buildForecastIODaily(JSONObject forecastJsonObject) {
		return new ForecastIODaily(forecastJsonObject);
	}

	/**
	 * method which returns a list of ForecastIOAlerts
	 * @return ForecastIOAlerts object which contains all alert info
	 * @see ForecastIOAlerts
	 */
	public ForecastIOAlerts getAlerts() {
		return mOutputAlerts;
	}
	
	/**
	 * method which sets up and populates the response object
	 * @param forecastJsonArray input json object for parsing
	 * @return ForecastIOAlerts object which contains all flag info
	 * @see ForecastIOAlerts
	 */
	public ForecastIOAlerts buildForecastIOAlerts(JSONArray forecastJsonArray) {
		return new ForecastIOAlerts(forecastJsonArray);
	}
	
	/**
	 * method which returns a list of ForecastIOFlags
	 * @return ForecastIOFlags object which contains all flag info
	 * @see ForecastIOFlags
	 */
	public ForecastIOFlags getFlags() {
		return mOutputFlags;
	}
	
	/**
	 * method which sets up and populates the response object
	 * @param forecastJsonObject input json object for parsing
	 * @return ForecastIOFlags object which contains all flag info
	 * @see ForecastIOFlags
	 */
	public ForecastIOFlags buildForecastIOFlags(JSONObject forecastJsonObject) {
		return new ForecastIOFlags(forecastJsonObject);
	}
}
