<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Resume List</title>
</head>
<body>
<%="http://"+request.getLocalAddr()+":"+request.getLocalPort()%>
<table cellpadding="10px">
    <tr><td><a href="/detail">新增</a></td><td/><td/><td/><td/><td><a href="/logout">登出</a></td></tr>
    <c:forEach items="${list}" var="item">
        <tr>
            <td><c:out value="${item.id}" /></td>
            <td><c:out value="${item.address}" /></td>
            <td><c:out value="${item.name}" /></td>
            <td><c:out value="${item.phone}" /></td>
            <td><a href="/detail?id=${item.id}">编辑</a></td>
            <td><a href="/delete?id=${item.id}">删除</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>