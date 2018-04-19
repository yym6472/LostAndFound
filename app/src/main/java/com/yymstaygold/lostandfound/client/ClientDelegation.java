package com.yymstaygold.lostandfound.client;

import android.graphics.Bitmap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yymstaygold.lostandfound.client.entity.Found;
import com.yymstaygold.lostandfound.client.entity.Lost;
import com.yymstaygold.lostandfound.client.util.match.MatchInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;

public class ClientDelegation {

    private static String getMD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check if the user's phone number and password match or not.
     * @param phoneNumber the input phone number
     * @param password the input plain password
     * @return the user id of user, or -1 if the phoneNumber or password not exists
     */
    public static int checkPassword(String phoneNumber, String password) {
        String urlString = "http://23.106.132.78/LostAndFoundServer/check_password";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(6000);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeUTF(phoneNumber);
            out.writeUTF(getMD5(password));
            out.flush();
            out.close();

            if (conn.getResponseCode() == 200) {
                DataInputStream in = new DataInputStream(conn.getInputStream());
                int userId = in.readInt();
                conn.disconnect();
                return userId;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Register phone number and password to server.
     * @param phoneNumber the input phone number
     * @param password the input plain password
     * @return true if register succeed, false otherwise.
     */
    public static boolean register(String phoneNumber, String password) {
        String urlString = "http://23.106.132.78/LostAndFoundServer/register";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(6000);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeUTF(phoneNumber);
            out.writeUTF(getMD5(password));
            out.flush();
            out.close();

            if (conn.getResponseCode() == 200) {
                DataInputStream in = new DataInputStream(conn.getInputStream());
                boolean result = in.readBoolean();
                conn.disconnect();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String uploadImage(Bitmap image) {
        // TODO: implement
        return null;
    }

    public static void uploadFound(Found found) {
        String urlString = "http://23.106.132.78/LostAndFoundServer/upload_found";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(6000);
            OutputStream out = conn.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, found);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> uploadLost(Lost lost) {

        String urlString = "http://23.106.132.78/LostAndFoundServer/upload_lost";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(6000);
            OutputStream out = conn.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, lost);
            out.flush();
            out.close();


            if (conn.getResponseCode() == 200) {
                DataInputStream in = new DataInputStream(conn.getInputStream());
                ArrayList<Integer> result = new ArrayList<>();
                int foundId;
                while ((foundId = in.readInt()) != -1) {
                    result.add(foundId);
                }
                conn.disconnect();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap downloadImage(String imagePath) {
        // TODO: implement
        return null;
    }

    public static ArrayList<MatchInfo> checkNewFounds(int userId) {
        String urlString = "http://23.106.132.78/LostAndFoundServer/check_new_founds";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(6000);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeInt(userId);
            out.flush();
            out.close();

            if (conn.getResponseCode() == 200) {
                DataInputStream in = new DataInputStream(conn.getInputStream());
                ArrayList<MatchInfo> result = new ArrayList<>();
                int lostId, foundId;
                while ((lostId = in.readInt()) != -1) {
                    foundId = in.readInt();
                    result.add(new MatchInfo(lostId, foundId));
                }
                conn.disconnect();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Found downloadFoundInfo(int foundId) {
        String urlString = "http://23.106.132.78/LostAndFoundServer/download_found_info";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(6000);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeInt(foundId);
            out.flush();
            out.close();

            if (conn.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                Found found = mapper.readValue(conn.getInputStream(), Found.class);
                conn.disconnect();
                return found;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        //System.out.println(checkPassword("13126609255", "zhunixingfu"));

        //System.out.println(register("13186879355", "zhunixingfu"));

    }
}
