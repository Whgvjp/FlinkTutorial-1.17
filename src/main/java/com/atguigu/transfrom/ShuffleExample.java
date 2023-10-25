package com.atguigu.transfrom;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class ShuffleExample {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		 env.setParallelism(2);

        DataStreamSource<String> stream = env.socketTextStream("localhost", 7777);;

//        stream.shuffle().print();
//        stream.rebalance().print();
//        stream.rescale().print();
        stream.global().print();

        env.execute();
    }
}