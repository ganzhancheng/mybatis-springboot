package com.winterchen.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winterchen.ExcelUtil;
import com.winterchen.HttpUtil;
import com.winterchen.dao.LotteryMapper;
import com.winterchen.dao.MoneyMapper;
import com.winterchen.model.Lottery;
import com.winterchen.model.LotteryExample;
import com.winterchen.model.Money;
import com.winterchen.model.MoneyExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//@EnableScheduling
@RestController
@RequestMapping("lottery")
public class LotteryController {

    @Autowired
    LotteryMapper lotteryMapper;

    @Autowired
    MoneyMapper moneyMapper;

    static int count1;

    @RequestMapping("get")
    public List<Lottery> get(Integer count) {
        if (count != null) {
            count1 = count;
        }else{
            count1 -= 1;
        }
        List<Lottery> lotteries = lotteryMapper.selectLottery(count1);
        return lotteries;
    }

    @RequestMapping("bet")
    public Money bet(String bet,String result){
        PageHelper.startPage(1, 1);
        MoneyExample example = new MoneyExample();
        example.setOrderByClause("id desc");
        List<Money> monies = moneyMapper.selectByExample(example);
        Money money = monies.get(0);

        Double money1 = money.getMoney();
        Double amount = money.getAmount();
        if (!money.getBet().equals(money.getResult())) {
            amount = 3 * amount;
        }else {
            amount = 1D;
        }
        if (bet.equals(result)) {
            money1 += amount * 0.97;
        }else{
            money1 -= amount;
        }

        money.setBet(bet);
        money.setResult(result);
        money.setMoney(money1);
        money.setAmount(amount);
        money.setId(null);
        moneyMapper.insert(money);

        return money;



    }


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
        lotteryExample.setOrderByClause("issue_no asc");
        if (StringUtils.isNotBlank(date)) {
            lotteryExample.createCriteria().andIssueNoLike("%"+date+"%");
        }
        List<Lottery> lotteries = lotteryMapper.selectByExample(lotteryExample);
        ArrayList<Object> list = new ArrayList<>();
        List<List> arrayList = new ArrayList<>();
        LinkedList<String> link = new LinkedList<>();

        int i =1;
        for (Lottery lottery : lotteries) {
            if ("1".equals(type) && ("单".equals(value) || "双".equals(value))) {
                if (lottery.getOdd().equals(value)) {
                    list.add(value);
                    if (i == lotteries.size()) {
                        arrayList.add(list);
                    }
                }else{
                    arrayList.add(list);
                    list = new ArrayList<>();
                }
            }
            if ("1".equals(type) && ("大".equals(value) || "小".equals(value))) {
                if (lottery.getBig().equals(value)) {
                    list.add(value);
                    if (i == lotteries.size()) {
                        arrayList.add(list);
                    }
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
                    if (i == lotteries.size()) {
                        arrayList.add(list);
                    }
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
                    if (i == lotteries.size()) {
                        arrayList.add(list);
                    }
                }else {
                    arrayList.add(link);
                    link = new LinkedList<>();
                }
            }

            i++;
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




}
