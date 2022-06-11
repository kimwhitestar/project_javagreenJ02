package common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {
	private String encryptPwd = "";
	
	public String encryptSHA256(String target){
    String sha = "";
    try {
	   	 MessageDigest sh = MessageDigest.getInstance("SHA-256");
	     sh.update(target.getBytes());
	     byte byteData[] = sh.digest();
	     StringBuffer sb = new StringBuffer();
	     for(int i = 0 ; i < byteData.length ; i++){
	         sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
	     }
	     sha = sb.toString();
	   } catch(NoSuchAlgorithmException e) {
	       System.out.println("Encrypt Error - NoSuchAlgorithmException");
	       sha = null;
	   }
	   return sha;
   } 
	
	public String decryptSHA256(String target){
    String sha = "";
    return sha;
	}
	
	public void setEncryptPwd(String encryptPwd) {
		this.encryptPwd = encryptPwd;
	}
	
	public String getEncryptPwd() {
		return encryptSHA256(this.encryptPwd);
	}
}