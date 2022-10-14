// 预定信息对象
public class BookingInfo{
    // 用户ID
    String userID;
    // 汽车名称
    String carName;
    // 租赁数量
    Integer orderNums;
    // 开始时间
    String startTime;
    // 结束时间
    String endTime;

    public BookingInfo() {
    }

    public BookingInfo(String userID, String carName, Integer nums, String startTime, String endTime) {
        this.carName = carName;
        this.userID = userID;
        this.orderNums = nums;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Hello," + userID +
                ".You have successfully booked " + orderNums + " " + carName +
                " from " + startTime + " to " + endTime;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getNum() {
        return orderNums;
    }

    public void setNum(Integer nums) {
        this.orderNums = nums;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
