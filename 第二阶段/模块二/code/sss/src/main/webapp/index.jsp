<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登陆<%="http://"+request.getLocalAddr()+":"+request.getLocalPort()%></title>

    <style type="text/css">
        body {
            background-color:#00b38a;
            text-align:center;
        }

        .lp-login {
            position:absolute;
            width:500px;
            height:300px;
            top:50%;
            left:50%;
            margin-top:-250px;
            margin-left:-250px;
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 0 10px #12a591;
            padding: 57px 50px 35px;
            box-sizing: border-box
        }


        .lp-login .submitBtn {
            display:block;
            text-decoration:none;
            height: 48px;
            width: 150px;
            line-height: 48px;
            font-size: 16px;
            color: #fff;
            text-align: center;
            background-image: -webkit-gradient(linear, left top, right top, from(#09cb9d), to(#02b389));
            background-image: linear-gradient(90deg, #09cb9d, #02b389);
            border-radius: 3px
        }


        input[type='text'] {
            height:30px;
            width:250px;
        }
        input[type='password'] {
            height:30px;
            width:250px;
        }

        span {
            font-style: normal;
            font-variant-ligatures: normal;
            font-variant-caps: normal;
            font-variant-numeric: normal;
            font-variant-east-asian: normal;
            font-weight: normal;
            font-stretch: normal;
            font-size: 14px;
            line-height: 22px;
            font-family: "Hiragino Sans GB", "Microsoft Yahei", SimSun, Arial, "Helvetica Neue", Helvetica;
        }

    </style>
</head>
<body>
<%="http://"+request.getLocalAddr()+":"+request.getLocalPort()%>
<form action="/login" method="post">
    <table class="lp-login">
        <tr>
            <td align="right"><span>用户名</span></td>
            <td align="center">
                <input type="text" id="username" name="username" value="admin"></input>
            </td>
        </tr>
        <tr>
            <td align="right"><span>密码</span></td>
            <td align="center">
                <input type="password" id="password" name="password" value="admin"></input>
            </td>
        </tr>
        <tr align="center">
            <td colspan="2">
                <input type="submit" class="submitBtn" value="登陆"></input>
            </td>
        </tr>
    </table>
</form>

</body>
</html>
