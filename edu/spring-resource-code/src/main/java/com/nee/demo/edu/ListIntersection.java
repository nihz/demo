package com.nee.demo.edu;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
@ToString
@Slf4j
public class ListIntersection {


    public static void main(String[] args) {
        List<String> userIds = new ArrayList<>();
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
            int i = 0;
            int handleCoune = 0;
            for (String line : lines) {
                User user = new User(line);
                lines[i] = null;
                userMap.computeIfAbsent(user.getU_id(), k -> new ArrayList<>());
                userMap.get(user.getU_id()).add(user);
                i++;
                if (i % 10000 == 0) System.out.println(i);
                // if (i == 950000) break;
                if (userMap.get(user.getU_id()).size() > 1) handleCoune ++;
            }

            log.info("The number of wait to handle com.nee.user: {}", handleCoune);
            log.info("spent {} seconds load data", (System.currentTimeMillis() - startTime) / 1000);

            startTime = System.currentTimeMillis();
            log.info("start find listIntersection com.nee.user...");
            userMap.forEach((u_id, users) -> {
                if (listIntersection(u_id, users)) {
                    userIds.add(u_id);
                }
            });
            log.info("spent {} seconds find listIntersection com.nee.user", (System.currentTimeMillis() - startTime) / 1000);

            log.info("listIntersection com.nee.user size: {}", userIds.size());
            log.info("all com.nee.user size: {}", userMap.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static boolean listIntersection(String u_id, List<User> us) {

        if (us == null || us.size() <= 1) return false;

        log.info("compute u, u_id = {}", u_id);

        for (int i = 0; i < us.size() - 1; i++) {
            for (int j = i + 1; j < us.size(); j++) {

                String maxBegin = max(us.get(i).getBegin(), us.get(j).getBegin());
                String minEnd = min(us.get(i).getEnd(), us.get(j).getEnd());

                if (maxBegin .compareTo(minEnd) < 0)
                    return true;

            }
        }
        return false;
    }

    private static String max(String s1, String s2) {
        if (s1.compareTo(s2) < 0) return s2;
        if (s1.compareTo(s2) > 0) return s1;
        return s1;
    }

    private static String min(String s1, String s2) {
        if (s1.compareTo(s2) < 0) return s1;
        if (s1.compareTo(s2) > 0) return s2;
        return s1;
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
        begin = s[3];
        end = s[2];
    }
}
