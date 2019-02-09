package org.kidzonshock.acase.acase.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceDataLawyer {

    static final String pref_lawyerid = "lawyer_lawyerid";
    static final String pref_firstame = "lawyer_firstname";
    static final String pref_lastname = "lawyer_lastname";
    static final String pref_email = "lawyer_email";
    static final String pref_phone = "lawyer_phone";
    static final String pref_cityOrMunicipality = "lawyer_cityOrMunicipality";
    static final String pref_office = "lawyer_office";
    static final String pref_aboutme = "lawyer_aboutme";
    static final String pref_profilepic = "lawyer_profilepic";
    static final String pref_firm = "lawyer_firm";
    static final String pref_FCM_TOKEN = "lawyer_FCM_TOKEN";
    static final String pref_status = "lawyer_status";


    public static SharedPreferences getSharedPreferences(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
//  setting preference
    public static void setLoggedInLawyerid(Context ctx, String lawyerid)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_lawyerid);
        editor.putString(pref_lawyerid, lawyerid);
        editor.commit();
    }
    public static void setLoggedInFirstname(Context ctx, String firstname)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_firstame);
        editor.putString(pref_firstame, firstname);
        editor.commit();
    }
    public static void setLoggedInLastname(Context ctx, String lastname)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_lastname);
        editor.putString(pref_lastname, lastname);
        editor.commit();
    }
    public static void setLoggedInEmail(Context ctx, String email)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_email);
        editor.putString(pref_email, email);
        editor.commit();
    }
    public static void setLoggedInPhone(Context ctx, String phone)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_phone);
        editor.putString(pref_phone, phone);
        editor.commit();
    }
    public static void setLoggedInCityOrMunicipality(Context ctx, String cityOrMunicipality)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_cityOrMunicipality);
        editor.putString(pref_cityOrMunicipality, cityOrMunicipality);
        editor.commit();
    }
    public static void setLoggedInOffice(Context ctx, String office)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_office);
        editor.putString(pref_office, office);
        editor.commit();
    }
    public static void setLoggedInAboutme(Context ctx, String aboutme)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_aboutme);
        editor.putString(pref_aboutme, aboutme);
        editor.commit();
    }
    public static void setLoggedInProfilePicture(Context ctx, String profilepic)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_profilepic);
        editor.putString(pref_profilepic, profilepic);
        editor.commit();
    }

    public static void setLoggedInFcmToken(Context ctx, String fcmtoken)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_FCM_TOKEN);
        editor.putString(pref_FCM_TOKEN, fcmtoken);
        editor.commit();
    }
    public static void setLoggedInFirm(Context ctx, String firm)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_firm);
        editor.putString(pref_firm, firm);
        editor.commit();
    }

//    getting shared preferences
    public static String getLoggedInLawyerid(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_lawyerid, "");
    }
    public static String getLoggedInFirstname(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_firstame, "");
    }
    public static String getLoggedInLastname(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_lastname, "");
    }
    public static String getLoggedInEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_email, "");
    }
    public static String getLoggedInPhone(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_phone, "");
    }
    public static String getLoggedInCityOrMunicipality(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_cityOrMunicipality, "");
    }
    public static String getLoggedInOffice(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_office, "");
    }
    public static String getLoggedInAboutme(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_aboutme, "");
    }
    public static String getLoggedInProfilePicture(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_profilepic, "");
    }

    public static String getLoggedInFcmToken(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_FCM_TOKEN, "");
    }

    public static String getLoggedInFirm(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_firm, "");
    }


//  status
    public static void setUserLoggedInStatus(Context ctx, boolean status)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_status);
        editor.putBoolean(pref_status, status);
        editor.commit();
    }

    public static boolean getUserLoggedInStatus(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(pref_status, false);
    }

//    clearing preferences
    public static void clearLoggedInLawyer(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_lawyerid);
        editor.remove(pref_firstame);
        editor.remove(pref_lastname);
        editor.remove(pref_email);
        editor.remove(pref_phone);
        editor.remove(pref_cityOrMunicipality);
        editor.remove(pref_office);
        editor.remove(pref_aboutme);
        editor.remove(pref_profilepic);
        editor.remove(pref_status);
        editor.remove(pref_firm);

        editor.commit();
    }
}
