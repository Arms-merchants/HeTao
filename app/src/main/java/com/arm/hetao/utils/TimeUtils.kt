package com.arm.hetao.utils

import android.annotation.SuppressLint
import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * timeMillis  : 2016/8/2
 * desc  : 时间相关工具类
</pre> *
 */
object TimeUtils {


    /**
     *
     * 在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.
     * 格式的意义如下： 日期和时间模式 <br></br>
     *
     * 日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
     * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
     * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
     *
     * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br></br> <table border="1" cellspacing="1" cellpadding="1" summary="Chart shows pattern letters, date/timeMillis component, presentation,
    and examples.">
     * <tr>
     * <th align="left">字母</th>
     * <th align="left">日期或时间元素</th>
     * <th align="left">表示</th>
     * <th align="left">示例</th>
    </tr> *
     * <tr>
     * <td>`G`</td>
     * <td>Era 标志符</td>
     * <td>Text</td>
     * <td>`AD`</td>
    </tr> *
     * <tr>
     * <td>`y` </td>
     * <td>年 </td>
     * <td>Year </td>
     * <td>`1996`; `ic_launcher` </td>
    </tr> *
     * <tr>
     * <td>`M` </td>
     * <td>年中的月份 </td>
     * <td>Month </td>
     * <td>`July`; `Jul`; `07` </td>
    </tr> *
     * <tr>
     * <td>`w` </td>
     * <td>年中的周数 </td>
     * <td>Number </td>
     * <td>`27` </td>
    </tr> *
     * <tr>
     * <td>`W` </td>
     * <td>月份中的周数 </td>
     * <td>Number </td>
     * <td>`2` </td>
    </tr> *
     * <tr>
     * <td>`D` </td>
     * <td>年中的天数 </td>
     * <td>Number </td>
     * <td>`189` </td>
    </tr> *
     * <tr>
     * <td>`d` </td>
     * <td>月份中的天数 </td>
     * <td>Number </td>
     * <td>`10` </td>
    </tr> *
     * <tr>
     * <td>`F` </td>
     * <td>月份中的星期 </td>
     * <td>Number </td>
     * <td>`2` </td>
    </tr> *
     * <tr>
     * <td>`E` </td>
     * <td>星期中的天数 </td>
     * <td>Text </td>
     * <td>`Tuesday`; `Tue` </td>
    </tr> *
     * <tr>
     * <td>`a` </td>
     * <td>Am/pm 标记 </td>
     * <td>Text </td>
     * <td>`PM` </td>
    </tr> *
     * <tr>
     * <td>`H` </td>
     * <td>一天中的小时数（0-23） </td>
     * <td>Number </td>
     * <td>`0` </td>
    </tr> *
     * <tr>
     * <td>`k` </td>
     * <td>一天中的小时数（1-24） </td>
     * <td>Number </td>
     * <td>`24` </td>
    </tr> *
     * <tr>
     * <td>`K` </td>
     * <td>am/pm 中的小时数（0-11） </td>
     * <td>Number </td>
     * <td>`0` </td>
    </tr> *
     * <tr>
     * <td>`h` </td>
     * <td>am/pm 中的小时数（1-12） </td>
     * <td>Number </td>
     * <td>`12` </td>
    </tr> *
     * <tr>
     * <td>`m` </td>
     * <td>小时中的分钟数 </td>
     * <td>Number </td>
     * <td>`30` </td>
    </tr> *
     * <tr>
     * <td>`s` </td>
     * <td>分钟中的秒数 </td>
     * <td>Number </td>
     * <td>`55` </td>
    </tr> *
     * <tr>
     * <td>`S` </td>
     * <td>毫秒数 </td>
     * <td>Number </td>
     * <td>`978` </td>
    </tr> *
     * <tr>
     * <td>`z` </td>
     * <td>时区 </td>
     * <td>General timeMillis zone </td>
     * <td>`Pacific Standard Time`; `PST`; `GMT-08:00` </td>
    </tr> *
     * <tr>
     * <td>`Z` </td>
     * <td>时区 </td>
     * <td>RFC 822 timeMillis zone </td>
     * <td>`-0800` </td>
    </tr> *
    </table> *
     * <pre>
     * HH:mm    15:44
     * h:mm a    3:44 下午
     * HH:mm z    15:44 CST
     * HH:mm Z    15:44 +0800
     * HH:mm zzzz    15:44 中国标准时间
     * HH:mm:ss    15:44:40
     * yyyy-MM-dd    2016-08-12
     * yyyy-MM-dd HH:mm    2016-08-12 15:44
     * yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     * yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     * EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     * yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     * K:mm a    3:44 下午
     * EEE, MMM d, ''yy    星期五, 八月 12, '16
     * hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     * yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     * EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     * yyMMddHHmmssZ    160812154440+0800
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
    </pre> *
     * 注意：SimpleDateFormat不是线程安全的，线程安全需用`ThreadLocal<SimpleDateFormat>`
     */
    const val DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss"
    private const val SECONDS_IN_DAY = 60 * 60 * 24
    private const val MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY
    private const val ONE_M = (60 * 1000).toLong()
    private const val ONE_HOUR = 60 * ONE_M
    private const val HALF_HOUR = ONE_HOUR / 2
    private const val ONE_DAY = 24 * ONE_HOUR

