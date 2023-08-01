
private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

/**
 * 매개변수의 길이만큼 영문 대/소문자, 숫자 섞은 랜덤 문자열 반환
 * @param length
 * @return
 */
public static String generateRandomString(int length) {

    Random random = new Random();
    StringBuilder key = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
        int randomIndex = random.nextInt(CHARACTERS.length());
        char randomChar = CHARACTERS.charAt(randomIndex);
        key.append(randomChar);
    }
    return key.toString();
}

/**
 * url 생성(ex: localhost:9000/user/newPassword?key=r50k1n1Q60364K13TX7F4114366s18)
 * @param host
 * @param contextPath
 * @param key
 * @return
 */
public static String generatePasswordResetURL(String host, String contextPath, String key) {

    StringBuilder url = new StringBuilder();
    //로컬
    url.append("http://").append(host);
    //운영
//        url.append("https://").append(host);

    if (!contextPath.startsWith("/")) {
        url.append("/");
    }

    url.append(contextPath);

    if (!contextPath.endsWith("/")) {
        url.append("/");
    }

    url.append("newPassword").append("?key=").append(key);

    return url.toString();
}
