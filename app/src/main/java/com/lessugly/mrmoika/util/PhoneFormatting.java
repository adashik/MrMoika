package com.lessugly.mrmoika.util;

import java.util.HashMap;

public class PhoneFormatting {

    public static HashMap<String, Object> result = new HashMap<>();
    public static String phone;
    public static int selection;

    public static void setPhone(String phone) {
        PhoneFormatting.phone = phone;
    }

    public static void setSelection(int selection) {
        PhoneFormatting.selection = selection;
    }

    public static HashMap<String, Object> formatPhone(CharSequence s,int start, int count, int after){
        String changedNumber = phoneClear(String.valueOf(s));
        if (changedNumber.length() == 0) {
            setPhone("");
            setSelection(0);
        }
        else
        if (changedNumber.length() < 4){
            setPhone("(" + changedNumber);
            if (s.length() == after && after > 1) setSelection(after);
            else
            if (count == 0)
                switch (start){
                    case 0:
                        setSelection(start + after + 1);
                        break;
                    case 4:
                        setSelection(start + after + 2);
                        break;
                    default:
                        setSelection(start + after);
                        break;
                }
            else
                switch (start){
                    case 5:
                        setSelection(start - 1);
                        break;
                    case 6:
                        setSelection(start - 2);
                        break;
                    default:
                        setSelection(start);
                        break;
                }
        } else
        if (changedNumber.length() < 7){
            setPhone("(" + changedNumber.substring(0, 3) + ") " + changedNumber.substring(3));
            if (s.length() == after && after > 1) setSelection(after);
            else
            if (count == 0)
                switch (start){
                    case 0:
                        setSelection(start + after + 1);
                        break;
                    case 4:
                        setSelection(start + after + 2);
                        break;
                    default:
                        setSelection(start + after);
                        break;
                }
            else
                switch (start){
                    case 10:
                        setSelection(start - 1);
                        break;
                    default:
                        setSelection(start);
                        break;
                }
        } else
        if (changedNumber.length() < 9){
            setPhone("(" + changedNumber.substring(0, 3) + ") " + changedNumber.substring(3, 6) + "-" + changedNumber.substring(6));
            if (s.length() == after && after > 1) setSelection(after);
            else
            if (count == 0)
                switch (start){
                    case 0:
                        setSelection(start + after + 1);
                        break;
                    case 4:
                        setSelection(start + after + 2);
                        break;
                    case 9:
                        setSelection(start + after + 1);
                        break;
                    default:
                        setSelection(start + after);
                        break;
                }
            else
                switch (start){
                    case 13:
                        setSelection(start - 1);
                        break;
                    default:
                        setSelection(start);
                        break;
                }
        } else {
            setPhone("(" + changedNumber.substring(0, 3) + ") " + changedNumber.substring(3, 6) + "-" + changedNumber.substring(6, 8)
                    + "-" + changedNumber.substring(8));
            if (s.length() == after && after > 1) setSelection(after);
            else
            if (count == 0)
                switch (start){
                    case 0:
                        setSelection(start + after + 1);
                        break;
                    case 4:
                        setSelection(start + after + 2);
                        break;
                    case 9:
                        setSelection(start + after + 1);
                        break;
                    case 12:
                        setSelection(start + after + 1);
                        break;
                    default:
                        setSelection(start + after);
                        break;
                }
            else
                switch (start){
                    case 16:
                        setSelection(start - 1);
                        break;
                    default:
                        setSelection(start);
                        break;
                }
        }

        result.put("phone",phone);
        result.put("selection",selection);
        
        return result;
    }

    public static String phoneClear(String phone){
        return phone.replace("(","").replace(")","").replace("-","").replace(" ","");
    }

}