    /**
     * 将时间戳转为时间字符串
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    fun millis2String(millis: Long): String {
        return SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).format(Date(millis))
    }

    /**
     * 将时间戳转为时间字符串
     *
     * 格式为pattern
     *
     * @param millis 毫秒时间戳
     * @param pattern 时间格式
     * @return 时间字符串
     */
    fun millis2String(millis: Long, pattern: String?): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(millis))
    }
    /**
     * 将时间字符串转为时间戳
     *
     * time格式为pattern
     *
     * @param time 时间字符串
     * @param pattern 时间格式
     * @return 毫秒时间戳
     */
    /**
     * 将时间字符串转为时间戳
     *
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    @JvmOverloads
    fun string2Millis(time: String?, pattern: String? = DEFAULT_PATTERN): Long {
        try {
            return SimpleDateFormat(pattern, Locale.getDefault()).parse(time).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return -1
    }
    /**
     * 将时间字符串转为Date类型
     *
     * time格式为pattern
     *
     * @param time 时间字符串
     * @param pattern 时间格式
     * @return Date类型
     */
    /**
     * 将时间字符串转为Date类型
     *
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return Date类型
     */
    @JvmOverloads
    fun string2Date(time: String?, pattern: String? = DEFAULT_PATTERN): Date {
        return Date(string2Millis(time, pattern))
    }
    /**
     * 将Date类型转为时间字符串
     *
     * 格式为pattern
     *
     * @param date Date类型时间
     * @param pattern 时间格式
     * @return 时间字符串
     */
    /**
     * 将Date类型转为时间字符串
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @param date Date类型时间
     * @return 时间字符串
     */
    @JvmOverloads
    fun date2String(date: Date?, pattern: String? = DEFAULT_PATTERN): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param date Date类型时间
     * @return 毫秒时间戳
     */
    fun date2Millis(date: Date): Long {
        return date.time
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param millis 毫秒时间戳
     * @return Date类型时间
     */
    fun millis2Date(millis: Long): Date {
        return Date(millis)
    }

    /**
     * 获取合适型两个时间差
     */
    fun getLongTimeSpan(time0: String?, time1: String?, pattern: String?): Long {
        return string2Millis(time0, pattern) - string2Millis(time1, pattern)
    }

    /**
     * 获取当前毫秒时间戳
     *
     * @return 毫秒时间戳
     */
    val nowTimeMills: Long
        get() = System.currentTimeMillis()

    /**
     * 获取当前时间字符串
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @return 时间字符串
     */
    val nowTimeString: String
        get() = millis2String(System.currentTimeMillis(), DEFAULT_PATTERN)

    /**
     * 获取当前时间字符串
     *
     * 格式为pattern
     *
     * @param pattern 时间格式
     * @return 时间字符串
     */
    fun getNowTimeString(pattern: String?): String {
        return millis2String(System.currentTimeMillis(), pattern)
    }

    /**
     * 获取当前Date
     *
     * @return Date类型时间
     */
    val nowTimeDate: Date
        get() = Date()

    fun getLongTimeSpanByNow(time: String?, pattern: String?): Long {
        return getLongTimeSpan(nowTimeString, time, pattern)
    }

    /**
     * 1429847795116L==》2015-4-24
     */
    fun mi2riqi(mi: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date(mi))
    }

    /**
     * 2015-4-24 11:55:16==》1429858516000L
     */
    fun date2mi(date: String?): Long {
        if (date == null) {
            return System.currentTimeMillis()
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        try {
            val parse = sdf.parse(date)
            return parse.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return -1
    }

    /**
     * 获取友好型与当前时间的差
     *
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 友好型与当前时间的差
     *
     *  * 如果小于1秒钟内，显示刚刚
     *  * 如果在1分钟内，显示XXX秒前
     *  * 如果在1小时内，显示XXX分钟前
     *  * 如果在1小时外的今天内，显示今天15:32
     *  * 如果是昨天的，显示昨天15:32
     *  * 其余显示，2016-10-15
     *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     *
     */
    fun getFriendlyTimeSpanByNow(time: String?): String {
        return getFriendlyTimeSpanByNow(time, DEFAULT_PATTERN)
    }

    /**
     * 获取友好型与当前时间的差
     *
     * time格式为pattern
     *
     * @param time 时间字符串
     * @param pattern 时间格式
     * @return 友好型与当前时间的差
     *
     *  * 如果小于1秒钟内，显示刚刚
     *  * 如果在1分钟内，显示XXX秒前
     *  * 如果在1小时内，显示XXX分钟前
     *  * 如果在1小时外的今天内，显示今天15:32
     *  * 如果是昨天的，显示昨天15:32
     *  * 其余显示，2016-10-15
     *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     *
     */
    fun getFriendlyTimeSpanByNow(time: String?, pattern: String?): String {
        return getFriendlyTimeSpanByNow(string2Millis(time, pattern))
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param date Date类型时间
     * @return 友好型与当前时间的差
     *
     *  * 如果小于1秒钟内，显示刚刚
     *  * 如果在1分钟内，显示XXX秒前
     *  * 如果在1小时内，显示XXX分钟前
     *  * 如果在1小时外的今天内，显示今天15:32
     *  * 如果是昨天的，显示昨天15:32
     *  * 其余显示，2016-10-15
     *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     *
     */
    fun getFriendlyTimeSpanByNow(date: Date): String {
        return getFriendlyTimeSpanByNow(date.time)
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param millis 毫秒时间戳
     * @return 友好型与当前时间的差
     *
     *  * 如果小于1秒钟内，显示刚刚
     *  * 如果在1分钟内，显示XXX秒前
     *  * 如果在1小时内，显示XXX分钟前
     *  * 如果在1小时外的今天内，显示今天15:32
     *  * 如果是昨天的，显示昨天15:32
     *  * 其余显示，2016-10-15
     *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     *
     */
    @SuppressLint("DefaultLocale")
    fun getFriendlyTimeSpanByNow(millis: Long): String {
        val now = System.currentTimeMillis()
        val span = now - millis
        if (span < 0) {
            return String.format(
                "%tc",
                millis
            ) // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
        }
        if (span < 1000) {
            return "刚刚"
        } else if (span < 60000) {
            return String.format("%d秒前", span / 1000)
        } else if (span < 3600000) {
            return String.format("%d分钟前", span / 60000)
        }
        // 获取当天00:00
        val wee = now / 86400000 * 86400000
        return if (millis >= wee) {
            String.format("今天%tR", millis)
        } else if (millis >= wee - 86400000) {
            String.format("昨天%tR", millis)
        } else {
            String.format("%tF", millis)
        }
    }

    fun getStrByDate(time: String?): String? {
        if (TextUtils.isEmpty(time)) {
            return "未知"
        }
        val longTime = date2mi(time)
        val nowTime = System.currentTimeMillis()
        val currTime = nowTime - longTime
        if (currTime <= ONE_M) {
            return "刚刚"
        }
        if (ONE_M < currTime && currTime < ONE_HOUR) {
            return if (currTime == HALF_HOUR) {
                "半小时前"
            } else (currTime / ONE_M).toString() + "分钟前"
        }
        if (currTime >= ONE_HOUR && currTime < ONE_DAY) {
            return (currTime / ONE_HOUR).toString() + "小时前"
        }
        if (currTime >= ONE_DAY && currTime <= 3 * ONE_DAY) {
            val d = (currTime / ONE_DAY).toInt()
            return if (d <= 1) {
                "1天前"
            } else (currTime / ONE_DAY).toString() + "天前"
        }
        return if (currTime > 3 * ONE_DAY) {
            mi2riqi(longTime)
        } else time
    }

    fun getStrByMitime(longTime: Long): String {
        val nowTime = System.currentTimeMillis()
        val currTime = nowTime - longTime
        if (currTime <= ONE_M) {
            return "刚刚"
        }
        if (ONE_M < currTime && currTime < ONE_HOUR) {
//            if (currTime == HALF_HOUR) {
//                return "半小时前";
//            }
            return (currTime / ONE_M).toString() + "分钟前"
        }
        if (currTime >= ONE_HOUR && currTime < ONE_DAY) {
            return (currTime / ONE_HOUR).toString() + "小时前"
        }
        if (currTime >= ONE_DAY && currTime <= 4 * ONE_DAY) {
//            int d = (int) (currTime / ONE_DAY);
//            if (d <= 1) {
//                return "昨天";
//            }
            return (currTime / ONE_DAY).toString() + "天前"
        }
        return if (currTime > 4 * ONE_DAY) {
            mi2riqi(longTime)
        } else "未知"
    }

    /**
     * 判断是否闰年
     *
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return `true`: 闰年<br></br>`false`: 平年
     */
    fun isLeapYear(time: String?): Boolean {
        return isLeapYear(string2Date(time, DEFAULT_PATTERN))
    }

    /**
     * 判断是否闰年
     *
     * time格式为pattern
     *
     * @param time 时间字符串
     * @param pattern 时间格式
     * @return `true`: 闰年<br></br>`false`: 平年
     */
    fun isLeapYear(time: String?, pattern: String?): Boolean {
        return isLeapYear(string2Date(time, pattern))
    }

    /**
     * 判断是否闰年
     *
     * @param date Date类型时间
     * @return `true`: 闰年<br></br>`false`: 平年
     */
    fun isLeapYear(date: Date?): Boolean {
        val cal = Calendar.getInstance()
        cal.time = date
        val year = cal[Calendar.YEAR]
        return isLeapYear(year)
    }

    /**
     * 判断是否闰年
     *
     * @param millis 毫秒时间戳
     * @return `true`: 闰年<br></br>`false`: 平年
     */
    fun isLeapYear(millis: Long): Boolean {
        return isLeapYear(millis2Date(millis))
    }

    /**
     * 判断是否闰年
     *
     * @param year 年份
     * @return `true`: 闰年<br></br>`false`: 平年
     */
    fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    /**
     * 获取星期
     *
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 星期
     */
    fun getWeek(time: String?): String {
        return getWeek(string2Date(time, DEFAULT_PATTERN))
    }

    /**
     * 获取星期
     *
     * time格式为pattern
     *
     * @param time 时间字符串
     * @param pattern 时间格式
     * @return 星期
     */
    fun getWeek(time: String?, pattern: String?): String {
        return getWeek(string2Date(time, pattern))
    }

    /**
     * 获取星期
     *
     * @param date Date类型时间
     * @return 星期
     */
    fun getWeek(date: Date?): String {
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
    }

    /**
     * 获取星期
     *
     * @param millis 毫秒时间戳
     * @return 星期
     */
    fun getWeek(millis: Long): String {
        return getWeek(Date(millis))
    }

    /**
     * 获取星期
     *
     * 注意：周日的Index才是1，周六为7
     *
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 1...5
     */
    fun getWeekIndex(time: String?): Int {
        return getWeekIndex(string2Date(time, DEFAULT_PATTERN))
    }

    /**
     * 获取星期
     *
     * 注意：周日的Index才是1，周六为7
     *
     * time格式为pattern
     *
     * @param time 时间字符串
     * @param pattern 时间格式
     * @return 1...7
     */
    fun getWeekIndex(time: String?, pattern: String?): Int {
        return getWeekIndex(string2Date(time, pattern))
    }

    /**
     * 获取星期
     *
     * 注意：周日的Index才是1，周六为7
     *
     * @param date Date类型时间
     * @return 1...7
     */
    fun getWeekIndex(date: Date?): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal[Calendar.DAY_OF_WEEK]
    }

    /**
     * 获取星期
     *
     * 注意：周日的Index才是1，周六为7
     *
     * @param millis 毫秒时间戳
     * @return 1...7
     */
    fun getWeekIndex(millis: Long): Int {
        return getWeekIndex(millis2Date(millis))
    }

    /**
     * 获取月份中的第几周
     *
     * 注意：国外周日才是新的一周的开始
     *
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 1...5
     */
    fun getWeekOfMonth(time: String?): Int {
        return getWeekOfMonth(string2Date(time, DEFAULT_PATTERN))
    }

    /**
     * 获取月份中的第几周
     *
     * 注意：国外周日才是新的一周的开始
     *
     * time格式为pattern
     *
     * @param time 时间字符串
     * @param pattern 时间格式
     * @return 1...5
     */
    fun getWeekOfMonth(time: String?, pattern: String?): Int {
        return getWeekOfMonth(string2Date(time, pattern))
    }

    /**
     * 获取月份中的第几周
     *
     * 注意：国外周日才是新的一周的开始
     *
     * @param date Date类型时间
     * @return 1...5
     */
    fun getWeekOfMonth(date: Date?): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal[Calendar.WEEK_OF_MONTH]
    }

    /**
     * 获取月份中的第几周
     *
     * 注意：国外周日才是新的一周的开始
     *
     * @param millis 毫秒时间戳
     * @return 1...5
     */
    fun getWeekOfMonth(millis: Long): Int {
        return getWeekOfMonth(millis2Date(millis))
    }

    /**
     * 获取年份中的第几周
     *
     * 注意：国外周日才是新的一周的开始
     *
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 1...54
     */
    fun getWeekOfYear(time: String?): Int {
        return getWeekOfYear(string2Date(time, DEFAULT_PATTERN))
    }

    /**
     * 获取年份中的第几周
     *
     * 注意：国外周日才是新的一周的开始
     *
     * time格式为pattern
     *
     * @param time 时间字符串
     * @param pattern 时间格式
     * @return 1...54
     */
    fun getWeekOfYear(time: String?, pattern: String?): Int {
        return getWeekOfYear(string2Date(time, pattern))
    }

    /**
     * 获取年份中的第几周
     *
     * 注意：国外周日才是新的一周的开始
     *
     * @param date Date类型时间
     * @return 1...54
     */
    fun getWeekOfYear(date: Date?): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal[Calendar.WEEK_OF_YEAR]
    }

    /**
     * 获取年份中的第几周
     *
     * 注意：国外周日才是新的一周的开始
     *
     * @param millis 毫秒时间戳
     * @return 1...54
     */
    fun getWeekOfYear(millis: Long): Int {
        return getWeekOfYear(millis2Date(millis))
    }

    private val CHINESE_ZODIAC = arrayOf(
        "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔",
        "龙", "蛇", "马", "羊"
    )

    /**
     * 获取生肖
     *
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 生肖
     */
    fun getChineseZodiac(time: String?): String {
        return getChineseZodiac(string2Date(time, DEFAULT_PATTERN))
    }

    /**
     * 获取生肖
     *
     * time格式为pattern
     *
     * @param time 时间字符串
     * @param pattern 时间格式
     * @return 生肖
     */
    fun getChineseZodiac(time: String?, pattern: String?): String {
        return getChineseZodiac(string2Date(time, pattern))
    }

    /**
     * 获取生肖
     *
     * @param date Date类型时间
     * @return 生肖
     */
    fun getChineseZodiac(date: Date?): String {
        val cal = Calendar.getInstance()
        cal.time = date
        return CHINESE_ZODIAC[cal[Calendar.YEAR] % 12]
    }

    /**
     * 获取生肖
     *
     * @param millis 毫秒时间戳
     * @return 生肖
     */
    fun getChineseZodiac(millis: Long): String {
        return getChineseZodiac(millis2Date(millis))
    }

    /**
     * 获取生肖
     *
     * @param year 年
     * @return 生肖
     */
    fun getChineseZodiac(year: Int): String {
        return CHINESE_ZODIAC[year % 12]
    }

    private val ZODIAC = arrayOf(
        "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座",
        "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"
    )
    private val ZODIAC_FLAGS = intArrayOf(20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22)

    /**
     * 获取星座
     *
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 生肖
     */
    fun getZodiac(time: String?): String {
        return getZodiac(string2Date(time, DEFAULT_PATTERN))
    }

    /**
     * 获取星座
     *
     * time格式为pattern
     *
     * @param time 时间字符串
     * @param pattern 时间格式
     * @return 生肖
     */
    fun getZodiac(time: String?, pattern: String?): String {
        return getZodiac(string2Date(time, pattern))
    }

    /**
     * 获取星座
     *
     * @param date Date类型时间
     * @return 星座
     */
    fun getZodiac(date: Date?): String {
        val cal = Calendar.getInstance()
        cal.time = date
        val month = cal[Calendar.MONTH] + 1
        val day = cal[Calendar.DAY_OF_MONTH]
        return getZodiac(month, day)
    }

    /**
     * 获取星座
     *
     * @param millis 毫秒时间戳
     * @return 星座
     */
    fun getZodiac(millis: Long): String {
        return getZodiac(millis2Date(millis))
    }

    /**
     * 获取星座
     *
     * @param month 月
     * @param day 日
     * @return 星座
     */
    fun getZodiac(month: Int, day: Int): String {
        return ZODIAC[if (day >= ZODIAC_FLAGS[month - 1]) month - 1 else (month + 10) % 12]
    }

    fun formatTwoPlace(value: Int): String {
        return if (value > 9) {
            value.toString() + ""
        } else {
            "0$value"
        }
    }

    /**
     * 获取包含的天数
     *
     * @param mills 秒
     * @return 天数
     */
    fun getDays(mills: Long): Int {
        return (mills / (60 * 60 * 24)).toInt()
    }

    /**
     * 获取包含小时数
     *
     * @param surplusMills 毫秒
     * @return 小时
     */
    fun getHourOfDay(surplusMills: Long): Int {
        return (surplusMills / (1000 * 60 * 60)).toInt()
    }

    /**
     * 获取除去小时后剩下的分钟
     *
     * @param surplusMills 毫秒
     * @return 分钟
     */
    fun getMinuteOfHour(surplusMills: Long): Int {
        return (surplusMills % (1000 * 60 * 60) / (1000 * 60)).toInt()
    }

    /**
     * 获取除去分钟后剩下的秒数
     *
     * @param surplusMills 毫秒
     * @return 分钟
     */
    fun getSecondOfMinute(surplusMills: Long): Int {
        return (surplusMills % (1000 * 60) / 1000).toInt()
    }

    /**
     * 获取当前dateStr的前几天，默认是前一天
     *
     * @param dateStr
     * @param days
     * @return
     */
    fun getPreviousDay(dateStr: String?, days: Int = -1): String? {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = dateFormat.parse(dateStr)
            calendar.time = date
            calendar.add(Calendar.DAY_OF_MONTH, days)
            val previousDay = calendar.time
            return dateFormat.format(previousDay)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getNextDay(dateStr: String?): String? {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = dateFormat.parse(dateStr)
            calendar.time = date
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            val nextDay = calendar.time
            return dateFormat.format(nextDay)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}