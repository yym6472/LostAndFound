package com.yymstaygold.lostandfound.client;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yymstaygold.lostandfound.client.entity.Found;
import com.yymstaygold.lostandfound.client.entity.Lost;
import com.yymstaygold.lostandfound.client.util.match.MatchInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ClientDelegation {

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
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            mapper.writeValue(out, found);
            out.flush();
            out.close();
            mapper.writeValue(System.out, found);
            System.out.flush();
            Log.w("ClientDelegation", "Upload found message has sent");
            if (conn.getResponseCode() == 200) {
                conn.getInputStream().close();
            }
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
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
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
                mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                Found found = mapper.readValue(conn.getInputStream(), Found.class);
                conn.disconnect();
                return found;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Lost downloadLostInfo(int lostId) {
        String urlString = "http://23.106.132.78/LostAndFoundServer/download_lost_info";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(6000);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeInt(lostId);
            out.flush();
            out.close();

            if (conn.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                Lost lost = mapper.readValue(conn.getInputStream(), Lost.class);
                conn.disconnect();
                return lost;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Integer> checkUserLost(int userId) {
        String urlString = "http://23.106.132.78/LostAndFoundServer/check_user_lost";
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
                ArrayList<Integer> result = new ArrayList<>();
                DataInputStream in = new DataInputStream(conn.getInputStream());
                int lostId;
                while ((lostId = in.readInt()) != -1) {
                    result.add(lostId);
                }
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Integer> checkUserFound(int userId) {
        String urlString = "http://23.106.132.78/LostAndFoundServer/check_user_found";
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
                ArrayList<Integer> result = new ArrayList<>();
                DataInputStream in = new DataInputStream(conn.getInputStream());
                int foundId;
                while ((foundId = in.readInt()) != -1) {
                    result.add(foundId);
                }
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
