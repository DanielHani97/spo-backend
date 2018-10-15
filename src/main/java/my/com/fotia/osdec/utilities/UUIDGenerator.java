package my.com.fotia.osdec.utilities;

import java.util.Random;
import java.util.UUID;

public class UUIDGenerator {
	
	public static UUIDGenerator uuid;
	private String[] characters = {"A","B","C","D","E","F","G","H","I",
			"J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X",
			"Y","Z","a","b","c","d","e","f","g","h","i","j","k","l",
			"m","n","o","p","q","r","s","t","u","v","w","x","y","z",
			"0","1","2","3","4","5","6","7","8","9"};
	
	private int idLength = 20;
	
	public static UUIDGenerator getInstance(){
		if(uuid == null){
			uuid = new UUIDGenerator();
		}
		
		return uuid;
	}
	
	public String getId(){
		UUID id = UUID.randomUUID();
		String strId = id.toString().replace("-", "");
		return strId + generateAdditionalKey(idLength - strId.length());
	}
	
	/**
	 * @param uuidLength number of characters taken from UUID
	 * @param length username length
	 * @return
	 */
	public String generateTempUsername(int uuidLength, int length){
		UUID id = UUID.randomUUID();
		String strId = id.toString().replace("-", "").substring(0, uuidLength);
		return strId + generateAdditionalKey(length - strId.length());
	}
	
	private String generateAdditionalKey(int keyLength){
		StringBuffer resultKey = new StringBuffer();
		
		for(int start = 0;start < keyLength; start++){
			Random random = new Random();
			if(random != null){
				int range = characters.length;
				resultKey.append(characters[(random.nextInt(range))]);
			}
		}
		
		return resultKey.toString();
	}
}
