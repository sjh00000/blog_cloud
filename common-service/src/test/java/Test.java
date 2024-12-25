import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        System.out.println(BigDecimal.ZERO);
    }

    private static  void buildPayTime(String req) {
        if (StrUtil.isNotBlank(req)) {
            String payTimeYear = req.substring(0, 4);
            System.out.println(payTimeYear);
            if (req.length() > 5) {
                String payTimeMonth = req.substring(5, 6);
                System.out.println(payTimeMonth);
            }
            if (req.length() > 7) {
                String payTimeDay = req.substring(7, 8);
                System.out.println(payTimeDay);
            }
        }
    }
}
