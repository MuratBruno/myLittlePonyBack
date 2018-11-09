package littlePoneyBack.controller;

public class JwtAuthentificationResponse {
	private String accesToken;
	public JwtAuthentificationResponse(String accesToken) {
		super();
		this.accesToken = accesToken;
	}
	@Override
	public String toString() {
		return "JwtAuthentificationResponse [accesToken=" + accesToken + ", tokenType=" + tokenType + "]";
	}
	public String getAccesToken() {
		return accesToken;
	}
	public void setAccesToken(String accesToken) {
		this.accesToken = accesToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	private String tokenType = "Bearer";
}
