package helper;

import com.littlepay.helper.MoneyHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyHelperTest {

    @Test
    void shouldReturnDefaultAmountWhenGivenAmountIsNull() {
        String result = MoneyHelper.formatMoney(null);
        assertEquals("$0.00", result);
    }

    @Test
    void shouldConvertMoneyAmountToGivenFormat() {
        BigDecimal amount = BigDecimal.valueOf(1.236);
        String result = MoneyHelper.formatMoney(amount);
        assertEquals("$1.24", result);
    }
}
