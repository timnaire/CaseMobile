package org.kidzonshock.acase.acase.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceDataClient
{
    static final String pref_clientid = "client_clientid";
    static final String pref_client_firstame = "client_firstname";
    static final String pref_client_lastname = "client_lastname";
    static final String pref_client_email = "client_email";
    static final String pref_client_phone = "client_phone";
    static final String pref_client_address = "client_address";
    static final String pref_client_profilepic = "client_in_profilepic";
    static final String pref_client_sex = "client_sex";
    static final String pref_client_FCM_TOKEN = "client_FCM_TOKEN";
    static final String pref_client_status = "client_status";

    public static SharedPreferences getSharedPreferences(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    //  setting preference
    public static void setLoggedInClientid(Context ctx, String clientid)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_clientid);
        editor.putString(pref_clientid, clientid);
        editor.commit();
    }
    public static void setLoggedInFirstname(Context ctx, String firstname)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_client_firstame);
        editor.putString(pref_client_firstame, firstname);
        editor.commit();
    }
    public static void setLoggedInLastname(Context ctx, String lastname)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_client_lastname);
        editor.putString(pref_client_lastname, lastname);
        editor.commit();
    }
    public static void setLoggedInEmail(Context ctx, String email)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_client_email);
        editor.putString(pref_client_email, email);
        editor.commit();
    }
    public static void setLoggedInPhone(Context ctx, String phone)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_client_phone);
        editor.putString(pref_client_phone, phone);
        editor.commit();
    }

    public static void setLoggedInAddress(Context ctx, String address)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_client_address);
        editor.putString(pref_client_address, address);
        editor.commit();
    }
    public static void setLoggedInProfilePicture(Context ctx, String profilepic)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_client_profilepic);
        editor.putString(pref_client_profilepic, profilepic);
        editor.commit();
    }
    public static void setLoggedInFcmToken(Context ctx, String token)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_client_FCM_TOKEN);
        editor.putString(pref_client_FCM_TOKEN, token);
        editor.commit();
    }

    public static void setLoggedInSex(Context ctx, String sex)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_client_sex);
        editor.putString(pref_client_sex, sex);
        editor.commit();
    }

    //    getting shared preferences
    public static String getLoggedInClientid(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_clientid, "");
    }
    public static String getLoggedInFirstname(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_client_firstame, "");
    }
    public static String getLoggedInLastname(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_client_lastname, "");
    }
    public static String getLoggedInEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_client_email, "");
    }
    public static String getLoggedInPhone(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_client_phone, "");
    }
    public static String getLoggedInAddress(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_client_address, "");
    }
    public static String getLoggedInProfilePicture(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_client_profilepic, "");
    }
    public static String getLoggedInFcmToken(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_client_FCM_TOKEN, "");
    }
    public static String getLoggedInSex(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_client_sex, "");
    }


    //  status
    public static void setUserLoggedInStatus(Context ctx, boolean status)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_client_status);
        editor.putBoolean(pref_client_status, status);
        editor.commit();
    }

    public static boolean getUserLoggedInStatus(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(pref_client_status, false);
    }

    //    clearing preferences
    public static void clearLoggedInClient(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_clientid);
        editor.remove(pref_client_firstame);
        editor.remove(pref_client_lastname);
        editor.remove(pref_client_email);
        editor.remove(pref_client_phone);
        editor.remove(pref_client_address);
        editor.remove(pref_client_profilepic);
        editor.remove(pref_client_status);
        editor.remove(pref_client_sex);
        editor.commit();
    }
}
