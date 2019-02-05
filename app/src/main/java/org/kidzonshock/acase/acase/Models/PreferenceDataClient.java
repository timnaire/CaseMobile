package org.kidzonshock.acase.acase.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceDataClient
{
    static final String pref_clientid = "logged_in_clientid";
    static final String pref_firstame = "logged_in_firstname";
    static final String pref_lastname = "logged_in_lastname";
    static final String pref_email = "logged_in_email";
    static final String pref_phone = "logged_in_phone";
    static final String pref_address = "logged_in_address";
    static final String pref_profilepic = "logged_in_profilepic";
    static final String pref_status_client = "logged_in_status_client";

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

    public static void setLoggedInAddress(Context ctx, String aboutme)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_address);
        editor.putString(pref_address, aboutme);
        editor.commit();
    }
    public static void setLoggedInProfilePicture(Context ctx, String profilepic)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_profilepic);
        editor.putString(pref_profilepic, profilepic);
        editor.commit();
    }

    //    getting shared preferences
    public static String getLoggedInClientid(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_clientid, "");
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
    public static String getLoggedInAddress(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_address, "");
    }
    public static String getLoggedInProfilePicture(Context ctx)
    {
        return getSharedPreferences(ctx).getString(pref_profilepic, "");
    }


    //  status
    public static void setUserLoggedInStatus(Context ctx, boolean status)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_status_client);
        editor.putBoolean(pref_status_client, status);
        editor.commit();
    }

    public static boolean getUserLoggedInStatus(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(pref_status_client, false);
    }

    //    clearing preferences
    public static void clearLoggedInClient(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(pref_clientid);
        editor.remove(pref_firstame);
        editor.remove(pref_lastname);
        editor.remove(pref_email);
        editor.remove(pref_phone);
        editor.remove(pref_address);
        editor.remove(pref_profilepic);
        editor.remove(pref_status_client);
        editor.commit();
    }
}
