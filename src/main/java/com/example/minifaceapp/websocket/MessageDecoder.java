package com.example.minifaceapp.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

public class MessageDecoder implements Decoder.Text<JSONObject> {

    @Override
    public JSONObject decode(final String textMessage) throws DecodeException {
        return new JSONObject(textMessage);
    }

	@Override
	public void init(EndpointConfig config) {
		// not used
	}

	@Override
	public void destroy() {
		// not used
	}

	@Override
	public boolean willDecode(String s) {
		return true;
	}

}