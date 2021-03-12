package com.agegame.request;

import com.agegame.utils.GameEra;

public interface Request {
    RequestType requestType = null;

    Object getRequestData();

}
