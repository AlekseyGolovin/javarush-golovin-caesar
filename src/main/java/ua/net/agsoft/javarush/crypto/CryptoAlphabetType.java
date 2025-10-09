package ua.net.agsoft.javarush.crypto;

public enum CryptoAlphabetType {

    EN_BASIC(CryptoAlphabet.EN_BASIC),
    EN_ADVANCED(CryptoAlphabet.EN_ADVANCED),
    LATIN(CryptoAlphabet.LATIN),
    CYRIL(CryptoAlphabet.CYRIL),
    LATIN_MIX(CryptoAlphabet.LATIN_MIX),
    CYRIL_MIX(CryptoAlphabet.CYRIL_MIX);

    private final char[] alphabet;

    CryptoAlphabetType(String alphabetString) {
        this.alphabet = alphabetString.toCharArray();
    }

    public char[] getAlphabet() {
        return alphabet;
    }
}
