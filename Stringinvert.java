public class Stringinvert() {

	public String stringinvert(String string) {
		if (string.length()==1) {
			return string;
		}
		if(string.length()==2) {
			string=string.substring(string.length()) + string;
		}
		string=string.substring(string.length()) + string;
		stringinvert(string.substring(1,string.length()-1));
	}
	
}