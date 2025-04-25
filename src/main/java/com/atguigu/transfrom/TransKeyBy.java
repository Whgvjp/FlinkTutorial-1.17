package com.atguigu.transfrom;

import com.atguigu.bean.WaterSensor;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TransKeyBy {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<WaterSensor> stream = env.fromElements(
                new WaterSensor("sensor_1", 1L, 1),
                new WaterSensor("sensor_1", 2L, 2),
                new WaterSensor("sensor_2", 2L, 2),
                new WaterSensor("sensor_3", 3L, 3)
        );

        // 方式一：使用Lambda表达式
        KeyedStream<WaterSensor, String> keyedStream = stream.keyBy(e -> e.id);

        // 方式二：使用匿名类实现KeySelector
        KeyedStream<WaterSensor, String> keyedStream1 = stream.keyBy(new KeySelector<WaterSensor, String>() {
            @Override
            public String getKey(WaterSensor e) throws Exception {
                return e.id;
            }
        });

//        keyedStream.print();

        SingleOutputStreamOperator<WaterSensor> result = keyedStream.sum("ts");
        SingleOutputStreamOperator<WaterSensor> result1 = keyedStream.reduce(new ReduceFunction<WaterSensor>() {
            @Override
            public WaterSensor reduce(WaterSensor value1, WaterSensor value2) throws Exception {
                return new WaterSensor(value1.id, value1.ts + value2.ts, value1.vc + value2.vc);
            }
        });
        // 将流命名或者加uid控制台都无法分辨出到底是哪个流的打印结果
        result.print().name("R1").uid("RR1");

        result1.print().name("R2").uid("RR2");

        env.execute();
    }
}
