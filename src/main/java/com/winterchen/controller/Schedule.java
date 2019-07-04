package com.winterchen.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.winterchen.HttpUtil;
import com.winterchen.dao.LotteryMapper;
import com.winterchen.model.Lottery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
public class Schedule {
    @Autowired
    LotteryMapper lotteryMapper;
    @Scheduled(fixedRate = 600000)
    public void test() throws InterruptedException {
        HashMap<String, String> map = new HashMap<>();
        String[] isnew = {"da865604-8106-4f0f-b3af-d6daed05ffc1c","1328cef4-f2c2-4254-a427-b837acdfb519k","6455a472-1272-4c38-b836-5d69cd67e8b9k","e13cd72d-d276-4d83-baa1-81d58c5123fde"};
        Random random = new Random();
        int i1 = random.nextInt(isnew.length);

        map.put("Action", "GetLotteryOpen");
        map.put("LotteryCode", 1407+"");
        map.put("IssueNo", 0+"");
        map.put("DataNum", 20+"");
        map.put("SourceType", 1+"");
        map.put("isNew","f87b479f-13ff-4ad7-b00c-1b8a651ba763g");

        String post = HttpUtil.post("http://62zc.vip/api/GetLotteryOpen", map);
        JSONObject jsonObject = JSONObject.parseObject(post);
        JSONArray backData = jsonObject.getJSONArray("BackData");
        int j = 0;
        while (backData == null ) {
            TimeUnit.SECONDS.sleep(2);

            j++;
            map.put("isNew",isnew[random.nextInt(isnew.length)]);
            post = HttpUtil.post("http://62zc.vip/api/GetLotteryOpen", map);
            jsonObject = JSONObject.parseObject(post);
            backData = jsonObject.getJSONArray("BackData");
        }
        for (Object object : backData) {
            JSONObject jb = (JSONObject) object;
            String issueNo = jb.getString("IssueNo");
            String lotteryOpen = jb.getString("LotteryOpen");
            Date openTime = jb.getDate("OpenTime");

            String[] split = lotteryOpen.split(",");
            int total = 0;
            for (String s : split) {
                int i = Integer.parseInt(s);
                total = total + i;
            }

            Lottery lottery = new Lottery();
            lottery.setOpenTime(openTime);
            lottery.setIssueNo(issueNo);
            lottery.setLotteryOpen(lotteryOpen);
            if (total % 2 == 0) {
                lottery.setOdd("双");
            }else{
                lottery.setOdd("单");
            }
            if (total <= 10) {
                lottery.setBig("小");
            }else {
                lottery.setBig("大");
            }

            try {
                lotteryMapper.insert(lottery);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
