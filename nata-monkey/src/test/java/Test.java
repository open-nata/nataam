import org.apache.log4j.Logger;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-06 10:48
 */
public class Test {
    private static Logger logger = Logger.getLogger(Test.class.getName());
    public static void main(String[] args) {
//         System.out.println("This is println message.");

//        // 记录debug级别的信息
//        logger.debug("This is debug message.");
//        // 记录info级别的信息
//        logger.info("This is info message.");
//        // 记录error级别的信息
//        logger.error("This is error message.");

//        int startX = 56;
//        int startY = 795;
//        int endX = 298;
//        int endY = 1029;

        int startX = 298;
        int startY = 795;
        int endX = 540;
        int endY = 1029;

        String packageName = "com.cvicse.zhnt";
        String resourceId = "";
        String className = "android.widget.RelativeLayout";
        String enabled = "true";
        String checked = "false";
        String selected = "false";

        int hash = 17;
        hash = 31 * hash + startX;
        hash = 31 * hash + startY;
        hash = 31 * hash + endX;
        hash = 31 * hash + endY;
        hash = 31 * hash + packageName.hashCode();
        hash = 31 * hash + resourceId.hashCode();
        hash = 31 * hash + className.hashCode();
        hash = 31 * hash + enabled.hashCode();
        hash = 31 * hash + checked.hashCode();
        hash = 31 * hash + selected.hashCode();

        System.out.println(hash);
    }
}
