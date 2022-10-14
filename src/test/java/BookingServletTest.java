import org.junit.Assert;
import org.junit.jupiter.api.Test;

// 主要针对逻辑处理方法bookingService进行测试,测试用例如下
class BookingServletTest {
    private static final Integer SUCCESS = 0;
    private static final Integer FAIL = 1;

    // 预定成功
    @Test
    public void bookingServerSuccess(){
        BookingServlet bookingServlet = new BookingServlet();
        BookingInfo bookingInfo = new BookingInfo("Mark","BMW 650",1,
                "2022-10-15 00:00:00", "2022-10-16 00:00:00");

        try {
            BookingReponse bookingReponse = bookingServlet.bookingService(bookingInfo);
            Assert.assertEquals(SUCCESS,bookingReponse.code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 库存不足
    @Test
    public void bookingServerStockIsNotEnough(){
        BookingServlet bookingServlet = new BookingServlet();
        BookingInfo bookingInfo = new BookingInfo("Mark","BMW 650",3,
                "2022-10-15 00:00:00", "2022-10-16 00:00:00");

        try {
            BookingReponse bookingReponse = bookingServlet.bookingService(bookingInfo);
            Assert.assertEquals(FAIL,bookingReponse.code);
            Assert.assertEquals("Booking failed. there is not enough stock.",bookingReponse.msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 输入的预定数量小于0
    @Test
    public void bookingServerNumLessThanZero(){
        BookingServlet bookingServlet = new BookingServlet();
        BookingInfo bookingInfo = new BookingInfo("Mark","BMW 650",0,
                "2022-10-15 00:00:00", "2022-10-16 00:00:00");
        try {
            BookingReponse bookingReponse = bookingServlet.bookingService(bookingInfo);
            Assert.assertEquals(FAIL,bookingReponse.code);
            Assert.assertEquals("Booking failed. The number of car rentals should be greater than 0.",bookingReponse.msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 没有对应车辆
    @Test
    public void bookingServerNoSuchCar(){
        BookingServlet bookingServlet = new BookingServlet();
        BookingInfo bookingInfo = new BookingInfo("Mark","ERRORCAR",0,
                "2022-10-15 00:00:00", "2022-10-16 00:00:00");
        try {
            BookingReponse bookingReponse = bookingServlet.bookingService(bookingInfo);
            Assert.assertEquals(FAIL,bookingReponse.code);
            Assert.assertEquals("Booking failed. No such car.",bookingReponse.msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 输入的开始时间早于当前时间
    @Test
    public void bookingServerStartTimeEarlyThanCurrentTime(){
        BookingServlet bookingServlet = new BookingServlet();
        BookingInfo bookingInfo = new BookingInfo("Mark","BMW 650",0,
                "2022-10-01 00:00:00", "2022-10-16 00:00:00");
        try {
            BookingReponse bookingReponse = bookingServlet.bookingService(bookingInfo);
            Assert.assertEquals(FAIL,bookingReponse.code);
            Assert.assertEquals("Booking failed. The current time should be earlier than the start time.",bookingReponse.msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 输入的结束时间早于开始时间
    @Test
    public void bookingServerEndTimeEarlyThanStartTime(){
        BookingServlet bookingServlet = new BookingServlet();
        BookingInfo bookingInfo = new BookingInfo("Mark","BMW 650",0,
                "2022-10-15 00:00:00", "2022-10-01 00:00:00");
        try {
            BookingReponse bookingReponse = bookingServlet.bookingService(bookingInfo);
            Assert.assertEquals(FAIL,bookingReponse.code);
            Assert.assertEquals("Booking failed. The start time should be earlier than the end time.",bookingReponse.msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}