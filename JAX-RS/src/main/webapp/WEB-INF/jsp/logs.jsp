<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>

<h2>How to use?</h2>

curl -XPOST http://localhost:8080/logs -d'name=access_log&line=10.146.45.58 - - [11/Nov/2012:03:21:02 +0000] "GET / HTTP/1.1" 200 7505 "-" "ELB-HealthChecker/1.0"'

<h2>Recent logs</h2>
<c:forEach var="log" items="${it.logs}" varStatus="s">
[${log.name}]: ${log.line}<br/>
</c:forEach>
</form>
</body>
</html>
