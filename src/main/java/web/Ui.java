package web;

import java.text.*;
import java.util.*;
import model.*;

public final class Ui {
    private Ui() {}
    public static String h(Object value) {
        if(value==null) return "";
        return String.valueOf(value).replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");
    }
    public static String name(Member3 m) {
        return m.getFullName()==null ? "" : (m.getFullName().getLastName()+" "+m.getFullName().getFirstName()).trim();
    }
    public static String money(float value) { return NumberFormat.getIntegerInstance(Locale.forLanguageTag("vi-VN")).format(value); }
    public static String date(Date value) { return format(value,"dd/MM/yyyy"); }
    public static String dateTime(Date value) { return format(value,"dd/MM/yyyy HH:mm"); }
    private static String format(Date value,String pattern) {
        if(value==null) return "(chưa duyệt)";
        SimpleDateFormat f=new SimpleDateFormat(pattern); f.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")); return f.format(value);
    }
    public static int id(String value) {
        try { int id=Integer.parseInt(value); return id>0?id:-1; } catch(Exception e) { return -1; }
    }
}
