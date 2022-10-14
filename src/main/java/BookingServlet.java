import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    // 此处用Map和List来模拟数据库以及后面关于库存的查询和插入
    private static final int SUCCESS = 0;
    private static final int FAIL = 1;
    private static List<BookingInfo> orderList = new ArrayList<>();
    private static Map<String, Integer> stockMap = new HashMap<>();
    static{
        stockMap.put("Toyota Camry",2);
        stockMap.put("BMW 650",2);
    }

    // 初始化信息
    private String title;
    private String content;
    public void init() throws ServletException{
        title = "Car rental booking";
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>"+title+"</h1>");

        // 处理预定请求
        try {
            BookingInfo bookingInfo = new BookingInfo();
            BookingReponse bookingReponse = checkAndFillParam(request, bookingInfo);
            if (bookingReponse == null) {
                bookingReponse = bookingService(bookingInfo);
            }

            // 打印结果信息和当前的库存数
            content = bookingReponse.toString();
            out.println("<body>Current stock</body></br>");
            out.println("<body>"+"Toyota Camry's stock:"+ stockMap.get("Toyota Camry") + "</br></body>");
            out.println("<body>"+"BMW 650's stock:"+ stockMap.get("BMW 650") + "</br></body></br>");
            out.println("<body>"+ content +"</body>");
        } catch (Exception e) {
            out.println("<body>Internal Server ERROR</body>");
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

    // 业务逻辑处理，
    // 关于返回的错误码，实际情况应该加以区分：比如100001对应参数xx缺失，200001对应库存不足等，然后前端根据不同的错误码显示提示信息
    // 这里因为已经返回了明确的提示信息，所以为了简约，code只分为0和1（0-成功 ; 1-失败）
    public BookingReponse bookingService(BookingInfo bookingInfo) throws Exception{
        // 1、逻辑判断
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Long startTime = sim.parse(bookingInfo.startTime).getTime();
        Long endTime = sim.parse(bookingInfo.endTime).getTime();
        if (startTime < System.currentTimeMillis()){
            return new BookingReponse(BookingServlet.FAIL, "Booking failed. The current time should be earlier than the start time.");
        }

        if (endTime < startTime){
            return new BookingReponse(BookingServlet.FAIL, "Booking failed. The start time should be earlier than the end time.");
        }

        if (stockMap.get(bookingInfo.getCarName()) == null){
            return new BookingReponse(BookingServlet.FAIL, "Booking failed. No such car.");
        }

        if (bookingInfo.orderNums <= 0) {
            return new BookingReponse(BookingServlet.FAIL, "Booking failed. The number of car rentals should be greater than 0.");
        }

        // 3、业务处理
        // 避免并发引起超卖问题
        synchronized (BookingServlet.class) {
            Integer rest = stockMap.get(bookingInfo.getCarName()) - bookingInfo.getNum();
            if (rest < 0) {
                return new BookingReponse(BookingServlet.FAIL,"Booking failed. there is not enough stock.");
            }
            // 更新库存，用map模拟更新数据库
            stockMap.put(bookingInfo.getCarName(), rest);
            // 将该条成功的订单插入数据库，用list模拟
            orderList.add(bookingInfo);
        }
        // 返回成功并展示预定数据
        return new BookingReponse(BookingServlet.SUCCESS, "Booking successful! " + bookingInfo.toString());
    }

    // 校验并且填充参数
    public BookingReponse checkAndFillParam(HttpServletRequest request, BookingInfo bookingRequest) throws Exception {
        // 参数为空校验（为了轻量没有引入框架和工具类，只做了简单的为空校验，
        // 其实还应该对输入的数字num的范围和时间格式做校验）
        if (request.getParameter("userID")==null || request.getParameter("userID") == ""){
            return new BookingReponse(BookingServlet.FAIL, "Booking failed. Missing userID parameter");
        }
        if (request.getParameter("carName")==null || request.getParameter("carName") == ""){
            return new BookingReponse(BookingServlet.FAIL, "Booking failed. Missing carName parameter");
        }
        if (request.getParameter("num")==null || request.getParameter("num") == ""){
            return new BookingReponse(BookingServlet.FAIL,"Booking failed. Missing num parameter");
        }
        if (request.getParameter("startTime")==null || request.getParameter("startTime")==""){
            return new BookingReponse(BookingServlet.FAIL,"Booking failed. Missing startTime parameter");
        }
        if (request.getParameter("endTime")==null || request.getParameter("endTime")==""){
            return new BookingReponse(BookingServlet.FAIL,"Booking failed. Missing endTime parameter");
        }

        // 对象赋值.将有可能的异常向上抛出由上层捕获
        bookingRequest.setUserID(request.getParameter("userID"));
        bookingRequest.setCarName(request.getParameter("carName"));
        bookingRequest.setNum(Integer.valueOf(request.getParameter("num")));
        bookingRequest.setStartTime(request.getParameter("startTime"));
        bookingRequest.setEndTime(request.getParameter("endTime"));
        return null;
    }
}
