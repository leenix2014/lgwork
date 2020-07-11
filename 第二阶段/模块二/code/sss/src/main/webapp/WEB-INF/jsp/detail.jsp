<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Resume Detail</title>
</head>
<body>
<%="http://"+request.getLocalAddr()+":"+request.getLocalPort()%>
<form action="/save" method="post">
<table>
    <c:if test="${not empty resume.id}">
        <tr>
            <td>ID:</td>
            <td><input type="text" name="id" value='<c:out value="${resume.id}"/>' readonly="true"/></td>
        </tr>
    </c:if>
    <tr>
        <td>Address:</td>
        <td><input type="text" name="address" value='<c:out value="${resume.address}"/>' /></td>
    </tr>
    <tr>
        <td>Name:</td>
        <td><input type="text" name="name" value='<c:out value="${resume.name}"/>' /></td>
    </tr>
    <tr>
        <td>Phone:</td>
        <td><input type="text" name="phone" value='<c:out value="${resume.phone}"/>' /></td>
    </tr>
    <tr>
        <td><input type="submit" value="保存"/></td>
        <td><a href="/list">返回</a></td>
    </tr>
</table>
</form>

</body>
</html>