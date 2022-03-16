<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>View Profiles</title>
</head>
<body>
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Age</th>
                <th>Occupation</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${profiles}" var="profile">
                <tr>
                    <td>${profile.name}</td>
                    <td>${profile.age}</td>
                    <td>${profile.occupation}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>