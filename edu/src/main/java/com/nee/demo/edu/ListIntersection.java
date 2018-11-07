package com.nee.demo.edu;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

import static jdk.nashorn.internal.objects.NativeMath.max;
import static jdk.nashorn.internal.objects.NativeMath.min;

@Data
@ToString
@Slf4j
public class ListIntersection {


    public static void main(String[] args) {
        List<String> userId = new ArrayList<>();
        Map<String, List<User>> userMap = new HashMap<>();
        try {
            Long startTime = System.currentTimeMillis();
            String fileName = "/Users/heikki/Documents/user_result/result.csv";
            String encoding = "UTF-8";
            File file = new File(fileName);
            Long filelength = file.length();
            byte[] filecontent = new byte[filelength.intValue()];
            try {
                FileInputStream in = new FileInputStream(file);
                in.read(filecontent);
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String content = new String(filecontent, encoding);
            filecontent = null;
            String[] lines = content.split("\n");
            content = null;
            for (String line : lines) {
                User user = new User(line);
                userMap.computeIfAbsent(user.getU_id(), k -> new ArrayList<>());
                userMap.get(user.getU_id()).add(user);
            }

            log.info("spent {} seconds load data", (System.currentTimeMillis() - startTime) / 1000);

            userMap.forEach((u_id, users) -> {
                if (listIntersection(users)) {
                    userId.add(u_id);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static boolean listIntersection(List<User> us) {
        for (int i = 0; i < us.size() - 1; i++) {
            for (int j = i + 1; j < us.size(); j++) {
                if (max(us.get(i).getBegin(), us.get(j).getBegin())
                        < min(us.get(i).getEnd(), us.get(j).getEnd()))
                    return true;

            }
        }
        return false;
    }


}

@Data
class User {
    private String u_id;
    private String ad;
    private String begin;
    private String end;

    User(String content) {
        String[] s = content.split(",");
        u_id = s[0];
        ad = s[1];
        begin = s[2];
        end = s[3];
    }
}
