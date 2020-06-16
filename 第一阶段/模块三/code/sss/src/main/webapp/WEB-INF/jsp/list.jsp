<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Resume List</title>
</head>
<body>

<table>
    <tr><a href="/detail">新增</a></tr>
    <c:forEach items="${list}" var="item">
        <tr>
            <td><c:out value="${item}" /></td>
            <td><a href="/detail?id=${item.id}">编辑</a></td>
            <td><a href="/delete?id=${item.id}">删除</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>