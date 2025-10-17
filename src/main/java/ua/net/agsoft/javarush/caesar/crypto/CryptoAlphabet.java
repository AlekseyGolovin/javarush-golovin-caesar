package ua.net.agsoft.javarush.caesar.crypto;

public class CryptoAlphabet {


    // @formatter:off

    public static final String EN_BASIC =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz";

    public static final String EN_ADVANCED =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            ".,«»\"':!? ";

    public static final String LATIN =
            "0123456789" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "! \"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~«»";

    public static final String CYRIL =
            "0123456789" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "! \"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~«»" +
            "абвгґдеёєжзийіїклмнопрстуфхцчшщэюяьъы" +
            "АБВГҐДЕЁЄЖЗИЙІЇКЛМНОПРСТУФХЦЧШЩЭЮЯЬЪЫ";

    public static final String LATIN_MIX =
            "$w/}B&)_\\{HKZ 8ncO[T>uWU" +
            "4M!*.aVLbl0:YS?<2dXoCFI7," +
            "36^xes1\"JpG=Pyh`QEq5|zRv" +
            "+(9~fgj@«tDr]%Nk;#-m'A»i";

    public static final String CYRIL_MIX =
            "2~L{АmЮцОh WТС<г[ё&лЁ_нs:cґ\\" +
            "ъ$1сьМЬnBx0JФe>#CE^ҐЗ-ЛYXи,Шk" +
            "MNgбRQф8=wSроИзя'yа9Й7ї)Ж|fІП" +
            "КЭOР}ч]oqБ/п%`Г?*upЪ(ЕжiуG\"z" +
            "ЩдU3tDvВэЄ.!4dйЯbЦKlAщVЇ6FЫХP" +
            "ыH«веюНі+У;Tj5ДIrхєшZт»кaм@Ч";

    // @formatter:on


}
