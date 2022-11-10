<#import "parts/common.ftlh" as lr>
<@lr.common>
<form method="post" action="/login">
    <div>
        <label> User Name : <input name="username" type="text"> </label>
    </div>
    <div>
        <label> Password: <input name="password" type="password"/> </label>
    </div>
    <div>
        <input type="submit" value="Sign In"/>
    </div>
</form>

<form action="/register">
    <input type="submit" value="Sign Up">
</form>
</@lr.common>