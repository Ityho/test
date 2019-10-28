package com.miduchina.wrd.eventanalysis.utils;



public class SinaWeiboMid2Id
{
    // 62进制字典
    private static String[] keys = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    public static String id2Mid(String id)
    {
        return url2Mid(id);
    }

    public static String mid2Id(String mid)
    {
        return url2Id(mid);
    }

    public static String url2Mid(String url)
    {
        String mid = null;

        try
        {
            url = url.trim().substring(url.lastIndexOf("/") + 1);// 取得url地址后的10进制数??3491397862794657??

            String[] surl = new String[3];
            // 3491397862794657：第??????4】第二段??139786】第三段??794657??
            surl[2] = url.substring(url.length() - 7, url.length());
            surl[1] = url.substring(url.length() - 14, url.length() - 7);
            surl[0] = url.substring(0, url.length() - 14);
            // 若不是第????，则去掉左边0
            while (surl[2].startsWith("0"))
            {
                surl[2] = surl[2].substring(1);
            }
            while (surl[1].startsWith("0"))
            {
                surl[1] = surl[1].substring(1);
            }

            String surl0Str62 = str10ToStr62(Long.parseLong(surl[0]));
            String surl1Str62 = str10ToStr62(Long.parseLong(surl[1]));
            String surl2Str62 = str10ToStr62(Long.parseLong(surl[2]));
            // 若不是第????，则不足4位补0 -- add by ydliu 20130604
            while (surl1Str62.length() < 4)
            {
                surl1Str62 = "0" + surl1Str62;
            }
            while (surl2Str62.length() < 4)
            {
                surl2Str62 = "0" + surl2Str62;
            }

            // mid = str10ToStr62(Long.parseLong(surl[0])) + str10ToStr62(Long.parseLong(surl[1])) + str10ToStr62(Long.parseLong(surl[2]));
            mid = surl0Str62 + surl1Str62 + surl2Str62;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return mid;
    }

    public static String url2Id(String url)
    {
        String id = null;

        try
        {
            url = url.trim().substring(url.lastIndexOf("/") + 1);// 取得url地址后的62进制数??5KD0TcOcJsZ??

            String[] surl = new String[3];

            // 5KD0TcOcJsZ：第??????KD】第二段??TcO】第三段【cJsZ??
            surl[2] = str62ToStr10(url.substring(url.length() - 4, url.length()));// 第三段，??位，??0进制

            surl[1] = str62ToStr10(url.substring(url.length() - 8, url.length() - 4));// 第二段，??位，??0进制

            surl[0] = str62ToStr10(url.substring(0, url.length() - 8));// 第一段，取剩下位数，??0进制

            // 若不是第????，则不足7位补0
            while (surl[2].length() < 7)
            {
                surl[2] = "0" + surl[2];
            }
            while (surl[1].length() < 7)
            {
                surl[1] = "0" + surl[1];
            }

            id = surl[0] + surl[1] + surl[2];// 合并三段
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return id;
    }

    // 62进制??0进制
    private static String str62ToStr10(String str62)
    {
        long long10 = 0;

        for (int i = 0; i < str62.length(); i++)
        {
            long l = (long) Math.pow(62, (str62.length() - i - 1));

            String t = str62.substring(i, i + 1);

            long10 += l * getStr62Cursor(t);
        }

        return Long.toString(long10);
    }

    // 10进制转成62进制
    public static String str10ToStr62(long long10)
    {
        String str62 = "";
        int r = 0;
        while (long10 != 0)
        {
            r = (int) (long10 % 62);
            str62 = getStr62Character(r) + str62;
            long10 = long10 / 62;
        }

        // System.out.println("->" + str62);
        return str62;
    }

    /**
     * 根据62进制字符内容获取62进制游标
     *
     * @param str62Character
     *            62进制字符内容
     * @return
     */
    private static int getStr62Cursor(String str62Character)
    {
        int cursor = 0;
        for (String k : keys)
        {
            if (str62Character.equals(k))
            {
                return cursor;
            }
            cursor++;
        }

        return 0;
    }

    /**
     * 根据62进制游标获得62进制字符内容
     *
     * @param str62Cursor
     * @return
     */
    private static String getStr62Character(int str62Cursor)
    {
        String character = "";

        if (str62Cursor >= 0 && str62Cursor <= 61)
        {
            character = keys[str62Cursor];
        }

        return character;
    }
}

