<%--
  Created by IntelliJ IDEA.
  User: 97131
  Date: 2022/10/11
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Car rental booking</title>
</head>
    <h1>Car rental booking</h1></br>
    <body>You can choose the following cars:</body></br>
    <body>Toyota Camry</body></br>
    <body>BMW 650</body></br>

    <form action="booking" method="post">
        </br>
        <table>
            <tr>
                <td>userID:</td>
                <td><input type="text" name="userID"/></td>
            </tr>
            <tr>
                <td>carName:</td>
                <td><input type="text" name="carName"/></td>
            </tr>
            <tr>
                <td>num:</td>
                <td><input type="number" name="num"/></td>
            </tr>
            <tr>
                <td>startTime:</td>
                <td><input type="text" name="startTime"/></td>
            </tr>
            <tr>
                <td>endTime:</td>
                <td><input type="text" name="endTime"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="submit"/></td>
                <td><input type="reset" value="reset"/></td>
            </tr>
        </table>
    </form>
</body>
</html>
