import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.tortmayr.chillisp.api.ActionMessage;
import at.tortmayr.chillisp.api.actions.RequestModelAction;
import at.tortmayr.chillisp.api.json.ActionTypeAdapter;

public class Test {

	public static void main (String[] args) {
		GsonBuilder builder = new GsonBuilder();
		ActionTypeAdapter.configureGson(builder);
		Gson gson = builder.create();
		Map<String,String> options= new HashMap<>();
		options.put("needsClientLayout", "true");
		RequestModelAction action= new RequestModelAction(options);
	
		ActionMessage msg= new ActionMessage("test", action);
		String out= gson.toJson(msg,ActionMessage.class);
		System.out.println(out);
	}
}
