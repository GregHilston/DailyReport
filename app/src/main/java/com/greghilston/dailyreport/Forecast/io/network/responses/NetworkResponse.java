/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.greghilston.dailyreport.Forecast.io.network.responses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created: Christopher Alex Brown on 7/31/13.
 */
public abstract class NetworkResponse implements INetworkResponse {

    public enum Status {
        SUCCESS,
        FAIL;
    }

    private Status mStatus = Status.FAIL;

    private String mErrorMessage;

    private int mErrorCode;

    public NetworkResponse() {
        super();
    }

    @Override
    public void onNetworkResponse( JSONObject response ) throws JSONException, IllegalStateException {
        if ( response == null ) {
            throw new IllegalStateException();
        }

        if ( response.has( "error" ) ) {
            mStatus = Status.FAIL;

            mErrorMessage = response.optString( "error" );

            mErrorCode = response.optInt( "code" );
        } else {
            mStatus = Status.SUCCESS;
        }
    }

    @Override
    public Status getStatus() {
        return mStatus;
    }

    public int getCode() {
        return mErrorCode;
    }

    public String getMessage() {
        return mErrorMessage;
    }
}
