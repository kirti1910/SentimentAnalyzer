package sentimentanalyzer.utils

public class CommonUtils {


    public static Map merge(Map[] sources) {
        if (sources.length == 0) return [:]
        if (sources.length == 1) return sources[0]

        sources.inject([:]) { result, source ->
            source.each { k, v ->
                result[k] = result[k] instanceof Map ? merge(result[k], v) : v
            }
            result
        }
    }

    def static average(List ls) {
        ls.sum() / ls.size()
    }

    public static String getOverallScore(Map msgList){
        Double sum = msgList.values().sum(){ Map map ->
            map.polarity
        }
        double avg=sum/msgList.size()
        if(avg > 0)
            return "Positive"
        else if(avg < 0){
            return "Negative"
        }
        else
            return "Neutral"
    }

    public static String getScore(Double polarity) {
        if (polarity == 0.0) {
            return "Neutral"
        } else if (polarity >= 0.7) {
            return "Excellent"
        } else if (polarity >= 0.5) {
            return "Very Good"
        } else if (polarity >= 0.2) {
            return "Good"
        } else if (polarity >= 0) {
            return "Not so Good"
        } else if (polarity >= -0.3) {
            return "Bad"
        } else if (polarity >= -0.4) {
            return "Very Bad"
        } else if (polarity >= -1) {
            return "Extremely Bad"
        } else
            return "Not Known"
    }
}
