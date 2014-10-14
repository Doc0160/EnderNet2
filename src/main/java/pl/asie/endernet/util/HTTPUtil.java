package pl.asie.endernet.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class HTTPUtil {
	public static String createParamsString(Map<String, String> params) {
		String out = "";
		Iterator it = params.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			String encodedValue = "";
			try {
				encodedValue = URLEncoder.encode((String)pairs.getValue(), "UTF-8");
			} catch(UnsupportedEncodingException e) {
				encodedValue = URLEncoder.encode((String)pairs.getValue());
			}
			out += pairs.getKey() + "=" + encodedValue + (it.hasNext() ? "&" : "\r\n");
			it.remove();
		}
		return out;
	}
	
	public static InputStream sendPOSTMessage(String server, String uri, String data) {
		if(!uri.startsWith("/")) uri = "/" + uri;
		
		try {
			URL url = new URL("http://" + server + uri);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(data);
			out.flush();
			out.close();
			return con.getInputStream();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
