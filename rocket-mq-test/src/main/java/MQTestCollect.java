//import io.prometheus.client.*;
//import org.checkerframework.checker.units.qual.C;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//public class MQTestCollect extends Collector {
//
//    private static final List<String> PRODUCE_COUNT_LABEL_NAMES = Arrays.asList("producer", "topic");
//
//
//    private Long count = 0L;
//
//    private CounterMetricFamily produceCountF = new CounterMetricFamily("produce_count", "help", PRODUCE_COUNT_LABEL_NAMES);
//
//    public List<MetricFamilySamples> collect() {
//        List<MetricFamilySamples> mfs = new ArrayList<MetricFamilySamples>();
//        produceCountF.addMetric(Arrays.asList("producer","topic"), count);
//        mfs.add(Collections.singletonList(counter));
//        return mfs;
//    }
//
//
//    public void labelProduceCount(Long count) {
//        this.count = count;
//    }
//}