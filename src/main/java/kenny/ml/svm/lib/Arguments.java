package kenny.ml.svm.lib;

import java.util.HashMap;

public class Arguments {
	
	private static Arguments INSTANCE = null;
	
	private HashMap<String, String> argumentMap = new HashMap<String, String>();
	
	private Arguments() {
		// singleton class, can't initialize publically
	}
	
	public static Arguments getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Arguments();
		}
		return INSTANCE;
	}
	
	public static Arguments init(HashMap<String, String> m) {
		Arguments arguments = Arguments.getInstance();
		arguments.setAll(m);
		return arguments;
	}
	
	/**
	 * parse an array of command line arguments
	 * @param args
	 */
	public void parse(String[] args) {
		for(String arg : args) {
			if(arg.substring(0,1).equals("-") ||
			   arg.substring(0,2).equals("--")) { // is arg a flag?
				argumentMap.put(arg, "true");
			} else if(arg.contains("=")) { // is it a key=value argument?
				int equalsIndex = arg.indexOf("=");
				String key = arg.substring(0, equalsIndex);
				String value = arg.substring(equalsIndex + 1);
				argumentMap.put(key, value);
			} else {
				argumentMap.put(arg, null);
				//throw new IllegalArgumentException("Unknown Argument!: ("+ arg+")");
			}
		}
	}

	/**
	 * retrieve an argument value
	 * syntax for flag "key=value"
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return argumentMap.get(key);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		return Integer.parseInt(argumentMap.get(key));
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public long getLong(String key) {
		return Long.parseLong(argumentMap.get(key));
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public float getFloat(String key) {
		return Float.parseFloat(argumentMap.get(key));
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public double getDouble(String key) {
		return Double.parseDouble(argumentMap.get(key));
	}
	
	
	/**
	 * determine if a flag has been set
	 * @param flag
	 * @return
	 */
	public boolean isFlag(String flag) {
		String value = argumentMap.get(flag);
		if(value != null && value.equals("true")) {
			return true;
		}
		return false;
	}
	
	public boolean isSet(String key) {
		return argumentMap.keySet().contains(key);
	}
	
	public void set(String key, String value) {
		if(argumentMap.containsKey(key)) {
			argumentMap.remove(key);
		}
		argumentMap.put(key, value);
	}
	
	public void setAll(HashMap<String, String> m) {
		argumentMap.putAll(m);
	}
	
	/**
	 * toString
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String key : argumentMap.keySet()) {
			sb.append(key).append(" : ").append(argumentMap.get(key)).append("\n");
		}
		return sb.toString();
	}

}
