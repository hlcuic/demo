package com.example.demo.dataservice.mysql;

import com.example.demo.dataservice.study.dao.TeamMapper;
import com.example.demo.dataservice.mysql.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestMysqlPerformance {

    @Autowired
    private TeamMapper mapper;


    // 单条循环插入1万条数据，耗时11158ms、11726ms、10706ms  1w条数据平均需要10s
    public void insert_1w(){
        new Thread(()->{
            long start = System.currentTimeMillis();
            for(int i=10000;i>0;i--){
                Team team = new Team();
                team.setCardId(i);
                team.setAge(i);
                team.setName("Lucy"+i);
                team.setHeight(i);
                team.setWeight(i);
                team.setAddress("sh");
                mapper.insertSelective(team);
            }
            long cost = System.currentTimeMillis()-start;
            System.out.println("插入1万条数据，耗时"+cost+"ms");
        }).start();

    }

    // 插入50万条数据，耗时528356ms,
    public void insert_50w(){
        new Thread(()->{
            long start = System.currentTimeMillis();
            for(int i=500000;i>0;i--){
                Team team = new Team();
                team.setCardId(i);
                team.setAge(i);
                team.setName("Lucy"+i);
                team.setHeight(i);
                team.setWeight(i);
                team.setAddress("sh");
                mapper.insertSelective(team);
            }
            long cost = System.currentTimeMillis()-start;
            System.out.println("插入1万条数据，耗时"+cost+"ms");
        }).start();

    }

}
