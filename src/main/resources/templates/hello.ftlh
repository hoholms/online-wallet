<#import "parts/common.ftlh" as lr>
<@lr.common>
<body>
<#--<h1 th:inline="text">Hello [[${#httpServletRequest.remoteUser}]]!</h1>-->
    Hello User
<form method="post" action="/logout">
    <input type="submit" value="Sign Out"/>
</form>
</@lr.common>