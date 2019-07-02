package com.winterchen.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.winterchen.ExcelUtil;
import com.winterchen.HttpUtil;
import com.winterchen.dao.LotteryMapper;
import com.winterchen.model.Lottery;
import com.winterchen.model.LotteryExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//@EnableScheduling
@RestController
@RequestMapping("lottery")
public class LotteryController {

    @Autowired
    LotteryMapper lotteryMapper;

    @RequestMapping("import")
    public void importExcel(MultipartFile file) throws Exception {
        List<Lottery> lotteries = ExcelUtil.importData(file, Lottery.class);

        for (Lottery lottery : lotteries) {
            lottery.setIssueNo(lottery.getIssueNo().replace("期",""));
            String lotteryOpen= lottery.getLotteryOpen();
            String[] split = lotteryOpen.split(",");
            int total = 0;
            for (String s : split) {
                int i = Integer.parseInt(s);
                total = total + i;
            }

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

    @RequestMapping("cal")
    public Map<String,Object> cal(String value,String type,String date){
        LotteryExample lotteryExample = new LotteryExample();
        if (StringUtils.isNotBlank(date)) {
            lotteryExample.createCriteria().andIssueNoLike("%"+date+"%");
        }
        List<Lottery> lotteries = lotteryMapper.selectByExample(lotteryExample);
        ArrayList<Object> list = new ArrayList<>();
        List<List> arrayList = new ArrayList<>();
        LinkedList<String> link = new LinkedList<>();

        for (Lottery lottery : lotteries) {
            if ("1".equals(type) && ("单".equals(value) || "双".equals(value))) {
                if (lottery.getOdd().equals(value)) {
                    list.add(value);
                }else{
                    arrayList.add(list);
                    list = new ArrayList<>();
                }
            }
            if ("1".equals(type) && ("大".equals(value) || "小".equals(value))) {
                if (lottery.getBig().equals(value)) {
                    list.add(value);
                }else{
                    arrayList.add(list);
                    list = new ArrayList<>();
                }
            }


            if ("2".equals(type) && ("单".equals(value) || "双".equals(value))) {
                String odd = lottery.getOdd();
                String last = "";
                if (link.size() > 0) {
                    last = link.getLast();
                }

                if (!odd.equals(last)) {
                    link.add(odd);
                }else {
                    arrayList.add(link);
                    link = new LinkedList<>();
                }


            }

            if ("2".equals(type) && ("大".equals(value) || "小".equals(value))) {
                String big = lottery.getBig();
                String last = "";
                if (link.size() > 0) {
                    last = link.getLast();
                }

                if (!big.equals(last)) {
                    link.add(big);
                }else {
                    arrayList.add(link);
                    link = new LinkedList<>();
                }


            }
        }

        HashMap<List, Integer> map = new HashMap<>();
        int top = 0;
        for (List o : arrayList) {
            if (o.size() > top) {
                top = o.size();
            }
            Integer integer = map.get(o);
            if (integer == null) {
                integer = 1;
                map.put(o, integer);
            }else{
                integer = integer + 1;
                map.put(o, integer);
            }
        }
        int sum = 0;
        ArrayList<Object> arrayList1 = new ArrayList<>();
        for (Map.Entry<List, Integer> e : map.entrySet()) {
            HashMap<Object, Object> map1 = new HashMap<>();
            List key = e.getKey();
            Integer value1 = e.getValue();
            map1.put("key", key.toString());
            map1.put("size", key.size());
            map1.put("count", value1);
            arrayList1.add(map1);
            int count = key.size() * value1;
            sum += count;
        }

        System.out.println(map);
        System.out.println(top);
        HashMap<String, Object> result = new HashMap<>();
        result.put("top", top);
        result.put("map", arrayList1);
        return result;
    }


    //@Scheduled(fixedRate = 600000)
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
        map.put("isNew","e13cd72d-d276-4d83-baa1-81d58c5123fde");

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
