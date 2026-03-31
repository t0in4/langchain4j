class AccessToken {
    String accessToken;
    long expiresIn;
    long issuedAt;

    AccessToken(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.issuedAt = System.currentTimeMillis();
    }

    boolean isExpired() {
        return System.currentTimeMillis() > (issuedAt + (expiresIn - 60) * 1000L);
    }
}