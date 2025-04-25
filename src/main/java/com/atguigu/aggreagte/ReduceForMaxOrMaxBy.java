package com.atguigu.aggreagte;

import com.atguigu.bean.WaterSensor;
import com.atguigu.functions.WaterSensorMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class ReduceForMaxOrMaxBy {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env
                .socketTextStream("localhost", 7777)
                .map(new WaterSensorMapFunction())
                .keyBy(WaterSensor::getId)
                .reduce(new ReduceFunction<WaterSensor>() {
                    @Override
                    public WaterSensor reduce(WaterSensor value1, WaterSensor value2) throws Exception {
                        System.out.println("Demo7_Reduce.reduce");

                        int maxVc = Math.max(value1.getVc(), value2.getVc());
                        //实现max(vc)的效果  取最大值，其他字段以当前组的第一个为主
                        //value1.setVc(maxVc);
                        //实现maxBy(vc)的效果  取当前最大值的所有字段
                        if (value1.getVc() > value2.getVc()) {
                            value1.setVc(maxVc);        // 这行应该不加也行，问了两个AI都这么说
                            return value1;
                        } else {
                            value2.setVc(maxVc);
                            return value2;
                        }
                    }
                })
                .print();
        env.execute();
    }
}
